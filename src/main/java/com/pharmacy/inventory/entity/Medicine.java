package com.pharmacy.inventory.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "medicines")
public class Medicine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String genericName;

    @Column(nullable = false, unique = true)
    private String sku;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(nullable = false)
    private String dosageForm;

    private String strength;
    private String manufacturer;
    private BigDecimal unitPrice;

    @Column(nullable = false)
    private Integer minStockThreshold;

    private boolean isCritical = false;
    private String storageCondition;

    @Column(length = 1000)
    private String description;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Medicine() {}

    public Medicine(Long id, String name, String genericName, String sku, Category category,
                    String dosageForm, String strength, String manufacturer, BigDecimal unitPrice,
                    Integer minStockThreshold, boolean isCritical, String storageCondition,
                    String description, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.genericName = genericName;
        this.sku = sku;
        this.category = category;
        this.dosageForm = dosageForm;
        this.strength = strength;
        this.manufacturer = manufacturer;
        this.unitPrice = unitPrice;
        this.minStockThreshold = minStockThreshold;
        this.isCritical = isCritical;
        this.storageCondition = storageCondition;
        this.description = description;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static MedicineBuilder builder() {
        return new MedicineBuilder();
    }

    public static class MedicineBuilder {
        private Long id;
        private String name;
        private String genericName;
        private String sku;
        private Category category;
        private String dosageForm;
        private String strength;
        private String manufacturer;
        private BigDecimal unitPrice;
        private Integer minStockThreshold = 20;
        private boolean isCritical = false;
        private String storageCondition;
        private String description;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        public MedicineBuilder id(Long id) { this.id = id; return this; }
        public MedicineBuilder name(String name) { this.name = name; return this; }
        public MedicineBuilder genericName(String genericName) { this.genericName = genericName; return this; }
        public MedicineBuilder sku(String sku) { this.sku = sku; return this; }
        public MedicineBuilder category(Category category) { this.category = category; return this; }
        public MedicineBuilder dosageForm(String dosageForm) { this.dosageForm = dosageForm; return this; }
        public MedicineBuilder strength(String strength) { this.strength = strength; return this; }
        public MedicineBuilder manufacturer(String manufacturer) { this.manufacturer = manufacturer; return this; }
        public MedicineBuilder unitPrice(BigDecimal unitPrice) { this.unitPrice = unitPrice; return this; }
        public MedicineBuilder minStockThreshold(Integer minStockThreshold) { this.minStockThreshold = minStockThreshold; return this; }
        public MedicineBuilder isCritical(boolean isCritical) { this.isCritical = isCritical; return this; }
        public MedicineBuilder storageCondition(String storageCondition) { this.storageCondition = storageCondition; return this; }
        public MedicineBuilder description(String description) { this.description = description; return this; }
        public MedicineBuilder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }
        public MedicineBuilder updatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; return this; }

        public Medicine build() {
            return new Medicine(id, name, genericName, sku, category, dosageForm, strength,
                    manufacturer, unitPrice, minStockThreshold, isCritical, storageCondition,
                    description, createdAt, updatedAt);
        }
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getGenericName() { return genericName; }
    public void setGenericName(String genericName) { this.genericName = genericName; }
    public String getSku() { return sku; }
    public void setSku(String sku) { this.sku = sku; }
    public Category getCategory() { return category; }
    public void setCategory(Category category) { this.category = category; }
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
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
