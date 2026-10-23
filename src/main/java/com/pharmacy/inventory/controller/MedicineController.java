package com.pharmacy.inventory.controller;

import com.pharmacy.inventory.dto.ApiResponse;
import com.pharmacy.inventory.dto.MedicineDto;
import com.pharmacy.inventory.service.MedicineService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/medicines")
public class MedicineController {

    private final MedicineService medicineService;

    public MedicineController(MedicineService medicineService) {
        this.medicineService = medicineService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<MedicineDto>>> getAllMedicines(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) Boolean criticalOnly) {
        List<MedicineDto> list = medicineService.searchMedicines(search, categoryId, criticalOnly);
        return ResponseEntity.ok(ApiResponse.success(list));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<MedicineDto>> getMedicineById(@PathVariable Long id) {
        MedicineDto dto = medicineService.getMedicineById(id);
        return ResponseEntity.ok(ApiResponse.success(dto));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'PHARMACIST')")
    public ResponseEntity<ApiResponse<MedicineDto>> createMedicine(@Valid @RequestBody MedicineDto dto) {
        MedicineDto created = medicineService.createMedicine(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Medicine registered successfully", created));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PHARMACIST')")
    public ResponseEntity<ApiResponse<MedicineDto>> updateMedicine(
            @PathVariable Long id,
            @Valid @RequestBody MedicineDto dto) {
        MedicineDto updated = medicineService.updateMedicine(id, dto);
        return ResponseEntity.ok(ApiResponse.success("Medicine updated successfully", updated));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> deleteMedicine(@PathVariable Long id) {
        medicineService.deleteMedicine(id);
        return ResponseEntity.ok(ApiResponse.success("Medicine removed successfully", null));
    }
}
