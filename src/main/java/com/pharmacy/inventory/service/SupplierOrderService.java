package com.pharmacy.inventory.service;

import com.pharmacy.inventory.dto.BatchDto;
import com.pharmacy.inventory.entity.*;
import com.pharmacy.inventory.exception.BadRequestException;
import com.pharmacy.inventory.exception.ResourceNotFoundException;
import com.pharmacy.inventory.repository.MedicineRepository;
import com.pharmacy.inventory.repository.SupplierOrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class SupplierOrderService {

    private final SupplierOrderRepository supplierOrderRepository;
    private final MedicineRepository medicineRepository;
    private final InventoryService inventoryService;
    private final AuthService authService;

    private static final AtomicInteger PO_COUNTER = new AtomicInteger(100);

    public SupplierOrderService(SupplierOrderRepository supplierOrderRepository,
                                MedicineRepository medicineRepository,
                                InventoryService inventoryService,
                                AuthService authService) {
        this.supplierOrderRepository = supplierOrderRepository;
        this.medicineRepository = medicineRepository;
        this.inventoryService = inventoryService;
        this.authService = authService;
    }

    @Transactional(readOnly = true)
    public List<SupplierOrder> getAllOrders() {
        return supplierOrderRepository.findAllByOrderByOrderedAtDesc();
    }

    @Transactional
    public SupplierOrder createOrder(Long medicineId, Integer quantity, String notes) {
        Medicine medicine = medicineRepository.findById(medicineId)
                .orElseThrow(() -> new ResourceNotFoundException("Medicine not found with ID: " + medicineId));

        String poNumber = "PO-" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")) + "-" + PO_COUNTER.incrementAndGet();

        SupplierOrder order = SupplierOrder.builder()
                .orderNumber(poNumber)
                .medicine(medicine)
                .quantity(quantity)
                .supplierCompanyName("Global Med Supply Corp")
                .status(SupplierOrder.OrderStatus.PLACED)
                .notes(notes)
                .build();

        return supplierOrderRepository.save(order);
    }

    @Transactional
    public SupplierOrder fulfillOrder(Long orderId, String batchNumber, LocalDate manufacturingDate,
                                      LocalDate expiryDate, String storageLocation) {
        SupplierOrder order = supplierOrderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Supplier order not found with ID: " + orderId));

        if (order.getStatus() == SupplierOrder.OrderStatus.DELIVERED) {
            throw new BadRequestException("Order is already delivered");
        }

        User supplierUser = authService.getCurrentAuthenticatedUser();

        order.setIssuedBatchNumber(batchNumber);
        order.setIssuedManufacturingDate(manufacturingDate);
        order.setIssuedExpiryDate(expiryDate);
        order.setSupplier(supplierUser);
        order.setStatus(SupplierOrder.OrderStatus.DELIVERED);
        order.setDeliveredAt(LocalDateTime.now());

        BatchDto batchDto = BatchDto.builder()
                .medicineId(order.getMedicine().getId())
                .batchNumber(batchNumber)
                .initialQuantity(order.getQuantity())
                .manufacturingDate(manufacturingDate)
                .expiryDate(expiryDate)
                .unitCost(order.getMedicine().getUnitPrice())
                .storageLocation(storageLocation != null ? storageLocation : "Main Pharmacy Inward Bay")
                .build();

        inventoryService.addBatch(batchDto);

        return supplierOrderRepository.save(order);
    }
}
