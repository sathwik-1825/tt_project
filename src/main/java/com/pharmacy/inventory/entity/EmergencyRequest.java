package com.pharmacy.inventory.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "emergency_requests")
public class EmergencyRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String requestNumber;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "medicine_id", nullable = false)
    private Medicine medicine;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "requested_by_user_id", nullable = false)
    private User requestedBy;

    @Column(nullable = false)
    private String department;

    @Column(nullable = false)
    private Integer requestedQuantity;

    private Integer allocatedQuantity = 0;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UrgencyLevel urgencyLevel;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RequestStatus status = RequestStatus.PENDING;

    @Column(length = 1000)
    private String clinicalReason;

    private String bedOrPatientId;
    private LocalDateTime requiredBy;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "approved_by_user_id")
    private User approvedBy;

    @Column(length = 1000)
    private String pharmacistNotes;

    @OneToMany(mappedBy = "emergencyRequest", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RequestAllocation> allocations = new ArrayList<>();

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public enum UrgencyLevel {
        CRITICAL,
        HIGH,
        MEDIUM
    }

    public enum RequestStatus {
        PENDING,
        APPROVED,
        ALLOCATED,
        DISPATCHED,
        DELIVERED,
        REJECTED,
        CANCELLED
    }

    public EmergencyRequest() {}

    public EmergencyRequest(Long id, String requestNumber, Medicine medicine, User requestedBy,
                            String department, Integer requestedQuantity, Integer allocatedQuantity,
                            UrgencyLevel urgencyLevel, RequestStatus status, String clinicalReason,
                            String bedOrPatientId, LocalDateTime requiredBy, User approvedBy,
                            String pharmacistNotes, List<RequestAllocation> allocations,
                            LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.requestNumber = requestNumber;
        this.medicine = medicine;
        this.requestedBy = requestedBy;
        this.department = department;
        this.requestedQuantity = requestedQuantity;
        this.allocatedQuantity = (allocatedQuantity != null) ? allocatedQuantity : 0;
        this.urgencyLevel = urgencyLevel;
        this.status = (status != null) ? status : RequestStatus.PENDING;
        this.clinicalReason = clinicalReason;
        this.bedOrPatientId = bedOrPatientId;
        this.requiredBy = requiredBy;
        this.approvedBy = approvedBy;
        this.pharmacistNotes = pharmacistNotes;
        this.allocations = (allocations != null) ? allocations : new ArrayList<>();
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static EmergencyRequestBuilder builder() {
        return new EmergencyRequestBuilder();
    }

    public static class EmergencyRequestBuilder {
        private Long id;
        private String requestNumber;
        private Medicine medicine;
        private User requestedBy;
        private String department;
        private Integer requestedQuantity;
        private Integer allocatedQuantity = 0;
        private UrgencyLevel urgencyLevel;
        private RequestStatus status = RequestStatus.PENDING;
        private String clinicalReason;
        private String bedOrPatientId;
        private LocalDateTime requiredBy;
        private User approvedBy;
        private String pharmacistNotes;
        private List<RequestAllocation> allocations = new ArrayList<>();
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        public EmergencyRequestBuilder id(Long id) { this.id = id; return this; }
        public EmergencyRequestBuilder requestNumber(String requestNumber) { this.requestNumber = requestNumber; return this; }
        public EmergencyRequestBuilder medicine(Medicine medicine) { this.medicine = medicine; return this; }
        public EmergencyRequestBuilder requestedBy(User requestedBy) { this.requestedBy = requestedBy; return this; }
        public EmergencyRequestBuilder department(String department) { this.department = department; return this; }
        public EmergencyRequestBuilder requestedQuantity(Integer requestedQuantity) { this.requestedQuantity = requestedQuantity; return this; }
        public EmergencyRequestBuilder allocatedQuantity(Integer allocatedQuantity) { this.allocatedQuantity = allocatedQuantity; return this; }
        public EmergencyRequestBuilder urgencyLevel(UrgencyLevel urgencyLevel) { this.urgencyLevel = urgencyLevel; return this; }
        public EmergencyRequestBuilder status(RequestStatus status) { this.status = status; return this; }
        public EmergencyRequestBuilder clinicalReason(String clinicalReason) { this.clinicalReason = clinicalReason; return this; }
        public EmergencyRequestBuilder bedOrPatientId(String bedOrPatientId) { this.bedOrPatientId = bedOrPatientId; return this; }
        public EmergencyRequestBuilder requiredBy(LocalDateTime requiredBy) { this.requiredBy = requiredBy; return this; }
        public EmergencyRequestBuilder approvedBy(User approvedBy) { this.approvedBy = approvedBy; return this; }
        public EmergencyRequestBuilder pharmacistNotes(String pharmacistNotes) { this.pharmacistNotes = pharmacistNotes; return this; }
        public EmergencyRequestBuilder allocations(List<RequestAllocation> allocations) { this.allocations = allocations; return this; }
        public EmergencyRequestBuilder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }
        public EmergencyRequestBuilder updatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; return this; }

        public EmergencyRequest build() {
            return new EmergencyRequest(id, requestNumber, medicine, requestedBy, department,
                    requestedQuantity, allocatedQuantity, urgencyLevel, status, clinicalReason,
                    bedOrPatientId, requiredBy, approvedBy, pharmacistNotes, allocations,
                    createdAt, updatedAt);
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
    public String getRequestNumber() { return requestNumber; }
    public void setRequestNumber(String requestNumber) { this.requestNumber = requestNumber; }
    public Medicine getMedicine() { return medicine; }
    public void setMedicine(Medicine medicine) { this.medicine = medicine; }
    public User getRequestedBy() { return requestedBy; }
    public void setRequestedBy(User requestedBy) { this.requestedBy = requestedBy; }
    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }
    public Integer getRequestedQuantity() { return requestedQuantity; }
    public void setRequestedQuantity(Integer requestedQuantity) { this.requestedQuantity = requestedQuantity; }
    public Integer getAllocatedQuantity() { return allocatedQuantity; }
    public void setAllocatedQuantity(Integer allocatedQuantity) { this.allocatedQuantity = allocatedQuantity; }
    public UrgencyLevel getUrgencyLevel() { return urgencyLevel; }
    public void setUrgencyLevel(UrgencyLevel urgencyLevel) { this.urgencyLevel = urgencyLevel; }
    public RequestStatus getStatus() { return status; }
    public void setStatus(RequestStatus status) { this.status = status; }
    public String getClinicalReason() { return clinicalReason; }
    public void setClinicalReason(String clinicalReason) { this.clinicalReason = clinicalReason; }
    public String getBedOrPatientId() { return bedOrPatientId; }
    public void setBedOrPatientId(String bedOrPatientId) { this.bedOrPatientId = bedOrPatientId; }
    public LocalDateTime getRequiredBy() { return requiredBy; }
    public void setRequiredBy(LocalDateTime requiredBy) { this.requiredBy = requiredBy; }
    public User getApprovedBy() { return approvedBy; }
    public void setApprovedBy(User approvedBy) { this.approvedBy = approvedBy; }
    public String getPharmacistNotes() { return pharmacistNotes; }
    public void setPharmacistNotes(String pharmacistNotes) { this.pharmacistNotes = pharmacistNotes; }
    public List<RequestAllocation> getAllocations() { return allocations; }
    public void setAllocations(List<RequestAllocation> allocations) { this.allocations = allocations; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
