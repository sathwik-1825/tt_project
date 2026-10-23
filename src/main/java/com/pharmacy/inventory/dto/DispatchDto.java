package com.pharmacy.inventory.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public class DispatchDto {
    private Long id;
    private String dispatchNumber;

    @NotNull(message = "Emergency Request ID is required")
    private Long emergencyRequestId;

    private String medicineName;
    private Integer allocatedQuantity;
    private String dispatchedByUserName;
    private LocalDateTime dispatchedAt;

    @NotBlank(message = "Carrier / Porter name is required")
    private String carrierName;

    @NotBlank(message = "Destination department is required")
    private String destinationDepartment;

    private String status;
    private String receivedByUserName;
    private LocalDateTime receivedAt;
    private String handlingInstructions;
    private String verificationCode;

    public DispatchDto() {}

    public DispatchDto(Long id, String dispatchNumber, Long emergencyRequestId, String medicineName,
                       Integer allocatedQuantity, String dispatchedByUserName, LocalDateTime dispatchedAt,
                       String carrierName, String destinationDepartment, String status,
                       String receivedByUserName, LocalDateTime receivedAt, String handlingInstructions,
                       String verificationCode) {
        this.id = id;
        this.dispatchNumber = dispatchNumber;
        this.emergencyRequestId = emergencyRequestId;
        this.medicineName = medicineName;
        this.allocatedQuantity = allocatedQuantity;
        this.dispatchedByUserName = dispatchedByUserName;
        this.dispatchedAt = dispatchedAt;
        this.carrierName = carrierName;
        this.destinationDepartment = destinationDepartment;
        this.status = status;
        this.receivedByUserName = receivedByUserName;
        this.receivedAt = receivedAt;
        this.handlingInstructions = handlingInstructions;
        this.verificationCode = verificationCode;
    }

    public static DispatchDtoBuilder builder() {
        return new DispatchDtoBuilder();
    }

    public static class DispatchDtoBuilder {
        private Long id;
        private String dispatchNumber;
        private Long emergencyRequestId;
        private String medicineName;
        private Integer allocatedQuantity;
        private String dispatchedByUserName;
        private LocalDateTime dispatchedAt;
        private String carrierName;
        private String destinationDepartment;
        private String status;
        private String receivedByUserName;
        private LocalDateTime receivedAt;
        private String handlingInstructions;
        private String verificationCode;

        public DispatchDtoBuilder id(Long id) { this.id = id; return this; }
        public DispatchDtoBuilder dispatchNumber(String dispatchNumber) { this.dispatchNumber = dispatchNumber; return this; }
        public DispatchDtoBuilder emergencyRequestId(Long emergencyRequestId) { this.emergencyRequestId = emergencyRequestId; return this; }
        public DispatchDtoBuilder medicineName(String medicineName) { this.medicineName = medicineName; return this; }
        public DispatchDtoBuilder allocatedQuantity(Integer allocatedQuantity) { this.allocatedQuantity = allocatedQuantity; return this; }
        public DispatchDtoBuilder dispatchedByUserName(String dispatchedByUserName) { this.dispatchedByUserName = dispatchedByUserName; return this; }
        public DispatchDtoBuilder dispatchedAt(LocalDateTime dispatchedAt) { this.dispatchedAt = dispatchedAt; return this; }
        public DispatchDtoBuilder carrierName(String carrierName) { this.carrierName = carrierName; return this; }
        public DispatchDtoBuilder destinationDepartment(String destinationDepartment) { this.destinationDepartment = destinationDepartment; return this; }
        public DispatchDtoBuilder status(String status) { this.status = status; return this; }
        public DispatchDtoBuilder receivedByUserName(String receivedByUserName) { this.receivedByUserName = receivedByUserName; return this; }
        public DispatchDtoBuilder receivedAt(LocalDateTime receivedAt) { this.receivedAt = receivedAt; return this; }
        public DispatchDtoBuilder handlingInstructions(String handlingInstructions) { this.handlingInstructions = handlingInstructions; return this; }
        public DispatchDtoBuilder verificationCode(String verificationCode) { this.verificationCode = verificationCode; return this; }

        public DispatchDto build() {
            return new DispatchDto(id, dispatchNumber, emergencyRequestId, medicineName, allocatedQuantity,
                    dispatchedByUserName, dispatchedAt, carrierName, destinationDepartment, status,
                    receivedByUserName, receivedAt, handlingInstructions, verificationCode);
        }
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getDispatchNumber() { return dispatchNumber; }
    public void setDispatchNumber(String dispatchNumber) { this.dispatchNumber = dispatchNumber; }
    public Long getEmergencyRequestId() { return emergencyRequestId; }
    public void setEmergencyRequestId(Long emergencyRequestId) { this.emergencyRequestId = emergencyRequestId; }
    public String getMedicineName() { return medicineName; }
    public void setMedicineName(String medicineName) { this.medicineName = medicineName; }
    public Integer getAllocatedQuantity() { return allocatedQuantity; }
    public void setAllocatedQuantity(Integer allocatedQuantity) { this.allocatedQuantity = allocatedQuantity; }
    public String getDispatchedByUserName() { return dispatchedByUserName; }
    public void setDispatchedByUserName(String dispatchedByUserName) { this.dispatchedByUserName = dispatchedByUserName; }
    public LocalDateTime getDispatchedAt() { return dispatchedAt; }
    public void setDispatchedAt(LocalDateTime dispatchedAt) { this.dispatchedAt = dispatchedAt; }
    public String getCarrierName() { return carrierName; }
    public void setCarrierName(String carrierName) { this.carrierName = carrierName; }
    public String getDestinationDepartment() { return destinationDepartment; }
    public void setDestinationDepartment(String destinationDepartment) { this.destinationDepartment = destinationDepartment; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getReceivedByUserName() { return receivedByUserName; }
    public void setReceivedByUserName(String receivedByUserName) { this.receivedByUserName = receivedByUserName; }
    public LocalDateTime getReceivedAt() { return receivedAt; }
    public void setReceivedAt(LocalDateTime receivedAt) { this.receivedAt = receivedAt; }
    public String getHandlingInstructions() { return handlingInstructions; }
    public void setHandlingInstructions(String handlingInstructions) { this.handlingInstructions = handlingInstructions; }
    public String getVerificationCode() { return verificationCode; }
    public void setVerificationCode(String verificationCode) { this.verificationCode = verificationCode; }
}
