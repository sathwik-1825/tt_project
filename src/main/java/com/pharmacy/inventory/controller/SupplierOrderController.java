package com.pharmacy.inventory.controller;

import com.pharmacy.inventory.dto.ApiResponse;
import com.pharmacy.inventory.entity.SupplierOrder;
import com.pharmacy.inventory.service.SupplierOrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/supplier-orders")
public class SupplierOrderController {

    private final SupplierOrderService supplierOrderService;

    public SupplierOrderController(SupplierOrderService supplierOrderService) {
        this.supplierOrderService = supplierOrderService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<SupplierOrder>>> getAllOrders() {
        return ResponseEntity.ok(ApiResponse.success(supplierOrderService.getAllOrders()));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'PHARMACIST')")
    public ResponseEntity<ApiResponse<SupplierOrder>> createOrder(@RequestBody Map<String, Object> payload) {
        Long medicineId = Long.valueOf(payload.get("medicineId").toString());
        Integer quantity = Integer.valueOf(payload.get("quantity").toString());
        String notes = (String) payload.get("notes");

        SupplierOrder order = supplierOrderService.createOrder(medicineId, quantity, notes);
        return ResponseEntity.ok(ApiResponse.success("Purchase order placed", order));
    }

    @PostMapping("/{id}/fulfill")
    @PreAuthorize("hasAnyRole('SUPPLIER', 'ADMIN', 'PHARMACIST')")
    public ResponseEntity<ApiResponse<SupplierOrder>> fulfillOrder(
            @PathVariable Long id,
            @RequestBody Map<String, String> payload) {
        String batchNumber = payload.get("batchNumber");
        LocalDate mfgDate = LocalDate.parse(payload.get("manufacturingDate"));
        LocalDate expDate = LocalDate.parse(payload.get("expiryDate"));
        String location = payload.get("storageLocation");

        SupplierOrder fulfilled = supplierOrderService.fulfillOrder(id, batchNumber, mfgDate, expDate, location);
        return ResponseEntity.ok(ApiResponse.success("Order fulfilled and new stock added to inventory", fulfilled));
    }
}
