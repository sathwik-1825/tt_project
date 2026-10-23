package com.pharmacy.inventory.controller;

import com.pharmacy.inventory.dto.AlertDto;
import com.pharmacy.inventory.dto.ApiResponse;
import com.pharmacy.inventory.service.AlertService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/alerts")
public class AlertController {

    private final AlertService alertService;

    public AlertController(AlertService alertService) {
        this.alertService = alertService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<AlertDto>>> getAlerts() {
        return ResponseEntity.ok(ApiResponse.success(alertService.getAllAlerts()));
    }
}
