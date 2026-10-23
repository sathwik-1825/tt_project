package com.pharmacy.inventory.repository;

import com.pharmacy.inventory.entity.RequestAllocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RequestAllocationRepository extends JpaRepository<RequestAllocation, Long> {
    List<RequestAllocation> findByEmergencyRequestId(Long requestId);
    List<RequestAllocation> findByBatchId(Long batchId);
}
