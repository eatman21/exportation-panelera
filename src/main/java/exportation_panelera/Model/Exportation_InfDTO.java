package exportation_panelera.Model;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Data Transfer Object for exportation information
 */
public class Exportation_InfDTO {
    private int id;
    private String exportationId;
    private String exportId;
    private String productType;
    private String productName;  // Added for compatibility
    private double amount;
    private double quantity;     // Added for compatibility
    private String destination;
    private Date exportationDate;
    private Date exportDate;     // Added for compatibility
    private BigDecimal unitPrice;
    private String currency;
    private boolean hasDelivery;
    private String status;
    private String notes;
    private Date createdAt;
    private Date updatedAt;
    private double totalValue;
    private String customerName;
    private String customerEmail;
    private String customerPhone;
    private String documentNumber;
    private String exportLicense;
    private String employeeId;   // Added for compatibility
    private String transportMethod; // Added for compatibility
    
    // Default constructor
    public Exportation_InfDTO() {
        this.createdAt = new Date();
        this.updatedAt = new Date();
        this.currency = "USD";
        this.hasDelivery = false;
        this.status = "PENDING";
    }
    
    // Constructor with basic required fields
    public Exportation_InfDTO(String exportationId, String productType, double amount, String destination) {
        this();
        this.exportationId = exportationId;
        this.exportId = exportationId;
        this.productType = productType;
        this.productName = productType; // Set both for compatibility
        this.amount = amount;
        this.quantity = amount; // Set both for compatibility
        this.destination = destination;
    }
    
    // Getters and Setters
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getExportationId() {
        return exportationId;
    }
    
    public void setExportationId(String exportationId) {
        this.exportationId = exportationId;
        this.exportId = exportationId;
    }
    
    public String getExportId() {
        return exportId;
    }
    
    public void setExportId(String exportId) {
        this.exportId = exportId;
        this.exportationId = exportId;
    }
    
    public String getProductType() {
        return productType;
    }
    
    public void setProductType(String productType) {
        this.productType = productType;
        this.productName = productType; // Keep in sync
    }
    
    // Fixed implementation for productName
    public String getProductName() {
        return productName != null ? productName : productType;
    }
    
    public void setProductName(String productName) {
        this.productName = productName;
        this.productType = productName; // Keep in sync
    }
    
    public double getAmount() {
        return amount;
    }
    
    public void setAmount(double amount) {
        this.amount = amount;
        this.quantity = amount; // Keep in sync
        calculateTotalValue();
    }
    
    // Fixed implementation for quantity
    public double getQuantity() {
        return quantity != 0 ? quantity : amount;
    }
    
    public void setQuantity(double quantity) {
        this.quantity = quantity;
        this.amount = quantity; // Keep in sync
        calculateTotalValue();
    }
    
    public String getDestination() {
        return destination;
    }
    
    public void setDestination(String destination) {
        this.destination = destination;
    }
    
    public Date getExportationDate() {
        return exportationDate;
    }
    
    public void setExportationDate(Date exportationDate) {
        this.exportationDate = exportationDate;
        this.exportDate = exportationDate; // Keep in sync
    }
    
    // Fixed implementation for exportDate
    public Date getExportDate() {
        return exportDate != null ? exportDate : exportationDate;
    }
    
    public void setExportDate(Date exportDate) {
        this.exportDate = exportDate;
        this.exportationDate = exportDate; // Keep in sync
    }
    
    public BigDecimal getUnitPrice() {
        return unitPrice;
    }
    
    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
        calculateTotalValue();
    }
    
    public String getCurrency() {
        return currency;
    }
    
    public void setCurrency(String currency) {
        this.currency = currency;
    }
    
    public boolean isHasDelivery() {
        return hasDelivery;
    }
    
    public void setHasDelivery(boolean hasDelivery) {
        this.hasDelivery = hasDelivery;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public String getNotes() {
        return notes;
    }
    
    public void setNotes(String notes) {
        this.notes = notes;
    }
    
    public Date getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
    
    public Date getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
    
    public double getTotalValue() {
        return totalValue;
    }
    
    public void setTotalValue(double totalValue) {
        this.totalValue = totalValue;
    }
    
    public String getCustomerName() {
        return customerName;
    }
    
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
    
    public String getCustomerEmail() {
        return customerEmail;
    }
    
    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }
    
    public String getCustomerPhone() {
        return customerPhone;
    }
    
    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }
    
    public String getDocumentNumber() {
        return documentNumber;
    }
    
    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }
    
    public String getExportLicense() {
        return exportLicense;
    }
    
    public void setExportLicense(String exportLicense) {
        this.exportLicense = exportLicense;
    }
    
    // Fixed implementation for employeeId
    public String getEmployeeId() {
        return employeeId;
    }
    
    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }
    
    // Fixed implementation for transportMethod
    public String getTransportMethod() {
        return transportMethod;
    }
    
    public void setTransportMethod(String transportMethod) {
        this.transportMethod = transportMethod;
    }
    
    // Utility methods
    public void markAsUpdated() {
        this.updatedAt = new Date();
    }
    
    private void calculateTotalValue() {
        if (unitPrice != null && (amount > 0 || quantity > 0)) {
            double qty = amount > 0 ? amount : quantity;
            this.totalValue = unitPrice.doubleValue() * qty;
        }
    }
    
    public void validateForSave() throws IllegalStateException {
        if (exportationId == null || exportationId.trim().isEmpty()) {
            throw new IllegalStateException("Exportation ID is required");
        }
        if ((productType == null || productType.trim().isEmpty()) && 
            (productName == null || productName.trim().isEmpty())) {
            throw new IllegalStateException("Product Type/Name is required");
        }
        if (amount <= 0 && quantity <= 0) {
            throw new IllegalStateException("Amount/Quantity must be greater than 0");
        }
        if (destination == null || destination.trim().isEmpty()) {
            throw new IllegalStateException("Destination is required");
        }
        if (exportationDate == null && exportDate == null) {
            throw new IllegalStateException("Exportation Date is required");
        }
    }
    
    @Override
    public String toString() {
        return "Exportation_InfDTO{" +
                "id=" + id +
                ", exportationId='" + exportationId + '\'' +
                ", productType='" + getProductType() + '\'' +
                ", amount=" + getAmount() +
                ", destination='" + destination + '\'' +
                ", exportationDate=" + getExportationDate() +
                ", unitPrice=" + unitPrice +
                ", currency='" + currency + '\'' +
                ", hasDelivery=" + hasDelivery +
                ", status='" + status + '\'' +
                ", totalValue=" + totalValue +
                '}';
    }
}