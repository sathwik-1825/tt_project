package com.pharmacy.inventory.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class MedicineDto {
    private Long id;

    @NotBlank(message = "Medicine name is required")
    private String name;

    @NotBlank(message = "Generic name is required")
    private String genericName;

    @NotBlank(message = "SKU is required")
    private String sku;

    private Long categoryId;
    private String categoryName;

    @NotBlank(message = "Dosage form is required")
    private String dosageForm;

    private String strength;
    private String manufacturer;
    private BigDecimal unitPrice;

    @NotNull(message = "Minimum stock threshold is required")
    private Integer minStockThreshold;

    private boolean isCritical;
    private String storageCondition;
    private String description;

    private Integer totalActiveStock;
    private Integer totalBatchesCount;
    private String stockStatus;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public MedicineDto() {}

    public MedicineDto(Long id, String name, String genericName, String sku, Long categoryId,
                       String categoryName, String dosageForm, String strength, String manufacturer,
                       BigDecimal unitPrice, Integer minStockThreshold, boolean isCritical,
                       String storageCondition, String description, Integer totalActiveStock,
                       Integer totalBatchesCount, String stockStatus, LocalDateTime createdAt,
                       LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.genericName = genericName;
        this.sku = sku;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.dosageForm = dosageForm;
        this.strength = strength;
        this.manufacturer = manufacturer;
        this.unitPrice = unitPrice;
        this.minStockThreshold = minStockThreshold;
        this.isCritical = isCritical;
        this.storageCondition = storageCondition;
        this.description = description;
        this.totalActiveStock = totalActiveStock;
        this.totalBatchesCount = totalBatchesCount;
        this.stockStatus = stockStatus;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static MedicineDtoBuilder builder() {
        return new MedicineDtoBuilder();
    }

    public static class MedicineDtoBuilder {
        private Long id;
        private String name;
        private String genericName;
        private String sku;
        private Long categoryId;
        private String categoryName;
        private String dosageForm;
        private String strength;
        private String manufacturer;
        private BigDecimal unitPrice;
        private Integer minStockThreshold;
        private boolean isCritical;
        private String storageCondition;
        private String description;
        private Integer totalActiveStock;
        private Integer totalBatchesCount;
        private String stockStatus;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        public MedicineDtoBuilder id(Long id) { this.id = id; return this; }
        public MedicineDtoBuilder name(String name) { this.name = name; return this; }
        public MedicineDtoBuilder genericName(String genericName) { this.genericName = genericName; return this; }
        public MedicineDtoBuilder sku(String sku) { this.sku = sku; return this; }
        public MedicineDtoBuilder categoryId(Long categoryId) { this.categoryId = categoryId; return this; }
        public MedicineDtoBuilder categoryName(String categoryName) { this.categoryName = categoryName; return this; }
        public MedicineDtoBuilder dosageForm(String dosageForm) { this.dosageForm = dosageForm; return this; }
        public MedicineDtoBuilder strength(String strength) { this.strength = strength; return this; }
        public MedicineDtoBuilder manufacturer(String manufacturer) { this.manufacturer = manufacturer; return this; }
        public MedicineDtoBuilder unitPrice(BigDecimal unitPrice) { this.unitPrice = unitPrice; return this; }
        public MedicineDtoBuilder minStockThreshold(Integer minStockThreshold) { this.minStockThreshold = minStockThreshold; return this; }
        public MedicineDtoBuilder isCritical(boolean isCritical) { this.isCritical = isCritical; return this; }
        public MedicineDtoBuilder storageCondition(String storageCondition) { this.storageCondition = storageCondition; return this; }
        public MedicineDtoBuilder description(String description) { this.description = description; return this; }
        public MedicineDtoBuilder totalActiveStock(Integer totalActiveStock) { this.totalActiveStock = totalActiveStock; return this; }
        public MedicineDtoBuilder totalBatchesCount(Integer totalBatchesCount) { this.totalBatchesCount = totalBatchesCount; return this; }
        public MedicineDtoBuilder stockStatus(String stockStatus) { this.stockStatus = stockStatus; return this; }
        public MedicineDtoBuilder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }
        public MedicineDtoBuilder updatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; return this; }

        public MedicineDto build() {
            return new MedicineDto(id, name, genericName, sku, categoryId, categoryName, dosageForm,
                    strength, manufacturer, unitPrice, minStockThreshold, isCritical, storageCondition,
                    description, totalActiveStock, totalBatchesCount, stockStatus, createdAt, updatedAt);
        }
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getGenericName() { return genericName; }
    public void setGenericName(String genericName) { this.genericName = genericName; }
    public String getSku() { return sku; }
    public void setSku(String sku) { this.sku = sku; }
    public Long getCategoryId() { return categoryId; }
    public void setCategoryId(Long categoryId) { this.categoryId = categoryId; }
    public String getCategoryName() { return categoryName; }
    public void setCategoryName(String categoryName) { this.categoryName = categoryName; }
    public String getDosageForm() { return dosageForm; }
    public void setDosageForm(String dosageForm) { this.dosageForm = dosageForm; }
    public String getStrength() { return strength; }
    public void setStrength(String strength) { this.strength = strength; }
    public String getManufacturer() { return manufacturer; }
    public void setManufacturer(String manufacturer) { this.manufacturer = manufacturer; }
    public BigDecimal getUnitPrice() { return unitPrice; }
    public void setUnitPrice(BigDecimal unitPrice) { this.unitPrice = unitPrice; }
    public Integer getMinStockThreshold() { return minStockThreshold; }
    public void setMinStockThreshold(Integer minStockThreshold) { this.minStockThreshold = minStockThreshold; }
    public boolean isCritical() { return isCritical; }
    public void setCritical(boolean isCritical) { this.isCritical = isCritical; }
    public String getStorageCondition() { return storageCondition; }
    public void setStorageCondition(String storageCondition) { this.storageCondition = storageCondition; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public Integer getTotalActiveStock() { return totalActiveStock; }
    public void setTotalActiveStock(Integer totalActiveStock) { this.totalActiveStock = totalActiveStock; }
    public Integer getTotalBatchesCount() { return totalBatchesCount; }
    public void setTotalBatchesCount(Integer totalBatchesCount) { this.totalBatchesCount = totalBatchesCount; }
    public String getStockStatus() { return stockStatus; }
    public void setStockStatus(String stockStatus) { this.stockStatus = stockStatus; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
