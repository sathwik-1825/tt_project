package com.pharmacy.inventory.repository;

import com.pharmacy.inventory.entity.DispatchRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DispatchRecordRepository extends JpaRepository<DispatchRecord, Long> {
    Optional<DispatchRecord> findByDispatchNumber(String dispatchNumber);
    Optional<DispatchRecord> findByEmergencyRequestId(Long emergencyRequestId);
    List<DispatchRecord> findByStatus(DispatchRecord.DispatchStatus status);
    List<DispatchRecord> findByDestinationDepartment(String destinationDepartment);
    List<DispatchRecord> findAllByOrderByDispatchedAtDesc();
}
