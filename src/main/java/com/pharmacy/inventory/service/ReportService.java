package com.pharmacy.inventory.service;

import com.pharmacy.inventory.dto.StockSummaryDto;
import com.pharmacy.inventory.entity.Batch;
import com.pharmacy.inventory.entity.EmergencyRequest;
import com.pharmacy.inventory.entity.Medicine;
import com.pharmacy.inventory.repository.BatchRepository;
import com.pharmacy.inventory.repository.EmergencyRequestRepository;
import com.pharmacy.inventory.repository.MedicineRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReportService {

    private final MedicineRepository medicineRepository;
    private final BatchRepository batchRepository;
    private final EmergencyRequestRepository requestRepository;

    public ReportService(MedicineRepository medicineRepository, BatchRepository batchRepository, EmergencyRequestRepository requestRepository) {
        this.medicineRepository = medicineRepository;
        this.batchRepository = batchRepository;
        this.requestRepository = requestRepository;
    }

    @Transactional(readOnly = true)
    public StockSummaryDto getDashboardStats() {
        LocalDate today = LocalDate.now();
        List<Medicine> medicines = medicineRepository.findAll();
        List<Batch> batches = batchRepository.findAll();
        List<EmergencyRequest> requests = requestRepository.findAll();

        long lowStockCount = 0;
        long outOfStockCount = 0;
        long totalStockUnits = 0;
        BigDecimal totalValue = BigDecimal.ZERO;
        Map<String, Integer> stockByCategory = new HashMap<>();

        for (Medicine m : medicines) {
            Integer activeStock = batchRepository.getTotalActiveStock(m.getId(), today);
            if (activeStock == null) activeStock = 0;

            totalStockUnits += activeStock;
            if (m.getUnitPrice() != null) {
                totalValue = totalValue.add(m.getUnitPrice().multiply(BigDecimal.valueOf(activeStock)));
            }

            if (activeStock == 0) {
                outOfStockCount++;
            } else if (activeStock <= m.getMinStockThreshold()) {
                lowStockCount++;
            }

            String catName = m.getCategory() != null ? m.getCategory().getName() : "General";
            stockByCategory.put(catName, stockByCategory.getOrDefault(catName, 0) + activeStock);
        }

        LocalDate thirtyDays = today.plusDays(30);
        long expiringSoonCount = batches.stream()
                .filter(b -> b.getStatus() == Batch.BatchStatus.ACTIVE &&
                             b.getCurrentQuantity() > 0 &&
                             !b.getExpiryDate().isBefore(today) &&
                             !b.getExpiryDate().isAfter(thirtyDays))
                .count();

        long expiredCount = batches.stream()
                .filter(b -> b.getExpiryDate().isBefore(today) || b.getStatus() == Batch.BatchStatus.EXPIRED)
                .count();

        long pendingRequests = requests.stream()
                .filter(r -> r.getStatus() == EmergencyRequest.RequestStatus.PENDING ||
                             r.getStatus() == EmergencyRequest.RequestStatus.APPROVED ||
                             r.getStatus() == EmergencyRequest.RequestStatus.ALLOCATED)
                .count();

        long criticalRequests = requests.stream()
                .filter(r -> r.getUrgencyLevel() == EmergencyRequest.UrgencyLevel.CRITICAL &&
                             r.getStatus() != EmergencyRequest.RequestStatus.DELIVERED &&
                             r.getStatus() != EmergencyRequest.RequestStatus.REJECTED)
                .count();

        long dispatchedRequests = requests.stream()
                .filter(r -> r.getStatus() == EmergencyRequest.RequestStatus.DISPATCHED)
                .count();

        long deliveredRequests = requests.stream()
                .filter(r -> r.getStatus() == EmergencyRequest.RequestStatus.DELIVERED)
                .count();

        Map<String, Integer> requestsByDepartment = new HashMap<>();
        for (EmergencyRequest r : requests) {
            requestsByDepartment.put(r.getDepartment(), requestsByDepartment.getOrDefault(r.getDepartment(), 0) + 1);
        }

        return StockSummaryDto.builder()
                .totalMedicines(medicines.size())
                .totalBatches(batches.size())
                .totalStockUnits(totalStockUnits)
                .totalInventoryValue(totalValue)
                .lowStockCount(lowStockCount)
                .outOfStockCount(outOfStockCount)
                .expiringSoonCount(expiringSoonCount)
                .expiredCount(expiredCount)
                .pendingEmergencyRequests(pendingRequests)
                .criticalEmergencyRequests(criticalRequests)
                .dispatchedRequests(dispatchedRequests)
                .deliveredRequests(deliveredRequests)
                .stockByCategory(stockByCategory)
                .requestsByDepartment(requestsByDepartment)
                .build();
    }
}
