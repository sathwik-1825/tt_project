package com.pharmacy.inventory.repository;

import com.pharmacy.inventory.entity.StockMovement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockMovementRepository extends JpaRepository<StockMovement, Long> {
    List<StockMovement> findByMedicineIdOrderByTimestampDesc(Long medicineId);
    List<StockMovement> findByBatchIdOrderByTimestampDesc(Long batchId);
    List<StockMovement> findAllByOrderByTimestampDesc();

    @Query("SELECT m FROM StockMovement m WHERE m.movementType = :movementType ORDER BY m.timestamp DESC")
    List<StockMovement> findByMovementType(@Param("movementType") StockMovement.MovementType movementType);
}
