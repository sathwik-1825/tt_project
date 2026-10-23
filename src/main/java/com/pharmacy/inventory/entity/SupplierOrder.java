package com.pharmacy.inventory.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "supplier_orders")
public class SupplierOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String orderNumber;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "medicine_id", nullable = false)
    private Medicine medicine;

    @Column(nullable = false)
    private Integer quantity;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "supplier_user_id")
    private User supplier;

    private String supplierCompanyName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status = OrderStatus.PLACED;

    private String issuedBatchNumber;
    private LocalDate issuedExpiryDate;
    private LocalDate issuedManufacturingDate;

    private LocalDateTime orderedAt;
    private LocalDateTime deliveredAt;

    private String notes;

    public enum OrderStatus {
        PLACED,
        SHIPPED,
        DELIVERED,
        CANCELLED
    }

    public SupplierOrder() {}

    public SupplierOrder(Long id, String orderNumber, Medicine medicine, Integer quantity,
                         User supplier, String supplierCompanyName, OrderStatus status,
                         String issuedBatchNumber, LocalDate issuedExpiryDate,
                         LocalDate issuedManufacturingDate, LocalDateTime orderedAt,
                         LocalDateTime deliveredAt, String notes) {
        this.id = id;
        this.orderNumber = orderNumber;
        this.medicine = medicine;
        this.quantity = quantity;
        this.supplier = supplier;
        this.supplierCompanyName = supplierCompanyName;
        this.status = (status != null) ? status : OrderStatus.PLACED;
        this.issuedBatchNumber = issuedBatchNumber;
        this.issuedExpiryDate = issuedExpiryDate;
        this.issuedManufacturingDate = issuedManufacturingDate;
        this.orderedAt = orderedAt;
        this.deliveredAt = deliveredAt;
        this.notes = notes;
    }

    public static SupplierOrderBuilder builder() {
        return new SupplierOrderBuilder();
    }

    public static class SupplierOrderBuilder {
        private Long id;
        private String orderNumber;
        private Medicine medicine;
        private Integer quantity;
        private User supplier;
        private String supplierCompanyName;
        private OrderStatus status = OrderStatus.PLACED;
        private String issuedBatchNumber;
        private LocalDate issuedExpiryDate;
        private LocalDate issuedManufacturingDate;
        private LocalDateTime orderedAt;
        private LocalDateTime deliveredAt;
        private String notes;

        public SupplierOrderBuilder id(Long id) { this.id = id; return this; }
        public SupplierOrderBuilder orderNumber(String orderNumber) { this.orderNumber = orderNumber; return this; }
        public SupplierOrderBuilder medicine(Medicine medicine) { this.medicine = medicine; return this; }
        public SupplierOrderBuilder quantity(Integer quantity) { this.quantity = quantity; return this; }
        public SupplierOrderBuilder supplier(User supplier) { this.supplier = supplier; return this; }
        public SupplierOrderBuilder supplierCompanyName(String supplierCompanyName) { this.supplierCompanyName = supplierCompanyName; return this; }
        public SupplierOrderBuilder status(OrderStatus status) { this.status = status; return this; }
        public SupplierOrderBuilder issuedBatchNumber(String issuedBatchNumber) { this.issuedBatchNumber = issuedBatchNumber; return this; }
        public SupplierOrderBuilder issuedExpiryDate(LocalDate issuedExpiryDate) { this.issuedExpiryDate = issuedExpiryDate; return this; }
        public SupplierOrderBuilder issuedManufacturingDate(LocalDate issuedManufacturingDate) { this.issuedManufacturingDate = issuedManufacturingDate; return this; }
        public SupplierOrderBuilder orderedAt(LocalDateTime orderedAt) { this.orderedAt = orderedAt; return this; }
        public SupplierOrderBuilder deliveredAt(LocalDateTime deliveredAt) { this.deliveredAt = deliveredAt; return this; }
        public SupplierOrderBuilder notes(String notes) { this.notes = notes; return this; }

        public SupplierOrder build() {
            return new SupplierOrder(id, orderNumber, medicine, quantity, supplier,
                    supplierCompanyName, status, issuedBatchNumber, issuedExpiryDate,
                    issuedManufacturingDate, orderedAt, deliveredAt, notes);
        }
    }

    @PrePersist
    protected void onCreate() {
        if (this.orderedAt == null) {
            this.orderedAt = LocalDateTime.now();
        }
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getOrderNumber() { return orderNumber; }
    public void setOrderNumber(String orderNumber) { this.orderNumber = orderNumber; }
    public Medicine getMedicine() { return medicine; }
    public void setMedicine(Medicine medicine) { this.medicine = medicine; }
    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
    public User getSupplier() { return supplier; }
    public void setSupplier(User supplier) { this.supplier = supplier; }
    public String getSupplierCompanyName() { return supplierCompanyName; }
    public void setSupplierCompanyName(String supplierCompanyName) { this.supplierCompanyName = supplierCompanyName; }
    public OrderStatus getStatus() { return status; }
    public void setStatus(OrderStatus status) { this.status = status; }
    public String getIssuedBatchNumber() { return issuedBatchNumber; }
    public void setIssuedBatchNumber(String issuedBatchNumber) { this.issuedBatchNumber = issuedBatchNumber; }
    public LocalDate getIssuedExpiryDate() { return issuedExpiryDate; }
    public void setIssuedExpiryDate(LocalDate issuedExpiryDate) { this.issuedExpiryDate = issuedExpiryDate; }
    public LocalDate getIssuedManufacturingDate() { return issuedManufacturingDate; }
    public void setIssuedManufacturingDate(LocalDate issuedManufacturingDate) { this.issuedManufacturingDate = issuedManufacturingDate; }
    public LocalDateTime getOrderedAt() { return orderedAt; }
    public void setOrderedAt(LocalDateTime orderedAt) { this.orderedAt = orderedAt; }
    public LocalDateTime getDeliveredAt() { return deliveredAt; }
    public void setDeliveredAt(LocalDateTime deliveredAt) { this.deliveredAt = deliveredAt; }
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
}
