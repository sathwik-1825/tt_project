package com.pharmacy.inventory.repository;

import com.pharmacy.inventory.entity.Batch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BatchRepository extends JpaRepository<Batch, Long> {

    List<Batch> findByMedicineId(Long medicineId);

    List<Batch> findByMedicineIdAndStatus(Long medicineId, Batch.BatchStatus status);

    // FEFO: First Expired, First Out valid active stock
    @Query("SELECT b FROM Batch b WHERE b.medicine.id = :medicineId " +
           "AND b.status = 'ACTIVE' AND b.currentQuantity > 0 AND b.expiryDate >= :today " +
           "ORDER BY b.expiryDate ASC")
    List<Batch> findFefoAvailableBatches(@Param("medicineId") Long medicineId, @Param("today") LocalDate today);

    // Total non-expired available quantity for a medicine
    @Query("SELECT COALESCE(SUM(b.currentQuantity), 0) FROM Batch b " +
           "WHERE b.medicine.id = :medicineId AND b.status = 'ACTIVE' AND b.expiryDate >= :today")
    Integer getTotalActiveStock(@Param("medicineId") Long medicineId, @Param("today") LocalDate today);

    // Batches expiring soon (within days)
    @Query("SELECT b FROM Batch b WHERE b.status = 'ACTIVE' AND b.currentQuantity > 0 " +
           "AND b.expiryDate >= :today AND b.expiryDate <= :expiryThreshold ORDER BY b.expiryDate ASC")
    List<Batch> findExpiringBatches(@Param("today") LocalDate today, @Param("expiryThreshold") LocalDate expiryThreshold);

    // Batches already expired but still marked active
    @Query("SELECT b FROM Batch b WHERE b.status = 'ACTIVE' AND b.expiryDate < :today ORDER BY b.expiryDate ASC")
    List<Batch> findExpiredBatches(@Param("today") LocalDate today);

    boolean existsByBatchNumber(String batchNumber);
}
