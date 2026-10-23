package com.pharmacy.inventory.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "dispatch_records")
public class DispatchRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String dispatchNumber;

    @OneToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "request_id", nullable = false)
    private EmergencyRequest emergencyRequest;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "dispatched_by_user_id", nullable = false)
    private User dispatchedBy;

    @Column(nullable = false)
    private LocalDateTime dispatchedAt;

    private String carrierName;

    @Column(nullable = false)
    private String destinationDepartment;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DispatchStatus status = DispatchStatus.IN_TRANSIT;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "received_by_user_id")
    private User receivedBy;

    private LocalDateTime receivedAt;

    @Column(length = 1000)
    private String handlingInstructions;

    private String verificationCode;

    public enum DispatchStatus {
        IN_TRANSIT,
        DELIVERED,
        RETURNED
    }

    public DispatchRecord() {}

    public DispatchRecord(Long id, String dispatchNumber, EmergencyRequest emergencyRequest,
                          User dispatchedBy, LocalDateTime dispatchedAt, String carrierName,
                          String destinationDepartment, DispatchStatus status, User receivedBy,
                          LocalDateTime receivedAt, String handlingInstructions, String verificationCode) {
        this.id = id;
        this.dispatchNumber = dispatchNumber;
        this.emergencyRequest = emergencyRequest;
        this.dispatchedBy = dispatchedBy;
        this.dispatchedAt = dispatchedAt;
        this.carrierName = carrierName;
        this.destinationDepartment = destinationDepartment;
        this.status = (status != null) ? status : DispatchStatus.IN_TRANSIT;
        this.receivedBy = receivedBy;
        this.receivedAt = receivedAt;
        this.handlingInstructions = handlingInstructions;
        this.verificationCode = verificationCode;
    }

    public static DispatchRecordBuilder builder() {
        return new DispatchRecordBuilder();
    }

    public static class DispatchRecordBuilder {
        private Long id;
        private String dispatchNumber;
        private EmergencyRequest emergencyRequest;
        private User dispatchedBy;
        private LocalDateTime dispatchedAt;
        private String carrierName;
        private String destinationDepartment;
        private DispatchStatus status = DispatchStatus.IN_TRANSIT;
        private User receivedBy;
        private LocalDateTime receivedAt;
        private String handlingInstructions;
        private String verificationCode;

        public DispatchRecordBuilder id(Long id) { this.id = id; return this; }
        public DispatchRecordBuilder dispatchNumber(String dispatchNumber) { this.dispatchNumber = dispatchNumber; return this; }
        public DispatchRecordBuilder emergencyRequest(EmergencyRequest emergencyRequest) { this.emergencyRequest = emergencyRequest; return this; }
        public DispatchRecordBuilder dispatchedBy(User dispatchedBy) { this.dispatchedBy = dispatchedBy; return this; }
        public DispatchRecordBuilder dispatchedAt(LocalDateTime dispatchedAt) { this.dispatchedAt = dispatchedAt; return this; }
        public DispatchRecordBuilder carrierName(String carrierName) { this.carrierName = carrierName; return this; }
        public DispatchRecordBuilder destinationDepartment(String destinationDepartment) { this.destinationDepartment = destinationDepartment; return this; }
        public DispatchRecordBuilder status(DispatchStatus status) { this.status = status; return this; }
        public DispatchRecordBuilder receivedBy(User receivedBy) { this.receivedBy = receivedBy; return this; }
        public DispatchRecordBuilder receivedAt(LocalDateTime receivedAt) { this.receivedAt = receivedAt; return this; }
        public DispatchRecordBuilder handlingInstructions(String handlingInstructions) { this.handlingInstructions = handlingInstructions; return this; }
        public DispatchRecordBuilder verificationCode(String verificationCode) { this.verificationCode = verificationCode; return this; }

        public DispatchRecord build() {
            return new DispatchRecord(id, dispatchNumber, emergencyRequest, dispatchedBy,
                    dispatchedAt, carrierName, destinationDepartment, status, receivedBy,
                    receivedAt, handlingInstructions, verificationCode);
        }
    }

    @PrePersist
    protected void onCreate() {
        if (this.dispatchedAt == null) {
            this.dispatchedAt = LocalDateTime.now();
        }
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getDispatchNumber() { return dispatchNumber; }
    public void setDispatchNumber(String dispatchNumber) { this.dispatchNumber = dispatchNumber; }
    public EmergencyRequest getEmergencyRequest() { return emergencyRequest; }
    public void setEmergencyRequest(EmergencyRequest emergencyRequest) { this.emergencyRequest = emergencyRequest; }
    public User getDispatchedBy() { return dispatchedBy; }
    public void setDispatchedBy(User dispatchedBy) { this.dispatchedBy = dispatchedBy; }
    public LocalDateTime getDispatchedAt() { return dispatchedAt; }
    public void setDispatchedAt(LocalDateTime dispatchedAt) { this.dispatchedAt = dispatchedAt; }
    public String getCarrierName() { return carrierName; }
    public void setCarrierName(String carrierName) { this.carrierName = carrierName; }
    public String getDestinationDepartment() { return destinationDepartment; }
    public void setDestinationDepartment(String destinationDepartment) { this.destinationDepartment = destinationDepartment; }
    public DispatchStatus getStatus() { return status; }
    public void setStatus(DispatchStatus status) { this.status = status; }
    public User getReceivedBy() { return receivedBy; }
    public void setReceivedBy(User receivedBy) { this.receivedBy = receivedBy; }
    public LocalDateTime getReceivedAt() { return receivedAt; }
    public void setReceivedAt(LocalDateTime receivedAt) { this.receivedAt = receivedAt; }
    public String getHandlingInstructions() { return handlingInstructions; }
    public void setHandlingInstructions(String handlingInstructions) { this.handlingInstructions = handlingInstructions; }
    public String getVerificationCode() { return verificationCode; }
    public void setVerificationCode(String verificationCode) { this.verificationCode = verificationCode; }
}
