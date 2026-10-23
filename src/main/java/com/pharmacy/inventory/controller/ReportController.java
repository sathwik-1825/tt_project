package com.pharmacy.inventory.controller;

import com.pharmacy.inventory.dto.ApiResponse;
import com.pharmacy.inventory.dto.StockSummaryDto;
import com.pharmacy.inventory.entity.StockMovement;
import com.pharmacy.inventory.service.InventoryService;
import com.pharmacy.inventory.service.ReportService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    private final ReportService reportService;
    private final InventoryService inventoryService;

    public ReportController(ReportService reportService, InventoryService inventoryService) {
        this.reportService = reportService;
        this.inventoryService = inventoryService;
    }

    @GetMapping("/dashboard-stats")
    public ResponseEntity<ApiResponse<StockSummaryDto>> getDashboardStats() {
        return ResponseEntity.ok(ApiResponse.success(reportService.getDashboardStats()));
    }

    @GetMapping("/stock-movements")
    public ResponseEntity<ApiResponse<List<StockMovement>>> getStockMovements() {
        return ResponseEntity.ok(ApiResponse.success(inventoryService.getRecentStockMovements()));
    }
}
