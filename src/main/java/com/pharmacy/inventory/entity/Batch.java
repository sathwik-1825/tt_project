package com.pharmacy.inventory.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "batches")
public class Batch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "medicine_id", nullable = false)
    private Medicine medicine;

    @Column(nullable = false)
    private String batchNumber;

    @Column(nullable = false)
    private Integer initialQuantity;

    @Column(nullable = false)
    private Integer currentQuantity;

    private LocalDate manufacturingDate;

    @Column(nullable = false)
    private LocalDate expiryDate;

    private BigDecimal unitCost;
    private String storageLocation;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BatchStatus status = BatchStatus.ACTIVE;

    private LocalDateTime createdAt;

    public enum BatchStatus {
        ACTIVE,
        DEPLETED,
        QUARANTINED,
        EXPIRED
    }

    public Batch() {}

    public Batch(Long id, Medicine medicine, String batchNumber, Integer initialQuantity,
                 Integer currentQuantity, LocalDate manufacturingDate, LocalDate expiryDate,
                 BigDecimal unitCost, String storageLocation, BatchStatus status, LocalDateTime createdAt) {
        this.id = id;
        this.medicine = medicine;
        this.batchNumber = batchNumber;
        this.initialQuantity = initialQuantity;
        this.currentQuantity = currentQuantity;
        this.manufacturingDate = manufacturingDate;
        this.expiryDate = expiryDate;
        this.unitCost = unitCost;
        this.storageLocation = storageLocation;
        this.status = (status != null) ? status : BatchStatus.ACTIVE;
        this.createdAt = createdAt;
    }

    public static BatchBuilder builder() {
        return new BatchBuilder();
    }

    public static class BatchBuilder {
        private Long id;
        private Medicine medicine;
        private String batchNumber;
        private Integer initialQuantity;
        private Integer currentQuantity;
        private LocalDate manufacturingDate;
        private LocalDate expiryDate;
        private BigDecimal unitCost;
        private String storageLocation;
        private BatchStatus status = BatchStatus.ACTIVE;
        private LocalDateTime createdAt;

        public BatchBuilder id(Long id) { this.id = id; return this; }
        public BatchBuilder medicine(Medicine medicine) { this.medicine = medicine; return this; }
        public BatchBuilder batchNumber(String batchNumber) { this.batchNumber = batchNumber; return this; }
        public BatchBuilder initialQuantity(Integer initialQuantity) { this.initialQuantity = initialQuantity; return this; }
        public BatchBuilder currentQuantity(Integer currentQuantity) { this.currentQuantity = currentQuantity; return this; }
        public BatchBuilder manufacturingDate(LocalDate manufacturingDate) { this.manufacturingDate = manufacturingDate; return this; }
        public BatchBuilder expiryDate(LocalDate expiryDate) { this.expiryDate = expiryDate; return this; }
        public BatchBuilder unitCost(BigDecimal unitCost) { this.unitCost = unitCost; return this; }
        public BatchBuilder storageLocation(String storageLocation) { this.storageLocation = storageLocation; return this; }
        public BatchBuilder status(BatchStatus status) { this.status = status; return this; }
        public BatchBuilder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }

        public Batch build() {
            return new Batch(id, medicine, batchNumber, initialQuantity,
                    (currentQuantity != null ? currentQuantity : initialQuantity),
                    manufacturingDate, expiryDate, unitCost, storageLocation, status, createdAt);
        }
    }

    @PrePersist
    protected void onCreate() {
        if (this.createdAt == null) {
            this.createdAt = LocalDateTime.now();
        }
        if (this.currentQuantity == null) {
            this.currentQuantity = this.initialQuantity;
        }
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Medicine getMedicine() { return medicine; }
    public void setMedicine(Medicine medicine) { this.medicine = medicine; }
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
    public BatchStatus getStatus() { return status; }
    public void setStatus(BatchStatus status) { this.status = status; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
