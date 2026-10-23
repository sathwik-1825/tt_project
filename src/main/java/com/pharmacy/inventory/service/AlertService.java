package com.pharmacy.inventory.service;

import com.pharmacy.inventory.dto.AlertDto;
import com.pharmacy.inventory.entity.Batch;
import com.pharmacy.inventory.entity.EmergencyRequest;
import com.pharmacy.inventory.entity.Medicine;
import com.pharmacy.inventory.repository.BatchRepository;
import com.pharmacy.inventory.repository.EmergencyRequestRepository;
import com.pharmacy.inventory.repository.MedicineRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
public class AlertService {

    private final MedicineRepository medicineRepository;
    private final BatchRepository batchRepository;
    private final EmergencyRequestRepository requestRepository;

    public AlertService(MedicineRepository medicineRepository, BatchRepository batchRepository, EmergencyRequestRepository requestRepository) {
        this.medicineRepository = medicineRepository;
        this.batchRepository = batchRepository;
        this.requestRepository = requestRepository;
    }

    @Transactional(readOnly = true)
    public List<AlertDto> getAllAlerts() {
        List<AlertDto> alerts = new ArrayList<>();
        LocalDate today = LocalDate.now();

        List<EmergencyRequest> criticalRequests = requestRepository.findByUrgencyLevel(EmergencyRequest.UrgencyLevel.CRITICAL);
        for (EmergencyRequest req : criticalRequests) {
            if (req.getStatus() == EmergencyRequest.RequestStatus.PENDING || req.getStatus() == EmergencyRequest.RequestStatus.APPROVED) {
                alerts.add(AlertDto.builder()
                        .id("REQ-" + req.getId())
                        .type("CRITICAL_REQUEST")
                        .severity("CRITICAL")
                        .title("Urgent Emergency Request: " + req.getMedicine().getName())
                        .message("Department " + req.getDepartment() + " urgently requires " +
                                req.getRequestedQuantity() + " units for: " + req.getClinicalReason())
                        .medicineId(req.getMedicine().getId())
                        .medicineName(req.getMedicine().getName())
                        .medicineSku(req.getMedicine().getSku())
                        .department(req.getDepartment())
                        .referenceId(req.getId())
                        .timestamp(req.getCreatedAt())
                        .suggestedAction("Review and Allocate FEFO stock immediately")
                        .build());
            }
        }

        List<Medicine> medicines = medicineRepository.findAll();
        for (Medicine medicine : medicines) {
            Integer totalStock = batchRepository.getTotalActiveStock(medicine.getId(), today);
            if (totalStock == null) totalStock = 0;

            if (totalStock == 0) {
                alerts.add(AlertDto.builder()
                        .id("STOCK-ZERO-" + medicine.getId())
                        .type("OUT_OF_STOCK")
                        .severity("CRITICAL")
                        .title("Stock Depleted: " + medicine.getName())
                        .message("No active units remaining in inventory! " + (medicine.isCritical() ? "[LIFE-SAVING MEDICINE]" : ""))
                        .medicineId(medicine.getId())
                        .medicineName(medicine.getName())
                        .medicineSku(medicine.getSku())
                        .currentStock(0)
                        .threshold(medicine.getMinStockThreshold())
                        .timestamp(LocalDateTime.now())
                        .suggestedAction("Place urgent supplier restock order")
                        .build());
            } else if (totalStock <= medicine.getMinStockThreshold()) {
                String severity = totalStock <= (medicine.getMinStockThreshold() / 2) ? "HIGH" : "WARNING";
                alerts.add(AlertDto.builder()
                        .id("STOCK-LOW-" + medicine.getId())
                        .type("LOW_STOCK")
                        .severity(severity)
                        .title("Low Stock Warning: " + medicine.getName())
                        .message("Current stock of " + totalStock + " units is at or below reorder threshold of " + medicine.getMinStockThreshold())
                        .medicineId(medicine.getId())
                        .medicineName(medicine.getName())
                        .medicineSku(medicine.getSku())
                        .currentStock(totalStock)
                        .threshold(medicine.getMinStockThreshold())
                        .timestamp(LocalDateTime.now())
                        .suggestedAction("Initiate purchase order replenishment")
                        .build());
            }
        }

        List<Batch> expiredBatches = batchRepository.findExpiredBatches(today);
        for (Batch batch : expiredBatches) {
            alerts.add(AlertDto.builder()
                    .id("BATCH-EXP-" + batch.getId())
                    .type("EXPIRED")
                    .severity("CRITICAL")
                    .title("Expired Medicine Batch: " + batch.getBatchNumber())
                    .message("Batch " + batch.getBatchNumber() + " of " + batch.getMedicine().getName() +
                            " expired on " + batch.getExpiryDate() + " (" + batch.getCurrentQuantity() + " units remaining).")
                    .medicineId(batch.getMedicine().getId())
                    .medicineName(batch.getMedicine().getName())
                    .medicineSku(batch.getMedicine().getSku())
                    .batchNumber(batch.getBatchNumber())
                    .currentStock(batch.getCurrentQuantity())
                    .timestamp(LocalDateTime.now())
                    .suggestedAction("Quarantine and initiate safe disposal protocol")
                    .build());
        }

        LocalDate thirtyDaysLater = today.plusDays(30);
        List<Batch> expiringSoonBatches = batchRepository.findExpiringBatches(today, thirtyDaysLater);
        for (Batch batch : expiringSoonBatches) {
            long daysLeft = ChronoUnit.DAYS.between(today, batch.getExpiryDate());
            alerts.add(AlertDto.builder()
                    .id("BATCH-SOON-" + batch.getId())
                    .type("EXPIRING_SOON")
                    .severity(daysLeft <= 10 ? "HIGH" : "WARNING")
                    .title("Expiring Soon (" + daysLeft + " days): " + batch.getBatchNumber())
                    .message("Batch " + batch.getBatchNumber() + " of " + batch.getMedicine().getName() +
                            " will expire on " + batch.getExpiryDate() + " (" + batch.getCurrentQuantity() + " units).")
                    .medicineId(batch.getMedicine().getId())
                    .medicineName(batch.getMedicine().getName())
                    .medicineSku(batch.getMedicine().getSku())
                    .batchNumber(batch.getBatchNumber())
                    .currentStock(batch.getCurrentQuantity())
                    .timestamp(LocalDateTime.now())
                    .suggestedAction("Prioritize for emergency dispatch via FEFO")
                    .build());
        }

        return alerts;
    }
}
