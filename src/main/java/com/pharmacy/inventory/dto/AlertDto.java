package com.pharmacy.inventory.dto;

import java.time.LocalDateTime;

public class AlertDto {
    private String id;
    private String type;
    private String severity;
    private String title;
    private String message;
    private Long medicineId;
    private String medicineName;
    private String medicineSku;
    private String batchNumber;
    private Integer currentStock;
    private Integer threshold;
    private String department;
    private Long referenceId;
    private LocalDateTime timestamp;
    private String suggestedAction;

    public AlertDto() {}

    public AlertDto(String id, String type, String severity, String title, String message,
                    Long medicineId, String medicineName, String medicineSku, String batchNumber,
                    Integer currentStock, Integer threshold, String department, Long referenceId,
                    LocalDateTime timestamp, String suggestedAction) {
        this.id = id;
        this.type = type;
        this.severity = severity;
        this.title = title;
        this.message = message;
        this.medicineId = medicineId;
        this.medicineName = medicineName;
        this.medicineSku = medicineSku;
        this.batchNumber = batchNumber;
        this.currentStock = currentStock;
        this.threshold = threshold;
        this.department = department;
        this.referenceId = referenceId;
        this.timestamp = timestamp;
        this.suggestedAction = suggestedAction;
    }

    public static AlertDtoBuilder builder() {
        return new AlertDtoBuilder();
    }

    public static class AlertDtoBuilder {
        private String id;
        private String type;
        private String severity;
        private String title;
        private String message;
        private Long medicineId;
        private String medicineName;
        private String medicineSku;
        private String batchNumber;
        private Integer currentStock;
        private Integer threshold;
        private String department;
        private Long referenceId;
        private LocalDateTime timestamp;
        private String suggestedAction;

        public AlertDtoBuilder id(String id) { this.id = id; return this; }
        public AlertDtoBuilder type(String type) { this.type = type; return this; }
        public AlertDtoBuilder severity(String severity) { this.severity = severity; return this; }
        public AlertDtoBuilder title(String title) { this.title = title; return this; }
        public AlertDtoBuilder message(String message) { this.message = message; return this; }
        public AlertDtoBuilder medicineId(Long medicineId) { this.medicineId = medicineId; return this; }
        public AlertDtoBuilder medicineName(String medicineName) { this.medicineName = medicineName; return this; }
        public AlertDtoBuilder medicineSku(String medicineSku) { this.medicineSku = medicineSku; return this; }
        public AlertDtoBuilder batchNumber(String batchNumber) { this.batchNumber = batchNumber; return this; }
        public AlertDtoBuilder currentStock(Integer currentStock) { this.currentStock = currentStock; return this; }
        public AlertDtoBuilder threshold(Integer threshold) { this.threshold = threshold; return this; }
        public AlertDtoBuilder department(String department) { this.department = department; return this; }
        public AlertDtoBuilder referenceId(Long referenceId) { this.referenceId = referenceId; return this; }
        public AlertDtoBuilder timestamp(LocalDateTime timestamp) { this.timestamp = timestamp; return this; }
        public AlertDtoBuilder suggestedAction(String suggestedAction) { this.suggestedAction = suggestedAction; return this; }

        public AlertDto build() {
            return new AlertDto(id, type, severity, title, message, medicineId, medicineName,
                    medicineSku, batchNumber, currentStock, threshold, department, referenceId,
                    timestamp, suggestedAction);
        }
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public String getSeverity() { return severity; }
    public void setSeverity(String severity) { this.severity = severity; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public Long getMedicineId() { return medicineId; }
    public void setMedicineId(Long medicineId) { this.medicineId = medicineId; }
    public String getMedicineName() { return medicineName; }
    public void setMedicineName(String medicineName) { this.medicineName = medicineName; }
    public String getMedicineSku() { return medicineSku; }
    public void setMedicineSku(String medicineSku) { this.medicineSku = medicineSku; }
    public String getBatchNumber() { return batchNumber; }
    public void setBatchNumber(String batchNumber) { this.batchNumber = batchNumber; }
    public Integer getCurrentStock() { return currentStock; }
    public void setCurrentStock(Integer currentStock) { this.currentStock = currentStock; }
    public Integer getThreshold() { return threshold; }
    public void setThreshold(Integer threshold) { this.threshold = threshold; }
    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }
    public Long getReferenceId() { return referenceId; }
    public void setReferenceId(Long referenceId) { this.referenceId = referenceId; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
    public String getSuggestedAction() { return suggestedAction; }
    public void setSuggestedAction(String suggestedAction) { this.suggestedAction = suggestedAction; }
}
