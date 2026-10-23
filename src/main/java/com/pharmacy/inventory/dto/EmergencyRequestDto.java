package com.pharmacy.inventory.dto;

import com.pharmacy.inventory.entity.EmergencyRequest;

import java.time.LocalDateTime;
import java.util.List;

public class EmergencyRequestDto {
    private Long id;
    private String requestNumber;

    private Long medicineId;
    private String medicineName;
    private String medicineGenericName;
    private String medicineSku;
    private String medicineDosageForm;
    private boolean isCriticalMedicine;

    private Long requestedByUserId;
    private String requestedByUserName;
    private String requestedByUserRole;

    private String department;
    private Integer requestedQuantity;
    private Integer allocatedQuantity;

    private EmergencyRequest.UrgencyLevel urgencyLevel;
    private EmergencyRequest.RequestStatus status;

    private String clinicalReason;
    private String bedOrPatientId;
    private LocalDateTime requiredBy;

    private String approvedByName;
    private String pharmacistNotes;

    private List<AllocationItemDto> allocations;

    private Long dispatchId;
    private String dispatchNumber;
    private String carrierName;
    private String dispatchStatus;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public EmergencyRequestDto() {}

    public EmergencyRequestDto(Long id, String requestNumber, Long medicineId, String medicineName,
                               String medicineGenericName, String medicineSku, String medicineDosageForm,
                               boolean isCriticalMedicine, Long requestedByUserId, String requestedByUserName,
                               String requestedByUserRole, String department, Integer requestedQuantity,
                               Integer allocatedQuantity, EmergencyRequest.UrgencyLevel urgencyLevel,
                               EmergencyRequest.RequestStatus status, String clinicalReason,
                               String bedOrPatientId, LocalDateTime requiredBy, String approvedByName,
                               String pharmacistNotes, List<AllocationItemDto> allocations,
                               Long dispatchId, String dispatchNumber, String carrierName,
                               String dispatchStatus, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.requestNumber = requestNumber;
        this.medicineId = medicineId;
        this.medicineName = medicineName;
        this.medicineGenericName = medicineGenericName;
        this.medicineSku = medicineSku;
        this.medicineDosageForm = medicineDosageForm;
        this.isCriticalMedicine = isCriticalMedicine;
        this.requestedByUserId = requestedByUserId;
        this.requestedByUserName = requestedByUserName;
        this.requestedByUserRole = requestedByUserRole;
        this.department = department;
        this.requestedQuantity = requestedQuantity;
        this.allocatedQuantity = allocatedQuantity;
        this.urgencyLevel = urgencyLevel;
        this.status = status;
        this.clinicalReason = clinicalReason;
        this.bedOrPatientId = bedOrPatientId;
        this.requiredBy = requiredBy;
        this.approvedByName = approvedByName;
        this.pharmacistNotes = pharmacistNotes;
        this.allocations = allocations;
        this.dispatchId = dispatchId;
        this.dispatchNumber = dispatchNumber;
        this.carrierName = carrierName;
        this.dispatchStatus = dispatchStatus;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static EmergencyRequestDtoBuilder builder() {
        return new EmergencyRequestDtoBuilder();
    }

    public static class EmergencyRequestDtoBuilder {
        private Long id;
        private String requestNumber;
        private Long medicineId;
        private String medicineName;
        private String medicineGenericName;
        private String medicineSku;
        private String medicineDosageForm;
        private boolean isCriticalMedicine;
        private Long requestedByUserId;
        private String requestedByUserName;
        private String requestedByUserRole;
        private String department;
        private Integer requestedQuantity;
        private Integer allocatedQuantity;
        private EmergencyRequest.UrgencyLevel urgencyLevel;
        private EmergencyRequest.RequestStatus status;
        private String clinicalReason;
        private String bedOrPatientId;
        private LocalDateTime requiredBy;
        private String approvedByName;
        private String pharmacistNotes;
        private List<AllocationItemDto> allocations;
        private Long dispatchId;
        private String dispatchNumber;
        private String carrierName;
        private String dispatchStatus;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        public EmergencyRequestDtoBuilder id(Long id) { this.id = id; return this; }
        public EmergencyRequestDtoBuilder requestNumber(String requestNumber) { this.requestNumber = requestNumber; return this; }
        public EmergencyRequestDtoBuilder medicineId(Long medicineId) { this.medicineId = medicineId; return this; }
        public EmergencyRequestDtoBuilder medicineName(String medicineName) { this.medicineName = medicineName; return this; }
        public EmergencyRequestDtoBuilder medicineGenericName(String medicineGenericName) { this.medicineGenericName = medicineGenericName; return this; }
        public EmergencyRequestDtoBuilder medicineSku(String medicineSku) { this.medicineSku = medicineSku; return this; }
        public EmergencyRequestDtoBuilder medicineDosageForm(String medicineDosageForm) { this.medicineDosageForm = medicineDosageForm; return this; }
        public EmergencyRequestDtoBuilder isCriticalMedicine(boolean isCriticalMedicine) { this.isCriticalMedicine = isCriticalMedicine; return this; }
        public EmergencyRequestDtoBuilder requestedByUserId(Long requestedByUserId) { this.requestedByUserId = requestedByUserId; return this; }
        public EmergencyRequestDtoBuilder requestedByUserName(String requestedByUserName) { this.requestedByUserName = requestedByUserName; return this; }
        public EmergencyRequestDtoBuilder requestedByUserRole(String requestedByUserRole) { this.requestedByUserRole = requestedByUserRole; return this; }
        public EmergencyRequestDtoBuilder department(String department) { this.department = department; return this; }
        public EmergencyRequestDtoBuilder requestedQuantity(Integer requestedQuantity) { this.requestedQuantity = requestedQuantity; return this; }
        public EmergencyRequestDtoBuilder allocatedQuantity(Integer allocatedQuantity) { this.allocatedQuantity = allocatedQuantity; return this; }
        public EmergencyRequestDtoBuilder urgencyLevel(EmergencyRequest.UrgencyLevel urgencyLevel) { this.urgencyLevel = urgencyLevel; return this; }
        public EmergencyRequestDtoBuilder status(EmergencyRequest.RequestStatus status) { this.status = status; return this; }
        public EmergencyRequestDtoBuilder clinicalReason(String clinicalReason) { this.clinicalReason = clinicalReason; return this; }
        public EmergencyRequestDtoBuilder bedOrPatientId(String bedOrPatientId) { this.bedOrPatientId = bedOrPatientId; return this; }
        public EmergencyRequestDtoBuilder requiredBy(LocalDateTime requiredBy) { this.requiredBy = requiredBy; return this; }
        public EmergencyRequestDtoBuilder approvedByName(String approvedByName) { this.approvedByName = approvedByName; return this; }
        public EmergencyRequestDtoBuilder pharmacistNotes(String pharmacistNotes) { this.pharmacistNotes = pharmacistNotes; return this; }
        public EmergencyRequestDtoBuilder allocations(List<AllocationItemDto> allocations) { this.allocations = allocations; return this; }
        public EmergencyRequestDtoBuilder dispatchId(Long dispatchId) { this.dispatchId = dispatchId; return this; }
        public EmergencyRequestDtoBuilder dispatchNumber(String dispatchNumber) { this.dispatchNumber = dispatchNumber; return this; }
        public EmergencyRequestDtoBuilder carrierName(String carrierName) { this.carrierName = carrierName; return this; }
        public EmergencyRequestDtoBuilder dispatchStatus(String dispatchStatus) { this.dispatchStatus = dispatchStatus; return this; }
        public EmergencyRequestDtoBuilder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }
        public EmergencyRequestDtoBuilder updatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; return this; }

