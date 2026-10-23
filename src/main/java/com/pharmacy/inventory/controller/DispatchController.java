package com.pharmacy.inventory.controller;

import com.pharmacy.inventory.dto.ApiResponse;
import com.pharmacy.inventory.dto.DispatchDto;
import com.pharmacy.inventory.service.DispatchService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/dispatches")
public class DispatchController {

    private final DispatchService dispatchService;

    public DispatchController(DispatchService dispatchService) {
        this.dispatchService = dispatchService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<DispatchDto>>> getAllDispatches() {
        return ResponseEntity.ok(ApiResponse.success(dispatchService.getAllDispatches()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<DispatchDto>> getDispatchById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(dispatchService.getDispatchById(id)));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('PHARMACIST', 'ADMIN', 'STAFF', 'SUPPLIER')")
    public ResponseEntity<ApiResponse<DispatchDto>> createDispatch(@Valid @RequestBody DispatchDto dto) {
        DispatchDto dispatched = dispatchService.executeDispatch(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Medicine dispatched. Inventory automatically deducted.", dispatched));
    }

    @PostMapping("/{id}/confirm-delivery")
    public ResponseEntity<ApiResponse<DispatchDto>> confirmDelivery(
            @PathVariable Long id,
            @RequestBody(required = false) Map<String, String> payload) {
        String code = (payload != null) ? payload.get("verificationCode") : null;
        DispatchDto delivered = dispatchService.confirmDelivery(id, code);
        return ResponseEntity.ok(ApiResponse.success("Delivery confirmed and marked as received.", delivered));
    }
}
