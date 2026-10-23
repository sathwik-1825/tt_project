package com.pharmacy.inventory.service;

import com.pharmacy.inventory.dto.DispatchDto;
import com.pharmacy.inventory.entity.*;
import com.pharmacy.inventory.exception.BadRequestException;
import com.pharmacy.inventory.exception.ResourceNotFoundException;
import com.pharmacy.inventory.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class DispatchService {

    private static final Logger log = LoggerFactory.getLogger(DispatchService.class);

    private final DispatchRecordRepository dispatchRecordRepository;
    private final EmergencyRequestRepository requestRepository;
    private final BatchRepository batchRepository;
    private final StockMovementRepository stockMovementRepository;
    private final AuthService authService;

    private static final AtomicInteger DISPATCH_COUNTER = new AtomicInteger(100);

    public DispatchService(DispatchRecordRepository dispatchRecordRepository,
                           EmergencyRequestRepository requestRepository,
                           BatchRepository batchRepository,
                           StockMovementRepository stockMovementRepository,
                           AuthService authService) {
        this.dispatchRecordRepository = dispatchRecordRepository;
        this.requestRepository = requestRepository;
        this.batchRepository = batchRepository;
        this.stockMovementRepository = stockMovementRepository;
        this.authService = authService;
    }

    @Transactional(readOnly = true)
    public List<DispatchDto> getAllDispatches() {
        return dispatchRecordRepository.findAllByOrderByDispatchedAtDesc().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public DispatchDto getDispatchById(Long id) {
        DispatchRecord record = dispatchRecordRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Dispatch record not found with ID: " + id));
        return mapToDto(record);
    }

    @Transactional
    public DispatchDto executeDispatch(DispatchDto dto) {
        User dispatchedBy = authService.getCurrentAuthenticatedUser();

        EmergencyRequest request = requestRepository.findById(dto.getEmergencyRequestId())
                .orElseThrow(() -> new ResourceNotFoundException("Emergency Request not found with ID: " + dto.getEmergencyRequestId()));

        if (request.getStatus() == EmergencyRequest.RequestStatus.DISPATCHED ||
            request.getStatus() == EmergencyRequest.RequestStatus.DELIVERED) {
            throw new BadRequestException("Request " + request.getRequestNumber() + " is already dispatched or delivered");
        }

        if (request.getAllocations().isEmpty()) {
            throw new BadRequestException("Cannot dispatch request without stock allocations. Please allocate batches first.");
        }

        for (RequestAllocation allocation : request.getAllocations()) {
            Batch batch = allocation.getBatch();
            int deductQty = allocation.getAllocatedQuantity();

            if (batch.getCurrentQuantity() < deductQty) {
                throw new BadRequestException("Batch " + batch.getBatchNumber() + " has only " +
                        batch.getCurrentQuantity() + " units remaining, cannot deduct " + deductQty);
            }

            int newQty = batch.getCurrentQuantity() - deductQty;
            batch.setCurrentQuantity(newQty);
            if (newQty == 0) {
                batch.setStatus(Batch.BatchStatus.DEPLETED);
            }
            batchRepository.save(batch);

            Integer currentTotal = batchRepository.getTotalActiveStock(batch.getMedicine().getId(), LocalDate.now());
            StockMovement movement = StockMovement.builder()
                    .medicine(batch.getMedicine())
                    .batch(batch)
                    .movementType(StockMovement.MovementType.DISPATCH)
                    .quantity(-deductQty)
                    .balanceAfter(currentTotal != null ? currentTotal : 0)
                    .referenceNumber(request.getRequestNumber())
                    .performedBy(dispatchedBy)
                    .remarks("Dispatched for Emergency Request: " + request.getRequestNumber() + " to " + request.getDepartment())
                    .build();
            stockMovementRepository.save(movement);
        }

        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss"));
        String dispatchNumber = "DSP-" + timestamp + "-" + DISPATCH_COUNTER.incrementAndGet();
        String verificationCode = String.format("%04d", new Random().nextInt(10000));

        DispatchRecord record = DispatchRecord.builder()
                .dispatchNumber(dispatchNumber)
                .emergencyRequest(request)
                .dispatchedBy(dispatchedBy)
                .dispatchedAt(LocalDateTime.now())
                .carrierName(dto.getCarrierName().trim())
                .destinationDepartment(dto.getDestinationDepartment() != null ? dto.getDestinationDepartment() : request.getDepartment())
                .status(DispatchRecord.DispatchStatus.IN_TRANSIT)
                .handlingInstructions(dto.getHandlingInstructions())
                .verificationCode(verificationCode)
                .build();

        record = dispatchRecordRepository.save(record);

        request.setStatus(EmergencyRequest.RequestStatus.DISPATCHED);
        requestRepository.save(request);

        return mapToDto(record);
    }

    @Transactional
    public DispatchDto confirmDelivery(Long dispatchId, String verificationCode) {
        User receivedBy = authService.getCurrentAuthenticatedUser();

        DispatchRecord record = dispatchRecordRepository.findById(dispatchId)
                .orElseThrow(() -> new ResourceNotFoundException("Dispatch record not found with ID: " + dispatchId));

        if (record.getStatus() == DispatchRecord.DispatchStatus.DELIVERED) {
            return mapToDto(record);
        }

        if (verificationCode != null && !verificationCode.isBlank()) {
            if (!verificationCode.trim().equals(record.getVerificationCode())) {
                throw new BadRequestException("Invalid delivery verification code");
            }
        }

        record.setStatus(DispatchRecord.DispatchStatus.DELIVERED);
        record.setReceivedBy(receivedBy);
        record.setReceivedAt(LocalDateTime.now());
        record = dispatchRecordRepository.save(record);

        EmergencyRequest request = record.getEmergencyRequest();
        request.setStatus(EmergencyRequest.RequestStatus.DELIVERED);
        requestRepository.save(request);

        return mapToDto(record);
    }

    public DispatchDto mapToDto(DispatchRecord record) {
        return DispatchDto.builder()
                .id(record.getId())
                .dispatchNumber(record.getDispatchNumber())
                .emergencyRequestId(record.getEmergencyRequest().getId())
                .medicineName(record.getEmergencyRequest().getMedicine().getName())
                .allocatedQuantity(record.getEmergencyRequest().getAllocatedQuantity())
                .dispatchedByUserName(record.getDispatchedBy().getFullName())
                .dispatchedAt(record.getDispatchedAt())
                .carrierName(record.getCarrierName())
                .destinationDepartment(record.getDestinationDepartment())
                .status(record.getStatus().name())
                .receivedByUserName(record.getReceivedBy() != null ? record.getReceivedBy().getFullName() : null)
                .receivedAt(record.getReceivedAt())
                .handlingInstructions(record.getHandlingInstructions())
                .verificationCode(record.getVerificationCode())
                .build();
    }
}