        public EmergencyRequestDto build() {
            return new EmergencyRequestDto(id, requestNumber, medicineId, medicineName, medicineGenericName,
                    medicineSku, medicineDosageForm, isCriticalMedicine, requestedByUserId, requestedByUserName,
                    requestedByUserRole, department, requestedQuantity, allocatedQuantity, urgencyLevel,
                    status, clinicalReason, bedOrPatientId, requiredBy, approvedByName, pharmacistNotes,
                    allocations, dispatchId, dispatchNumber, carrierName, dispatchStatus, createdAt, updatedAt);
        }
    }

    public static class AllocationItemDto {
        private Long allocationId;
        private Long batchId;
        private String batchNumber;
        private String expiryDate;
        private Integer quantity;
        private String storageLocation;

        public AllocationItemDto() {}

        public AllocationItemDto(Long allocationId, Long batchId, String batchNumber, String expiryDate, Integer quantity, String storageLocation) {
            this.allocationId = allocationId;
            this.batchId = batchId;
            this.batchNumber = batchNumber;
            this.expiryDate = expiryDate;
            this.quantity = quantity;
            this.storageLocation = storageLocation;
        }

        public static AllocationItemDtoBuilder builder() {
            return new AllocationItemDtoBuilder();
        }

        public static class AllocationItemDtoBuilder {
            private Long allocationId;
            private Long batchId;
            private String batchNumber;
            private String expiryDate;
            private Integer quantity;
            private String storageLocation;

            public AllocationItemDtoBuilder allocationId(Long allocationId) { this.allocationId = allocationId; return this; }
            public AllocationItemDtoBuilder batchId(Long batchId) { this.batchId = batchId; return this; }
            public AllocationItemDtoBuilder batchNumber(String batchNumber) { this.batchNumber = batchNumber; return this; }
            public AllocationItemDtoBuilder expiryDate(String expiryDate) { this.expiryDate = expiryDate; return this; }
            public AllocationItemDtoBuilder quantity(Integer quantity) { this.quantity = quantity; return this; }
            public AllocationItemDtoBuilder storageLocation(String storageLocation) { this.storageLocation = storageLocation; return this; }

            public AllocationItemDto build() {
                return new AllocationItemDto(allocationId, batchId, batchNumber, expiryDate, quantity, storageLocation);
            }
        }

        public Long getAllocationId() { return allocationId; }
        public void setAllocationId(Long allocationId) { this.allocationId = allocationId; }
        public Long getBatchId() { return batchId; }
        public void setBatchId(Long batchId) { this.batchId = batchId; }
        public String getBatchNumber() { return batchNumber; }
        public void setBatchNumber(String batchNumber) { this.batchNumber = batchNumber; }
        public String getExpiryDate() { return expiryDate; }
        public void setExpiryDate(String expiryDate) { this.expiryDate = expiryDate; }
        public Integer getQuantity() { return quantity; }
        public void setQuantity(Integer quantity) { this.quantity = quantity; }
        public String getStorageLocation() { return storageLocation; }
        public void setStorageLocation(String storageLocation) { this.storageLocation = storageLocation; }
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getRequestNumber() { return requestNumber; }
    public void setRequestNumber(String requestNumber) { this.requestNumber = requestNumber; }
    public Long getMedicineId() { return medicineId; }
    public void setMedicineId(Long medicineId) { this.medicineId = medicineId; }
    public String getMedicineName() { return medicineName; }
    public void setMedicineName(String medicineName) { this.medicineName = medicineName; }
    public String getMedicineGenericName() { return medicineGenericName; }
    public void setMedicineGenericName(String medicineGenericName) { this.medicineGenericName = medicineGenericName; }
    public String getMedicineSku() { return medicineSku; }
    public void setMedicineSku(String medicineSku) { this.medicineSku = medicineSku; }
    public String getMedicineDosageForm() { return medicineDosageForm; }
    public void setMedicineDosageForm(String medicineDosageForm) { this.medicineDosageForm = medicineDosageForm; }
    public boolean isCriticalMedicine() { return isCriticalMedicine; }
    public void setCriticalMedicine(boolean criticalMedicine) { isCriticalMedicine = criticalMedicine; }
    public Long getRequestedByUserId() { return requestedByUserId; }
    public void setRequestedByUserId(Long requestedByUserId) { this.requestedByUserId = requestedByUserId; }
    public String getRequestedByUserName() { return requestedByUserName; }
    public void setRequestedByUserName(String requestedByUserName) { this.requestedByUserName = requestedByUserName; }
    public String getRequestedByUserRole() { return requestedByUserRole; }
    public void setRequestedByUserRole(String requestedByUserRole) { this.requestedByUserRole = requestedByUserRole; }
    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }
    public Integer getRequestedQuantity() { return requestedQuantity; }
    public void setRequestedQuantity(Integer requestedQuantity) { this.requestedQuantity = requestedQuantity; }
    public Integer getAllocatedQuantity() { return allocatedQuantity; }
    public void setAllocatedQuantity(Integer allocatedQuantity) { this.allocatedQuantity = allocatedQuantity; }
    public EmergencyRequest.UrgencyLevel getUrgencyLevel() { return urgencyLevel; }
    public void setUrgencyLevel(EmergencyRequest.UrgencyLevel urgencyLevel) { this.urgencyLevel = urgencyLevel; }
    public EmergencyRequest.RequestStatus getStatus() { return status; }
    public void setStatus(EmergencyRequest.RequestStatus status) { this.status = status; }
    public String getClinicalReason() { return clinicalReason; }
    public void setClinicalReason(String clinicalReason) { this.clinicalReason = clinicalReason; }
    public String getBedOrPatientId() { return bedOrPatientId; }
    public void setBedOrPatientId(String bedOrPatientId) { this.bedOrPatientId = bedOrPatientId; }
    public LocalDateTime getRequiredBy() { return requiredBy; }
    public void setRequiredBy(LocalDateTime requiredBy) { this.requiredBy = requiredBy; }
    public String getApprovedByName() { return approvedByName; }
    public void setApprovedByName(String approvedByName) { this.approvedByName = approvedByName; }
    public String getPharmacistNotes() { return pharmacistNotes; }
    public void setPharmacistNotes(String pharmacistNotes) { this.pharmacistNotes = pharmacistNotes; }
    public List<AllocationItemDto> getAllocations() { return allocations; }
    public void setAllocations(List<AllocationItemDto> allocations) { this.allocations = allocations; }
    public Long getDispatchId() { return dispatchId; }
    public void setDispatchId(Long dispatchId) { this.dispatchId = dispatchId; }
    public String getDispatchNumber() { return dispatchNumber; }
    public void setDispatchNumber(String dispatchNumber) { this.dispatchNumber = dispatchNumber; }
    public String getCarrierName() { return carrierName; }
    public void setCarrierName(String carrierName) { this.carrierName = carrierName; }
    public String getDispatchStatus() { return dispatchStatus; }
    public void setDispatchStatus(String dispatchStatus) { this.dispatchStatus = dispatchStatus; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
