package com.pharmacy.inventory.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "stock_movements")
public class StockMovement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "medicine_id", nullable = false)
    private Medicine medicine;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "batch_id")
    private Batch batch;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MovementType movementType;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private Integer balanceAfter;

    private String referenceNumber;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "performed_by_user_id")
    private User performedBy;

    private String remarks;

    @Column(nullable = false)
    private LocalDateTime timestamp;

    public enum MovementType {
        INWARD,
        DISPATCH,
        RETURN,
        AUDIT_ADJUSTMENT,
        EXPIRED_DISPOSAL
    }

    public StockMovement() {}

    public StockMovement(Long id, Medicine medicine, Batch batch, MovementType movementType,
                         Integer quantity, Integer balanceAfter, String referenceNumber,
                         User performedBy, String remarks, LocalDateTime timestamp) {
        this.id = id;
        this.medicine = medicine;
        this.batch = batch;
        this.movementType = movementType;
        this.quantity = quantity;
        this.balanceAfter = balanceAfter;
        this.referenceNumber = referenceNumber;
        this.performedBy = performedBy;
        this.remarks = remarks;
        this.timestamp = timestamp;
    }

    public static StockMovementBuilder builder() {
        return new StockMovementBuilder();
    }

    public static class StockMovementBuilder {
        private Long id;
        private Medicine medicine;
        private Batch batch;
        private MovementType movementType;
        private Integer quantity;
        private Integer balanceAfter;
        private String referenceNumber;
        private User performedBy;
        private String remarks;
        private LocalDateTime timestamp;

        public StockMovementBuilder id(Long id) { this.id = id; return this; }
        public StockMovementBuilder medicine(Medicine medicine) { this.medicine = medicine; return this; }
        public StockMovementBuilder batch(Batch batch) { this.batch = batch; return this; }
        public StockMovementBuilder movementType(MovementType movementType) { this.movementType = movementType; return this; }
        public StockMovementBuilder quantity(Integer quantity) { this.quantity = quantity; return this; }
        public StockMovementBuilder balanceAfter(Integer balanceAfter) { this.balanceAfter = balanceAfter; return this; }
        public StockMovementBuilder referenceNumber(String referenceNumber) { this.referenceNumber = referenceNumber; return this; }
        public StockMovementBuilder performedBy(User performedBy) { this.performedBy = performedBy; return this; }
        public StockMovementBuilder remarks(String remarks) { this.remarks = remarks; return this; }
        public StockMovementBuilder timestamp(LocalDateTime timestamp) { this.timestamp = timestamp; return this; }

        public StockMovement build() {
            return new StockMovement(id, medicine, batch, movementType, quantity, balanceAfter,
                    referenceNumber, performedBy, remarks, timestamp);
        }
    }

    @PrePersist
    protected void onCreate() {
        if (this.timestamp == null) {
            this.timestamp = LocalDateTime.now();
        }
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Medicine getMedicine() { return medicine; }
    public void setMedicine(Medicine medicine) { this.medicine = medicine; }
    public Batch getBatch() { return batch; }
    public void setBatch(Batch batch) { this.batch = batch; }
    public MovementType getMovementType() { return movementType; }
    public void setMovementType(MovementType movementType) { this.movementType = movementType; }
    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
    public Integer getBalanceAfter() { return balanceAfter; }
    public void setBalanceAfter(Integer balanceAfter) { this.balanceAfter = balanceAfter; }
    public String getReferenceNumber() { return referenceNumber; }
    public void setReferenceNumber(String referenceNumber) { this.referenceNumber = referenceNumber; }
    public User getPerformedBy() { return performedBy; }
    public void setPerformedBy(User performedBy) { this.performedBy = performedBy; }
    public String getRemarks() { return remarks; }
    public void setRemarks(String remarks) { this.remarks = remarks; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
}
