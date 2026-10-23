package com.pharmacy.inventory.service;

import com.pharmacy.inventory.dto.AllocateRequestDto;
import com.pharmacy.inventory.dto.CreateEmergencyRequestDto;
import com.pharmacy.inventory.dto.EmergencyRequestDto;
import com.pharmacy.inventory.entity.*;
import com.pharmacy.inventory.exception.BadRequestException;
import com.pharmacy.inventory.exception.InsufficientStockException;
import com.pharmacy.inventory.exception.ResourceNotFoundException;
import com.pharmacy.inventory.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class EmergencyRequestService {

    private static final Logger log = LoggerFactory.getLogger(EmergencyRequestService.class);

    private final EmergencyRequestRepository requestRepository;
    private final MedicineRepository medicineRepository;
    private final BatchRepository batchRepository;
    private final RequestAllocationRepository allocationRepository;
    private final DispatchRecordRepository dispatchRecordRepository;
    private final AuthService authService;
    private final InventoryService inventoryService;

    private static final AtomicInteger COUNTER = new AtomicInteger(100);

    public EmergencyRequestService(EmergencyRequestRepository requestRepository,
                                   MedicineRepository medicineRepository,
                                   BatchRepository batchRepository,
                                   RequestAllocationRepository allocationRepository,
                                   DispatchRecordRepository dispatchRecordRepository,
                                   AuthService authService,
                                   InventoryService inventoryService) {
        this.requestRepository = requestRepository;
        this.medicineRepository = medicineRepository;
        this.batchRepository = batchRepository;
        this.allocationRepository = allocationRepository;
        this.dispatchRecordRepository = dispatchRecordRepository;
        this.authService = authService;
        this.inventoryService = inventoryService;
    }

    @Transactional(readOnly = true)
    public List<EmergencyRequestDto> getAllRequests() {
        return requestRepository.findAllPrioritized().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<EmergencyRequestDto> getRequestsForCurrentUser() {
        User user = authService.getCurrentAuthenticatedUser();
        if (user.getRole() == Role.ROLE_STAFF) {
            return requestRepository.findByDepartment(user.getDepartment()).stream()
                    .map(this::mapToDto)
                    .collect(Collectors.toList());
        }
        return getAllRequests();
    }

    @Transactional(readOnly = true)
    public EmergencyRequestDto getRequestById(Long id) {
        EmergencyRequest request = requestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Emergency Request not found with ID: " + id));
        return mapToDto(request);
    }

    @Transactional
    public EmergencyRequestDto createRequest(CreateEmergencyRequestDto dto) {
        User currentUser = authService.getCurrentAuthenticatedUser();

        Medicine medicine = medicineRepository.findById(dto.getMedicineId())
                .orElseThrow(() -> new ResourceNotFoundException("Medicine not found with ID: " + dto.getMedicineId()));

        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss"));
        String requestNumber = "REQ-EMG-" + timestamp + "-" + COUNTER.incrementAndGet();

        EmergencyRequest request = EmergencyRequest.builder()
                .requestNumber(requestNumber)
                .medicine(medicine)
                .requestedBy(currentUser)
                .department(dto.getDepartment() != null ? dto.getDepartment() : currentUser.getDepartment())
                .requestedQuantity(dto.getRequestedQuantity())
                .allocatedQuantity(0)
                .urgencyLevel(dto.getUrgencyLevel())
                .status(EmergencyRequest.RequestStatus.PENDING)
                .clinicalReason(dto.getClinicalReason())
                .bedOrPatientId(dto.getBedOrPatientId())
                .requiredBy(dto.getRequiredBy() != null ? dto.getRequiredBy() : LocalDateTime.now().plusHours(2))
                .build();

        request = requestRepository.save(request);

        if (dto.getUrgencyLevel() == EmergencyRequest.UrgencyLevel.CRITICAL) {
            autoAllocateFefo(request);
        }

        return mapToDto(request);
    }

    @Transactional
    public EmergencyRequestDto autoAllocateFefo(EmergencyRequest request) {
        LocalDate today = LocalDate.now();
        List<Batch> availableBatches = batchRepository.findFefoAvailableBatches(request.getMedicine().getId(), today);

        int totalAvailable = availableBatches.stream().mapToInt(Batch::getCurrentQuantity).sum();
        if (totalAvailable == 0) {
            log.warn("Emergency Request {}: No available stock for medicine {}", request.getRequestNumber(), request.getMedicine().getName());
            return mapToDto(request);
        }

        if (!request.getAllocations().isEmpty()) {
            allocationRepository.deleteAll(request.getAllocations());
            request.getAllocations().clear();
        }

        int remainingToAllocate = request.getRequestedQuantity();
        int totalAllocated = 0;

        for (Batch batch : availableBatches) {
            if (remainingToAllocate <= 0) break;

            int allocateFromThisBatch = Math.min(batch.getCurrentQuantity(), remainingToAllocate);

            RequestAllocation allocation = RequestAllocation.builder()
                    .emergencyRequest(request)
                    .batch(batch)
                    .allocatedQuantity(allocateFromThisBatch)
                    .build();

            request.getAllocations().add(allocation);
            remainingToAllocate -= allocateFromThisBatch;
            totalAllocated += allocateFromThisBatch;
        }

        request.setAllocatedQuantity(totalAllocated);
        request.setStatus(EmergencyRequest.RequestStatus.ALLOCATED);
        request.setPharmacistNotes("System auto-allocated " + totalAllocated + " units via FEFO algorithm.");

        request = requestRepository.save(request);
        return mapToDto(request);
    }

    @Transactional
    public EmergencyRequestDto allocateBatches(AllocateRequestDto dto) {
        User pharmacist = authService.getCurrentAuthenticatedUser();

        EmergencyRequest request = requestRepository.findById(dto.getRequestId())
                .orElseThrow(() -> new ResourceNotFoundException("Emergency Request not found with ID: " + dto.getRequestId()));

        if (request.getStatus() == EmergencyRequest.RequestStatus.DISPATCHED ||
            request.getStatus() == EmergencyRequest.RequestStatus.DELIVERED) {
            throw new BadRequestException("Cannot reallocate a request that is already dispatched or delivered");
        }

        int totalAllocated = 0;
        List<RequestAllocation> newAllocations = new ArrayList<>();

        for (AllocateRequestDto.BatchAllocationItem item : dto.getAllocations()) {
            Batch batch = batchRepository.findById(item.getBatchId())
                    .orElseThrow(() -> new ResourceNotFoundException("Batch not found with ID: " + item.getBatchId()));

            if (batch.getStatus() != Batch.BatchStatus.ACTIVE) {
                throw new BadRequestException("Batch " + batch.getBatchNumber() + " is not active");
            }

            if (batch.getExpiryDate().isBefore(LocalDate.now())) {
                throw new BadRequestException("Batch " + batch.getBatchNumber() + " is expired and cannot be allocated");
            }

            if (item.getQuantity() > batch.getCurrentQuantity()) {
                throw new InsufficientStockException("Requested " + item.getQuantity() + " units from batch " +
                        batch.getBatchNumber() + " but only " + batch.getCurrentQuantity() + " available");
            }

            RequestAllocation allocation = RequestAllocation.builder()
                    .emergencyRequest(request)
                    .batch(batch)
                    .allocatedQuantity(item.getQuantity())
                    .build();

            newAllocations.add(allocation);
            totalAllocated += item.getQuantity();
        }

        allocationRepository.deleteAll(request.getAllocations());
        request.getAllocations().clear();

        request.getAllocations().addAll(newAllocations);
        request.setAllocatedQuantity(totalAllocated);
        request.setApprovedBy(pharmacist);
        request.setPharmacistNotes(dto.getPharmacistNotes());
        request.setStatus(EmergencyRequest.RequestStatus.ALLOCATED);

        request = requestRepository.save(request);
        return mapToDto(request);
    }

    @Transactional
    public EmergencyRequestDto rejectRequest(Long requestId, String reason) {
        User pharmacist = authService.getCurrentAuthenticatedUser();

        EmergencyRequest request = requestRepository.findById(requestId)
                .orElseThrow(() -> new ResourceNotFoundException("Emergency Request not found with ID: " + requestId));

        request.setStatus(EmergencyRequest.RequestStatus.REJECTED);
        request.setApprovedBy(pharmacist);
        request.setPharmacistNotes("Rejected: " + reason);

        request = requestRepository.save(request);
        return mapToDto(request);
    }

    public EmergencyRequestDto mapToDto(EmergencyRequest request) {
        List<EmergencyRequestDto.AllocationItemDto> allocationDtos = request.getAllocations().stream()
                .map(a -> EmergencyRequestDto.AllocationItemDto.builder()
                        .allocationId(a.getId())
                        .batchId(a.getBatch().getId())
                        .batchNumber(a.getBatch().getBatchNumber())
                        .expiryDate(a.getBatch().getExpiryDate().toString())
                        .quantity(a.getAllocatedQuantity())
                        .storageLocation(a.getBatch().getStorageLocation())
                        .build())
                .collect(Collectors.toList());

        var dispatchOpt = dispatchRecordRepository.findByEmergencyRequestId(request.getId());

        return EmergencyRequestDto.builder()
                .id(request.getId())
                .requestNumber(request.getRequestNumber())
                .medicineId(request.getMedicine().getId())
                .medicineName(request.getMedicine().getName())
                .medicineGenericName(request.getMedicine().getGenericName())
                .medicineSku(request.getMedicine().getSku())
                .medicineDosageForm(request.getMedicine().getDosageForm())
                .isCriticalMedicine(request.getMedicine().isCritical())
                .requestedByUserId(request.getRequestedBy().getId())
                .requestedByUserName(request.getRequestedBy().getFullName())
                .requestedByUserRole(request.getRequestedBy().getRole().name())
                .department(request.getDepartment())
                .requestedQuantity(request.getRequestedQuantity())
                .allocatedQuantity(request.getAllocatedQuantity())
                .urgencyLevel(request.getUrgencyLevel())
                .status(request.getStatus())
                .clinicalReason(request.getClinicalReason())
                .bedOrPatientId(request.getBedOrPatientId())
                .requiredBy(request.getRequiredBy())
                .approvedByName(request.getApprovedBy() != null ? request.getApprovedBy().getFullName() : null)
                .pharmacistNotes(request.getPharmacistNotes())
                .allocations(allocationDtos)
                .dispatchId(dispatchOpt.map(DispatchRecord::getId).orElse(null))
                .dispatchNumber(dispatchOpt.map(DispatchRecord::getDispatchNumber).orElse(null))
                .carrierName(dispatchOpt.map(DispatchRecord::getCarrierName).orElse(null))
                .dispatchStatus(dispatchOpt.map(d -> d.getStatus().name()).orElse(null))
                .createdAt(request.getCreatedAt())
                .updatedAt(request.getUpdatedAt())
                .build();
    }
}
