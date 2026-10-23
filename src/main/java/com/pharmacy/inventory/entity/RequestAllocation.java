package com.pharmacy.inventory.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "request_allocations")
public class RequestAllocation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "request_id", nullable = false)
    @JsonIgnore
    private EmergencyRequest emergencyRequest;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "batch_id", nullable = false)
    private Batch batch;

    @Column(nullable = false)
    private Integer allocatedQuantity;

    private LocalDateTime allocatedAt;

    public RequestAllocation() {}

    public RequestAllocation(Long id, EmergencyRequest emergencyRequest, Batch batch,
                             Integer allocatedQuantity, LocalDateTime allocatedAt) {
        this.id = id;
        this.emergencyRequest = emergencyRequest;
        this.batch = batch;
        this.allocatedQuantity = allocatedQuantity;
        this.allocatedAt = allocatedAt;
    }

    public static RequestAllocationBuilder builder() {
        return new RequestAllocationBuilder();
    }

    public static class RequestAllocationBuilder {
        private Long id;
        private EmergencyRequest emergencyRequest;
        private Batch batch;
        private Integer allocatedQuantity;
        private LocalDateTime allocatedAt;

        public RequestAllocationBuilder id(Long id) { this.id = id; return this; }
        public RequestAllocationBuilder emergencyRequest(EmergencyRequest emergencyRequest) { this.emergencyRequest = emergencyRequest; return this; }
        public RequestAllocationBuilder batch(Batch batch) { this.batch = batch; return this; }
        public RequestAllocationBuilder allocatedQuantity(Integer allocatedQuantity) { this.allocatedQuantity = allocatedQuantity; return this; }
        public RequestAllocationBuilder allocatedAt(LocalDateTime allocatedAt) { this.allocatedAt = allocatedAt; return this; }

        public RequestAllocation build() {
            return new RequestAllocation(id, emergencyRequest, batch, allocatedQuantity, allocatedAt);
        }
    }

    @PrePersist
    protected void onCreate() {
        if (this.allocatedAt == null) {
            this.allocatedAt = LocalDateTime.now();
        }
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public EmergencyRequest getEmergencyRequest() { return emergencyRequest; }
    public void setEmergencyRequest(EmergencyRequest emergencyRequest) { this.emergencyRequest = emergencyRequest; }
    public Batch getBatch() { return batch; }
    public void setBatch(Batch batch) { this.batch = batch; }
    public Integer getAllocatedQuantity() { return allocatedQuantity; }
    public void setAllocatedQuantity(Integer allocatedQuantity) { this.allocatedQuantity = allocatedQuantity; }
    public LocalDateTime getAllocatedAt() { return allocatedAt; }
    public void setAllocatedAt(LocalDateTime allocatedAt) { this.allocatedAt = allocatedAt; }
}
