package com.pharmacy.inventory.dto;

import com.pharmacy.inventory.entity.EmergencyRequest;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public class CreateEmergencyRequestDto {
    @NotNull(message = "Medicine ID is required")
    private Long medicineId;

    @NotNull(message = "Requested quantity is required")
    @Min(value = 1, message = "Quantity must be at least 1")
    private Integer requestedQuantity;

    @NotNull(message = "Urgency level is required")
    private EmergencyRequest.UrgencyLevel urgencyLevel;

    @NotBlank(message = "Department is required")
    private String department;

    private String clinicalReason;
    private String bedOrPatientId;
    private LocalDateTime requiredBy;

    public CreateEmergencyRequestDto() {}

    public Long getMedicineId() { return medicineId; }
    public void setMedicineId(Long medicineId) { this.medicineId = medicineId; }
    public Integer getRequestedQuantity() { return requestedQuantity; }
    public void setRequestedQuantity(Integer requestedQuantity) { this.requestedQuantity = requestedQuantity; }
    public EmergencyRequest.UrgencyLevel getUrgencyLevel() { return urgencyLevel; }
    public void setUrgencyLevel(EmergencyRequest.UrgencyLevel urgencyLevel) { this.urgencyLevel = urgencyLevel; }
    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }
    public String getClinicalReason() { return clinicalReason; }
    public void setClinicalReason(String clinicalReason) { this.clinicalReason = clinicalReason; }
    public String getBedOrPatientId() { return bedOrPatientId; }
    public void setBedOrPatientId(String bedOrPatientId) { this.bedOrPatientId = bedOrPatientId; }
    public LocalDateTime getRequiredBy() { return requiredBy; }
    public void setRequiredBy(LocalDateTime requiredBy) { this.requiredBy = requiredBy; }
}
