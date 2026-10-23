package com.pharmacy.inventory.service;

import com.pharmacy.inventory.dto.MedicineDto;
import com.pharmacy.inventory.entity.Category;
import com.pharmacy.inventory.entity.Medicine;
import com.pharmacy.inventory.exception.BadRequestException;
import com.pharmacy.inventory.exception.ResourceNotFoundException;
import com.pharmacy.inventory.repository.BatchRepository;
import com.pharmacy.inventory.repository.CategoryRepository;
import com.pharmacy.inventory.repository.MedicineRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MedicineService {

    private final MedicineRepository medicineRepository;
    private final CategoryRepository categoryRepository;
    private final BatchRepository batchRepository;

    public MedicineService(MedicineRepository medicineRepository, CategoryRepository categoryRepository, BatchRepository batchRepository) {
        this.medicineRepository = medicineRepository;
        this.categoryRepository = categoryRepository;
        this.batchRepository = batchRepository;
    }

    @Transactional(readOnly = true)
    public List<MedicineDto> getAllMedicines() {
        return medicineRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public MedicineDto getMedicineById(Long id) {
        Medicine medicine = medicineRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Medicine not found with ID: " + id));
        return mapToDto(medicine);
    }

    @Transactional(readOnly = true)
    public List<MedicineDto> searchMedicines(String query, Long categoryId, Boolean criticalOnly) {
        List<Medicine> medicines;
        if (query != null && !query.isBlank()) {
            medicines = medicineRepository.searchMedicines(query.trim());
        } else if (categoryId != null) {
            medicines = medicineRepository.findByCategoryId(categoryId);
        } else if (Boolean.TRUE.equals(criticalOnly)) {
            medicines = medicineRepository.findByIsCriticalTrue();
        } else {
            medicines = medicineRepository.findAll();
        }

        return medicines.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public MedicineDto createMedicine(MedicineDto dto) {
        if (medicineRepository.existsBySku(dto.getSku().trim().toUpperCase())) {
            throw new BadRequestException("A medicine with SKU " + dto.getSku() + " already exists");
        }

        Category category = null;
        if (dto.getCategoryId() != null) {
            category = categoryRepository.findById(dto.getCategoryId())
                    .orElseThrow(() -> new ResourceNotFoundException("Category not found with ID: " + dto.getCategoryId()));
        }

        Medicine medicine = Medicine.builder()
                .name(dto.getName().trim())
                .genericName(dto.getGenericName().trim())
                .sku(dto.getSku().trim().toUpperCase())
                .category(category)
                .dosageForm(dto.getDosageForm().trim())
                .strength(dto.getStrength())
                .manufacturer(dto.getManufacturer())
                .unitPrice(dto.getUnitPrice())
                .minStockThreshold(dto.getMinStockThreshold() != null ? dto.getMinStockThreshold() : 20)
                .isCritical(dto.isCritical())
                .storageCondition(dto.getStorageCondition())
                .description(dto.getDescription())
                .build();

        medicine = medicineRepository.save(medicine);
        return mapToDto(medicine);
    }

    @Transactional
    public MedicineDto updateMedicine(Long id, MedicineDto dto) {
        Medicine medicine = medicineRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Medicine not found with ID: " + id));

        if (!medicine.getSku().equalsIgnoreCase(dto.getSku().trim()) &&
                medicineRepository.existsBySku(dto.getSku().trim().toUpperCase())) {
            throw new BadRequestException("SKU " + dto.getSku() + " is already in use by another medicine");
        }

        Category category = null;
        if (dto.getCategoryId() != null) {
            category = categoryRepository.findById(dto.getCategoryId())
                    .orElseThrow(() -> new ResourceNotFoundException("Category not found with ID: " + dto.getCategoryId()));
        }

        medicine.setName(dto.getName().trim());
        medicine.setGenericName(dto.getGenericName().trim());
        medicine.setSku(dto.getSku().trim().toUpperCase());
        medicine.setCategory(category);
        medicine.setDosageForm(dto.getDosageForm().trim());
        medicine.setStrength(dto.getStrength());
        medicine.setManufacturer(dto.getManufacturer());
        medicine.setUnitPrice(dto.getUnitPrice());
        medicine.setMinStockThreshold(dto.getMinStockThreshold());
        medicine.setCritical(dto.isCritical());
        medicine.setStorageCondition(dto.getStorageCondition());
        medicine.setDescription(dto.getDescription());

        medicine = medicineRepository.save(medicine);
        return mapToDto(medicine);
    }

    @Transactional
    public void deleteMedicine(Long id) {
        Medicine medicine = medicineRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Medicine not found with ID: " + id));
        medicineRepository.delete(medicine);
    }

    public MedicineDto mapToDto(Medicine medicine) {
        LocalDate today = LocalDate.now();
        Integer totalActiveStock = batchRepository.getTotalActiveStock(medicine.getId(), today);
        if (totalActiveStock == null) {
            totalActiveStock = 0;
        }

        int batchesCount = batchRepository.findByMedicineId(medicine.getId()).size();

        String stockStatus;
        if (totalActiveStock == 0) {
            stockStatus = "OUT_OF_STOCK";
        } else if (totalActiveStock <= (medicine.getMinStockThreshold() / 2)) {
            stockStatus = "CRITICAL_LOW";
        } else if (totalActiveStock <= medicine.getMinStockThreshold()) {
            stockStatus = "LOW";
        } else {
            stockStatus = "ADEQUATE";
        }

        return MedicineDto.builder()
                .id(medicine.getId())
                .name(medicine.getName())
                .genericName(medicine.getGenericName())
                .sku(medicine.getSku())
                .categoryId(medicine.getCategory() != null ? medicine.getCategory().getId() : null)
                .categoryName(medicine.getCategory() != null ? medicine.getCategory().getName() : "General")
                .dosageForm(medicine.getDosageForm())
                .strength(medicine.getStrength())
                .manufacturer(medicine.getManufacturer())
                .unitPrice(medicine.getUnitPrice())
                .minStockThreshold(medicine.getMinStockThreshold())
                .isCritical(medicine.isCritical())
                .storageCondition(medicine.getStorageCondition())
                .description(medicine.getDescription())
                .totalActiveStock(totalActiveStock)
                .totalBatchesCount(batchesCount)
                .stockStatus(stockStatus)
                .createdAt(medicine.getCreatedAt())
                .updatedAt(medicine.getUpdatedAt())
                .build();
    }
}
