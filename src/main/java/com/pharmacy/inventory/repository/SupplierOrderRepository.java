package com.pharmacy.inventory.repository;

import com.pharmacy.inventory.entity.SupplierOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SupplierOrderRepository extends JpaRepository<SupplierOrder, Long> {
    Optional<SupplierOrder> findByOrderNumber(String orderNumber);
    List<SupplierOrder> findByStatus(SupplierOrder.OrderStatus status);
    List<SupplierOrder> findBySupplierId(Long supplierId);
    List<SupplierOrder> findAllByOrderByOrderedAtDesc();
}
