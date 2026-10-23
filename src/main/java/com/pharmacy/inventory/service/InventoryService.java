package com.pharmacy.inventory.service;

import com.pharmacy.inventory.dto.BatchDto;
import com.pharmacy.inventory.entity.Batch;
import com.pharmacy.inventory.entity.Medicine;
import com.pharmacy.inventory.entity.StockMovement;
import com.pharmacy.inventory.entity.User;
import com.pharmacy.inventory.exception.BadRequestException;
import com.pharmacy.inventory.exception.ResourceNotFoundException;
import com.pharmacy.inventory.repository.BatchRepository;
import com.pharmacy.inventory.repository.MedicineRepository;
import com.pharmacy.inventory.repository.StockMovementRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InventoryService {

    private static final Logger log = LoggerFactory.getLogger(InventoryService.class);

    private final BatchRepository batchRepository;
    private final MedicineRepository medicineRepository;
    private final StockMovementRepository stockMovementRepository;
    private final AuthService authService;

    public InventoryService(BatchRepository batchRepository, MedicineRepository medicineRepository,
                            StockMovementRepository stockMovementRepository, AuthService authService) {
        this.batchRepository = batchRepository;
        this.medicineRepository = medicineRepository;
        this.stockMovementRepository = stockMovementRepository;
        this.authService = authService;
    }

    @Transactional(readOnly = true)
    public List<BatchDto> getAllBatches() {
        return batchRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<BatchDto> getBatchesByMedicine(Long medicineId) {
        return batchRepository.findByMedicineId(medicineId).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public BatchDto addBatch(BatchDto dto) {
        Medicine medicine = medicineRepository.findById(dto.getMedicineId())
                .orElseThrow(() -> new ResourceNotFoundException("Medicine not found with ID: " + dto.getMedicineId()));

        if (batchRepository.existsByBatchNumber(dto.getBatchNumber().trim())) {
            throw new BadRequestException("Batch number " + dto.getBatchNumber() + " already exists");
        }

        if (dto.getExpiryDate().isBefore(LocalDate.now())) {
            throw new BadRequestException("Cannot register a batch that is already expired (" + dto.getExpiryDate() + ")");
        }

        Batch batch = Batch.builder()
                .medicine(medicine)
                .batchNumber(dto.getBatchNumber().trim().toUpperCase())
                .initialQuantity(dto.getInitialQuantity())
                .currentQuantity(dto.getInitialQuantity())
                .manufacturingDate(dto.getManufacturingDate())
                .expiryDate(dto.getExpiryDate())
                .unitCost(dto.getUnitCost())
                .storageLocation(dto.getStorageLocation())
                .status(Batch.BatchStatus.ACTIVE)
                .build();

        batch = batchRepository.save(batch);

        User currentUser = null;
        try {
            currentUser = authService.getCurrentAuthenticatedUser();
        } catch (Exception ignored) {}

        Integer currentTotal = batchRepository.getTotalActiveStock(medicine.getId(), LocalDate.now());
        StockMovement movement = StockMovement.builder()
                .medicine(medicine)
                .batch(batch)
                .movementType(StockMovement.MovementType.INWARD)
                .quantity(dto.getInitialQuantity())
                .balanceAfter(currentTotal != null ? currentTotal : dto.getInitialQuantity())
                .referenceNumber("BATCH-IN-" + batch.getBatchNumber())
                .performedBy(currentUser)
                .remarks("Inward stock registered for batch " + batch.getBatchNumber())
                .build();
        stockMovementRepository.save(movement);

        return mapToDto(batch);
    }

    @Transactional
    public BatchDto updateBatchStatus(Long batchId, Batch.BatchStatus status, String reason) {
        Batch batch = batchRepository.findById(batchId)
                .orElseThrow(() -> new ResourceNotFoundException("Batch not found with ID: " + batchId));

        batch.setStatus(status);
        batch = batchRepository.save(batch);

        User currentUser = null;
        try {
            currentUser = authService.getCurrentAuthenticatedUser();
        } catch (Exception ignored) {}

        Integer currentTotal = batchRepository.getTotalActiveStock(batch.getMedicine().getId(), LocalDate.now());
        StockMovement movement = StockMovement.builder()
                .medicine(batch.getMedicine())
                .batch(batch)
                .movementType(status == Batch.BatchStatus.EXPIRED ? StockMovement.MovementType.EXPIRED_DISPOSAL : StockMovement.MovementType.AUDIT_ADJUSTMENT)
                .quantity(0)
                .balanceAfter(currentTotal != null ? currentTotal : 0)
                .referenceNumber("STATUS-UPDATE-" + batch.getBatchNumber())
                .performedBy(currentUser)
                .remarks("Status updated to " + status + ". Reason: " + reason)
                .build();
        stockMovementRepository.save(movement);

        return mapToDto(batch);
    }

    @Transactional(readOnly = true)
    public List<FefoMatchResult> recommendFefoBatches(Long medicineId, int requestedQuantity) {
        LocalDate today = LocalDate.now();
        List<Batch> fefoBatches = batchRepository.findFefoAvailableBatches(medicineId, today);

        List<FefoMatchResult> results = new ArrayList<>();
        int remainingNeeded = requestedQuantity;

        for (Batch batch : fefoBatches) {
            if (remainingNeeded <= 0) break;

            int take = Math.min(batch.getCurrentQuantity(), remainingNeeded);
            results.add(new FefoMatchResult(
                    batch.getId(),
                    batch.getBatchNumber(),
                    batch.getExpiryDate(),
                    batch.getCurrentQuantity(),
                    take,
                    batch.getStorageLocation()
            ));
            remainingNeeded -= take;
        }

        return results;
    }

    @Transactional(readOnly = true)
    public List<StockMovement> getRecentStockMovements() {
        return stockMovementRepository.findAllByOrderByTimestampDesc();
    }

    public BatchDto mapToDto(Batch batch) {
        LocalDate today = LocalDate.now();
        long daysUntilExpiry = ChronoUnit.DAYS.between(today, batch.getExpiryDate());
        boolean isExpired = batch.getExpiryDate().isBefore(today);
        boolean isExpiringSoon = !isExpired && daysUntilExpiry <= 30;

        return BatchDto.builder()
                .id(batch.getId())
                .medicineId(batch.getMedicine().getId())
                .medicineName(batch.getMedicine().getName())
                .medicineGenericName(batch.getMedicine().getGenericName())
                .medicineSku(batch.getMedicine().getSku())
                .batchNumber(batch.getBatchNumber())
                .initialQuantity(batch.getInitialQuantity())
                .currentQuantity(batch.getCurrentQuantity())
                .manufacturingDate(batch.getManufacturingDate())
                .expiryDate(batch.getExpiryDate())
                .unitCost(batch.getUnitCost())
                .storageLocation(batch.getStorageLocation())
                .status(batch.getStatus())
                .daysUntilExpiry(daysUntilExpiry)
                .isExpired(isExpired)
                .isExpiringSoon(isExpiringSoon)
                .createdAt(batch.getCreatedAt())
                .build();
    }

    public record FefoMatchResult(
            Long batchId,
            String batchNumber,
            LocalDate expiryDate,
            int availableQuantity,
            int recommendedQuantity,
            String storageLocation
    ) {}
}
