package com.pharmacy.inventory.repository;

import com.pharmacy.inventory.entity.EmergencyRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmergencyRequestRepository extends JpaRepository<EmergencyRequest, Long> {

    Optional<EmergencyRequest> findByRequestNumber(String requestNumber);

    List<EmergencyRequest> findByStatus(EmergencyRequest.RequestStatus status);

    List<EmergencyRequest> findByUrgencyLevel(EmergencyRequest.UrgencyLevel urgencyLevel);

    List<EmergencyRequest> findByDepartment(String department);

    List<EmergencyRequest> findByRequestedById(Long userId);

    @Query("SELECT r FROM EmergencyRequest r ORDER BY " +
           "CASE r.urgencyLevel WHEN 'CRITICAL' THEN 1 WHEN 'HIGH' THEN 2 WHEN 'MEDIUM' THEN 3 ELSE 4 END ASC, " +
           "r.createdAt DESC")
    List<EmergencyRequest> findAllPrioritized();

    @Query("SELECT COUNT(r) FROM EmergencyRequest r WHERE r.status IN ('PENDING', 'APPROVED', 'ALLOCATED')")
    long countPendingOrActiveRequests();

    @Query("SELECT COUNT(r) FROM EmergencyRequest r WHERE r.urgencyLevel = 'CRITICAL' AND r.status IN ('PENDING', 'APPROVED', 'ALLOCATED')")
    long countActiveCriticalRequests();
}
