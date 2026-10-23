package com.pharmacy.inventory.dto;

import java.math.BigDecimal;
import java.util.Map;

public class StockSummaryDto {
    private long totalMedicines;
    private long totalBatches;
    private long totalStockUnits;
    private BigDecimal totalInventoryValue;

    private long lowStockCount;
    private long outOfStockCount;
    private long expiringSoonCount;
    private long expiredCount;

    private long pendingEmergencyRequests;
    private long criticalEmergencyRequests;
    private long dispatchedRequests;
    private long deliveredRequests;

    private Map<String, Integer> stockByCategory;
    private Map<String, Integer> requestsByDepartment;

    public StockSummaryDto() {}

    public StockSummaryDto(long totalMedicines, long totalBatches, long totalStockUnits,
                           BigDecimal totalInventoryValue, long lowStockCount, long outOfStockCount,
                           long expiringSoonCount, long expiredCount, long pendingEmergencyRequests,
                           long criticalEmergencyRequests, long dispatchedRequests,
                           long deliveredRequests, Map<String, Integer> stockByCategory,
                           Map<String, Integer> requestsByDepartment) {
        this.totalMedicines = totalMedicines;
        this.totalBatches = totalBatches;
        this.totalStockUnits = totalStockUnits;
        this.totalInventoryValue = totalInventoryValue;
        this.lowStockCount = lowStockCount;
        this.outOfStockCount = outOfStockCount;
        this.expiringSoonCount = expiringSoonCount;
        this.expiredCount = expiredCount;
        this.pendingEmergencyRequests = pendingEmergencyRequests;
        this.criticalEmergencyRequests = criticalEmergencyRequests;
        this.dispatchedRequests = dispatchedRequests;
        this.deliveredRequests = deliveredRequests;
        this.stockByCategory = stockByCategory;
        this.requestsByDepartment = requestsByDepartment;
    }

    public static StockSummaryDtoBuilder builder() {
        return new StockSummaryDtoBuilder();
    }

    public static class StockSummaryDtoBuilder {
        private long totalMedicines;
        private long totalBatches;
        private long totalStockUnits;
        private BigDecimal totalInventoryValue;
        private long lowStockCount;
        private long outOfStockCount;
        private long expiringSoonCount;
        private long expiredCount;
        private long pendingEmergencyRequests;
        private long criticalEmergencyRequests;
        private long dispatchedRequests;
        private long deliveredRequests;
        private Map<String, Integer> stockByCategory;
        private Map<String, Integer> requestsByDepartment;

        public StockSummaryDtoBuilder totalMedicines(long totalMedicines) { this.totalMedicines = totalMedicines; return this; }
        public StockSummaryDtoBuilder totalBatches(long totalBatches) { this.totalBatches = totalBatches; return this; }
        public StockSummaryDtoBuilder totalStockUnits(long totalStockUnits) { this.totalStockUnits = totalStockUnits; return this; }
        public StockSummaryDtoBuilder totalInventoryValue(BigDecimal totalInventoryValue) { this.totalInventoryValue = totalInventoryValue; return this; }
        public StockSummaryDtoBuilder lowStockCount(long lowStockCount) { this.lowStockCount = lowStockCount; return this; }
        public StockSummaryDtoBuilder outOfStockCount(long outOfStockCount) { this.outOfStockCount = outOfStockCount; return this; }
        public StockSummaryDtoBuilder expiringSoonCount(long expiringSoonCount) { this.expiringSoonCount = expiringSoonCount; return this; }
        public StockSummaryDtoBuilder expiredCount(long expiredCount) { this.expiredCount = expiredCount; return this; }
        public StockSummaryDtoBuilder pendingEmergencyRequests(long pendingEmergencyRequests) { this.pendingEmergencyRequests = pendingEmergencyRequests; return this; }
        public StockSummaryDtoBuilder criticalEmergencyRequests(long criticalEmergencyRequests) { this.criticalEmergencyRequests = criticalEmergencyRequests; return this; }
        public StockSummaryDtoBuilder dispatchedRequests(long dispatchedRequests) { this.dispatchedRequests = dispatchedRequests; return this; }
        public StockSummaryDtoBuilder deliveredRequests(long deliveredRequests) { this.deliveredRequests = deliveredRequests; return this; }
        public StockSummaryDtoBuilder stockByCategory(Map<String, Integer> stockByCategory) { this.stockByCategory = stockByCategory; return this; }
        public StockSummaryDtoBuilder requestsByDepartment(Map<String, Integer> requestsByDepartment) { this.requestsByDepartment = requestsByDepartment; return this; }

        public StockSummaryDto build() {
            return new StockSummaryDto(totalMedicines, totalBatches, totalStockUnits, totalInventoryValue,
                    lowStockCount, outOfStockCount, expiringSoonCount, expiredCount, pendingEmergencyRequests,
                    criticalEmergencyRequests, dispatchedRequests, deliveredRequests, stockByCategory, requestsByDepartment);
        }
    }

    public long getTotalMedicines() { return totalMedicines; }
    public void setTotalMedicines(long totalMedicines) { this.totalMedicines = totalMedicines; }
    public long getTotalBatches() { return totalBatches; }
    public void setTotalBatches(long totalBatches) { this.totalBatches = totalBatches; }
    public long getTotalStockUnits() { return totalStockUnits; }
    public void setTotalStockUnits(long totalStockUnits) { this.totalStockUnits = totalStockUnits; }
    public BigDecimal getTotalInventoryValue() { return totalInventoryValue; }
    public void setTotalInventoryValue(BigDecimal totalInventoryValue) { this.totalInventoryValue = totalInventoryValue; }
    public long getLowStockCount() { return lowStockCount; }
    public void setLowStockCount(long lowStockCount) { this.lowStockCount = lowStockCount; }
    public long getOutOfStockCount() { return outOfStockCount; }
    public void setOutOfStockCount(long outOfStockCount) { this.outOfStockCount = outOfStockCount; }
    public long getExpiringSoonCount() { return expiringSoonCount; }
    public void setExpiringSoonCount(long expiringSoonCount) { this.expiringSoonCount = expiringSoonCount; }
    public long getExpiredCount() { return expiredCount; }
    public void setExpiredCount(long expiredCount) { this.expiredCount = expiredCount; }
    public long getPendingEmergencyRequests() { return pendingEmergencyRequests; }
    public void setPendingEmergencyRequests(long pendingEmergencyRequests) { this.pendingEmergencyRequests = pendingEmergencyRequests; }
    public long getCriticalEmergencyRequests() { return criticalEmergencyRequests; }
    public void setCriticalEmergencyRequests(long criticalEmergencyRequests) { this.criticalEmergencyRequests = criticalEmergencyRequests; }
    public long getDispatchedRequests() { return dispatchedRequests; }
    public void setDispatchedRequests(long dispatchedRequests) { this.dispatchedRequests = dispatchedRequests; }
    public long getDeliveredRequests() { return deliveredRequests; }
    public void setDeliveredRequests(long deliveredRequests) { this.deliveredRequests = deliveredRequests; }
    public Map<String, Integer> getStockByCategory() { return stockByCategory; }
    public void setStockByCategory(Map<String, Integer> stockByCategory) { this.stockByCategory = stockByCategory; }
    public Map<String, Integer> getRequestsByDepartment() { return requestsByDepartment; }
    public void setRequestsByDepartment(Map<String, Integer> requestsByDepartment) { this.requestsByDepartment = requestsByDepartment; }
}
