package com.pharmacy.inventory.controller;

import com.pharmacy.inventory.dto.ApiResponse;
import com.pharmacy.inventory.dto.BatchDto;
import com.pharmacy.inventory.entity.Batch;
import com.pharmacy.inventory.service.InventoryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/batches")
public class BatchController {

    private final InventoryService inventoryService;

    public BatchController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<BatchDto>>> getAllBatches(
            @RequestParam(required = false) Long medicineId) {
        List<BatchDto> list = (medicineId != null)
                ? inventoryService.getBatchesByMedicine(medicineId)
                : inventoryService.getAllBatches();
        return ResponseEntity.ok(ApiResponse.success(list));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'PHARMACIST', 'SUPPLIER')")
    public ResponseEntity<ApiResponse<BatchDto>> addBatch(@Valid @RequestBody BatchDto dto) {
        BatchDto created = inventoryService.addBatch(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Batch registered into inventory", created));
    }

    @GetMapping("/fefo-recommendation")
    public ResponseEntity<ApiResponse<List<InventoryService.FefoMatchResult>>> getFefoRecommendation(
            @RequestParam Long medicineId,
            @RequestParam(defaultValue = "1") int quantity) {
        List<InventoryService.FefoMatchResult> results = inventoryService.recommendFefoBatches(medicineId, quantity);
        return ResponseEntity.ok(ApiResponse.success(results));
    }

    @PatchMapping("/{batchId}/status")
    @PreAuthorize("hasAnyRole('ADMIN', 'PHARMACIST')")
    public ResponseEntity<ApiResponse<BatchDto>> updateBatchStatus(
            @PathVariable Long batchId,
            @RequestBody Map<String, String> payload) {
        String statusStr = payload.get("status");
        String reason = payload.getOrDefault("reason", "Manual adjustment");
        Batch.BatchStatus status = Batch.BatchStatus.valueOf(statusStr.toUpperCase());
        BatchDto updated = inventoryService.updateBatchStatus(batchId, status, reason);
        return ResponseEntity.ok(ApiResponse.success("Batch status updated", updated));
    }
}
