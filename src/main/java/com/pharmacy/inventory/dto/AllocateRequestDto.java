package com.pharmacy.inventory.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public class AllocateRequestDto {
    @NotNull(message = "Request ID is required")
    private Long requestId;

    @NotEmpty(message = "Allocations must not be empty")
    private List<BatchAllocationItem> allocations;

    private String pharmacistNotes;

    public AllocateRequestDto() {}

    public static class BatchAllocationItem {
        @NotNull(message = "Batch ID is required")
        private Long batchId;

        @NotNull(message = "Quantity is required")
        private Integer quantity;

        public BatchAllocationItem() {}
        public BatchAllocationItem(Long batchId, Integer quantity) {
            this.batchId = batchId;
            this.quantity = quantity;
        }

        public Long getBatchId() { return batchId; }
        public void setBatchId(Long batchId) { this.batchId = batchId; }
        public Integer getQuantity() { return quantity; }
        public void setQuantity(Integer quantity) { this.quantity = quantity; }
    }

    public Long getRequestId() { return requestId; }
    public void setRequestId(Long requestId) { this.requestId = requestId; }
    public List<BatchAllocationItem> getAllocations() { return allocations; }
    public void setAllocations(List<BatchAllocationItem> allocations) { this.allocations = allocations; }
    public String getPharmacistNotes() { return pharmacistNotes; }
    public void setPharmacistNotes(String pharmacistNotes) { this.pharmacistNotes = pharmacistNotes; }
}
