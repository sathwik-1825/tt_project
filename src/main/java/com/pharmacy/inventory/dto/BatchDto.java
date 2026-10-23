package com.pharmacy.inventory.dto;

import com.pharmacy.inventory.entity.Batch;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class BatchDto {
    private Long id;

    @NotNull(message = "Medicine ID is required")
    private Long medicineId;

    private String medicineName;
    private String medicineGenericName;
    private String medicineSku;

    @NotBlank(message = "Batch number is required")
    private String batchNumber;

    @NotNull(message = "Initial quantity is required")
    private Integer initialQuantity;

    private Integer currentQuantity;

    private LocalDate manufacturingDate;

    @NotNull(message = "Expiry date is required")
    private LocalDate expiryDate;

    private BigDecimal unitCost;
    private String storageLocation;
    private Batch.BatchStatus status;

    private Long daysUntilExpiry;
    private boolean isExpired;
    private boolean isExpiringSoon;
    private LocalDateTime createdAt;

    public BatchDto() {}

    public BatchDto(Long id, Long medicineId, String medicineName, String medicineGenericName,
                    String medicineSku, String batchNumber, Integer initialQuantity,
                    Integer currentQuantity, LocalDate manufacturingDate, LocalDate expiryDate,
                    BigDecimal unitCost, String storageLocation, Batch.BatchStatus status,
                    Long daysUntilExpiry, boolean isExpired, boolean isExpiringSoon, LocalDateTime createdAt) {
        this.id = id;
        this.medicineId = medicineId;
        this.medicineName = medicineName;
        this.medicineGenericName = medicineGenericName;
        this.medicineSku = medicineSku;
        this.batchNumber = batchNumber;
        this.initialQuantity = initialQuantity;
        this.currentQuantity = currentQuantity;
        this.manufacturingDate = manufacturingDate;
        this.expiryDate = expiryDate;
        this.unitCost = unitCost;
        this.storageLocation = storageLocation;
        this.status = status;
        this.daysUntilExpiry = daysUntilExpiry;
        this.isExpired = isExpired;
        this.isExpiringSoon = isExpiringSoon;
        this.createdAt = createdAt;
    }

    public static BatchDtoBuilder builder() {
        return new BatchDtoBuilder();
    }

    public static class BatchDtoBuilder {
        private Long id;
        private Long medicineId;
        private String medicineName;
        private String medicineGenericName;
        private String medicineSku;
        private String batchNumber;
        private Integer initialQuantity;
        private Integer currentQuantity;
        private LocalDate manufacturingDate;
        private LocalDate expiryDate;
        private BigDecimal unitCost;
        private String storageLocation;
        private Batch.BatchStatus status;
        private Long daysUntilExpiry;
        private boolean isExpired;
        private boolean isExpiringSoon;
        private LocalDateTime createdAt;

        public BatchDtoBuilder id(Long id) { this.id = id; return this; }
        public BatchDtoBuilder medicineId(Long medicineId) { this.medicineId = medicineId; return this; }
        public BatchDtoBuilder medicineName(String medicineName) { this.medicineName = medicineName; return this; }
        public BatchDtoBuilder medicineGenericName(String medicineGenericName) { this.medicineGenericName = medicineGenericName; return this; }
        public BatchDtoBuilder medicineSku(String medicineSku) { this.medicineSku = medicineSku; return this; }
        public BatchDtoBuilder batchNumber(String batchNumber) { this.batchNumber = batchNumber; return this; }
        public BatchDtoBuilder initialQuantity(Integer initialQuantity) { this.initialQuantity = initialQuantity; return this; }
        public BatchDtoBuilder currentQuantity(Integer currentQuantity) { this.currentQuantity = currentQuantity; return this; }
        public BatchDtoBuilder manufacturingDate(LocalDate manufacturingDate) { this.manufacturingDate = manufacturingDate; return this; }
        public BatchDtoBuilder expiryDate(LocalDate expiryDate) { this.expiryDate = expiryDate; return this; }
        public BatchDtoBuilder unitCost(BigDecimal unitCost) { this.unitCost = unitCost; return this; }
        public BatchDtoBuilder storageLocation(String storageLocation) { this.storageLocation = storageLocation; return this; }
        public BatchDtoBuilder status(Batch.BatchStatus status) { this.status = status; return this; }
        public BatchDtoBuilder daysUntilExpiry(Long daysUntilExpiry) { this.daysUntilExpiry = daysUntilExpiry; return this; }
        public BatchDtoBuilder isExpired(boolean isExpired) { this.isExpired = isExpired; return this; }
        public BatchDtoBuilder isExpiringSoon(boolean isExpiringSoon) { this.isExpiringSoon = isExpiringSoon; return this; }
        public BatchDtoBuilder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }

        public BatchDto build() {
            return new BatchDto(id, medicineId, medicineName, medicineGenericName, medicineSku,
                    batchNumber, initialQuantity, currentQuantity, manufacturingDate, expiryDate,
                    unitCost, storageLocation, status, daysUntilExpiry, isExpired, isExpiringSoon, createdAt);
        }
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getMedicineId() { return medicineId; }
    public void setMedicineId(Long medicineId) { this.medicineId = medicineId; }
    public String getMedicineName() { return medicineName; }
    public void setMedicineName(String medicineName) { this.medicineName = medicineName; }
    public String getMedicineGenericName() { return medicineGenericName; }
    public void setMedicineGenericName(String medicineGenericName) { this.medicineGenericName = medicineGenericName; }
    public String getMedicineSku() { return medicineSku; }
    public void setMedicineSku(String medicineSku) { this.medicineSku = medicineSku; }
    public String getBatchNumber() { return batchNumber; }
    public void setBatchNumber(String batchNumber) { this.batchNumber = batchNumber; }
    public Integer getInitialQuantity() { return initialQuantity; }
    public void setInitialQuantity(Integer initialQuantity) { this.initialQuantity = initialQuantity; }
    public Integer getCurrentQuantity() { return currentQuantity; }
    public void setCurrentQuantity(Integer currentQuantity) { this.currentQuantity = currentQuantity; }
    public LocalDate getManufacturingDate() { return manufacturingDate; }
    public void setManufacturingDate(LocalDate manufacturingDate) { this.manufacturingDate = manufacturingDate; }
    public LocalDate getExpiryDate() { return expiryDate; }
    public void setExpiryDate(LocalDate expiryDate) { this.expiryDate = expiryDate; }
    public BigDecimal getUnitCost() { return unitCost; }
    public void setUnitCost(BigDecimal unitCost) { this.unitCost = unitCost; }
    public String getStorageLocation() { return storageLocation; }
    public void setStorageLocation(String storageLocation) { this.storageLocation = storageLocation; }
    public Batch.BatchStatus getStatus() { return status; }
    public void setStatus(Batch.BatchStatus status) { this.status = status; }
    public Long getDaysUntilExpiry() { return daysUntilExpiry; }
    public void setDaysUntilExpiry(Long daysUntilExpiry) { this.daysUntilExpiry = daysUntilExpiry; }
    public boolean isExpired() { return isExpired; }
    public void setExpired(boolean expired) { isExpired = expired; }
    public boolean isExpiringSoon() { return isExpiringSoon; }
    public void setExpiringSoon(boolean expiringSoon) { isExpiringSoon = expiringSoon; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
