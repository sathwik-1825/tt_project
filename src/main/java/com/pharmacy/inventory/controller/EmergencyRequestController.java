package com.pharmacy.inventory.controller;

import com.pharmacy.inventory.dto.AllocateRequestDto;
import com.pharmacy.inventory.dto.ApiResponse;
import com.pharmacy.inventory.dto.CreateEmergencyRequestDto;
import com.pharmacy.inventory.dto.EmergencyRequestDto;
import com.pharmacy.inventory.entity.EmergencyRequest;
import com.pharmacy.inventory.repository.EmergencyRequestRepository;
import com.pharmacy.inventory.service.EmergencyRequestService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/requests")
public class EmergencyRequestController {

    private final EmergencyRequestService requestService;
    private final EmergencyRequestRepository requestRepository;

    public EmergencyRequestController(EmergencyRequestService requestService, EmergencyRequestRepository requestRepository) {
        this.requestService = requestService;
        this.requestRepository = requestRepository;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<EmergencyRequestDto>>> getRequests() {
        List<EmergencyRequestDto> list = requestService.getRequestsForCurrentUser();
        return ResponseEntity.ok(ApiResponse.success(list));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<EmergencyRequestDto>> getRequestById(@PathVariable Long id) {
        EmergencyRequestDto dto = requestService.getRequestById(id);
        return ResponseEntity.ok(ApiResponse.success(dto));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('STAFF', 'ADMIN', 'PHARMACIST')")
    public ResponseEntity<ApiResponse<EmergencyRequestDto>> createRequest(
            @Valid @RequestBody CreateEmergencyRequestDto dto) {
        EmergencyRequestDto created = requestService.createRequest(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Emergency supply request submitted", created));
    }

    @PostMapping("/{id}/auto-allocate")
    @PreAuthorize("hasAnyRole('PHARMACIST', 'ADMIN')")
    public ResponseEntity<ApiResponse<EmergencyRequestDto>> autoAllocateFefo(@PathVariable Long id) {
        EmergencyRequest request = requestRepository.findById(id).orElseThrow();
        EmergencyRequestDto allocated = requestService.autoAllocateFefo(request);
        return ResponseEntity.ok(ApiResponse.success("Stock auto-matched using FEFO algorithm", allocated));
    }

    @PostMapping("/allocate")
    @PreAuthorize("hasAnyRole('PHARMACIST', 'ADMIN')")
    public ResponseEntity<ApiResponse<EmergencyRequestDto>> allocateBatches(
            @Valid @RequestBody AllocateRequestDto dto) {
        EmergencyRequestDto allocated = requestService.allocateBatches(dto);
        return ResponseEntity.ok(ApiResponse.success("Stock allocated successfully", allocated));
    }

    @PostMapping("/{id}/reject")
    @PreAuthorize("hasAnyRole('PHARMACIST', 'ADMIN')")
    public ResponseEntity<ApiResponse<EmergencyRequestDto>> rejectRequest(
            @PathVariable Long id,
            @RequestBody Map<String, String> payload) {
        String reason = payload.getOrDefault("reason", "Not specified");
        EmergencyRequestDto rejected = requestService.rejectRequest(id, reason);
        return ResponseEntity.ok(ApiResponse.success("Request rejected", rejected));
    }
}
