package exportation_panelera.Model;

import java.util.Date;

/**
 * Data Transfer Object for Delivery Information
 * Updated with relaxed validation to match current database structure
 */
public class Delivery_InfDTO {
    private String deliveryId;
    private String exportId;
    private String exportationId; // For compatibility with older code
    private String carrierName;
    private String trackingNumber;
    private String deliveryAddress;
    private String contactPerson;
    private String contactPhone;
    private Date deliveryDate;
    private Date date; // For compatibility with older code
    private String deliveryStatus;
    private String status; // For compatibility with older code
    private String notes;
    private String shippingMethod;
    private double shippingCost;
    private String shippingCurrency = "USD"; // Default to USD
    
    // Additional fields
    private String referenceNumber;
    private int id;
    private Date createdAt;
    private Date updatedAt;
    
    // Default constructor
    public Delivery_InfDTO() {
        // Set default values for fields that might not be in database
        this.carrierName = "Not specified";
        this.deliveryAddress = "Not specified";
        this.contactPerson = "Not specified";
        this.contactPhone = "Not specified";
        this.shippingMethod = "Standard";
        this.shippingCost = 0.0;
        this.shippingCurrency = "USD";
        this.createdAt = new Date();
        this.updatedAt = new Date();
    }

    // Constructor with basic required fields (only what's in your database)
    public Delivery_InfDTO(String deliveryId, String exportId, String trackingNumber) {
        this();
        this.deliveryId = deliveryId;
        this.exportId = exportId;
        this.exportationId = extractExportId(exportId);
        this.trackingNumber = trackingNumber;
        this.status = "PENDING";
        this.deliveryStatus = "PENDING";
    }

    // Constructor with all database fields
    public Delivery_InfDTO(String deliveryId, String exportId, String trackingNumber, String status, String notes, Date deliveryDate) {
        this(deliveryId, exportId, trackingNumber);
        this.status = status;
        this.deliveryStatus = status;
        this.notes = notes;
        this.deliveryDate = deliveryDate;
        this.date = deliveryDate;
    }

    // Helper method to extract numeric part from export ID
    private String extractExportId(String exportId) {
        if (exportId == null) return null;
        return exportId.startsWith("EXP") ? exportId.substring(3) : exportId;
    }

    // ALL GETTERS AND SETTERS
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDeliveryId() {
        return deliveryId;
    }

    public void setDeliveryId(String deliveryId) {
        this.deliveryId = deliveryId;
    }

    public String getExportId() {
        return exportId;
    }

    public void setExportId(String exportId) {
        this.exportId = exportId;
        // Keep exportationId in sync (store numeric part)
        this.exportationId = extractExportId(exportId);
    }

    public String getExportationId() {
        return exportationId;
    }

    public void setExportationId(String exportationId) {
        this.exportationId = exportationId;
        // Keep exportId in sync (add EXP prefix if not present)
        if (exportationId != null && !exportationId.startsWith("EXP")) {
            this.exportId = "EXP" + exportationId;
        } else {
            this.exportId = exportationId;
        }
    }

    public String getCarrierName() {
        return carrierName;
    }

    public void setCarrierName(String carrierName) {
        this.carrierName = carrierName != null ? carrierName : "Not specified";
    }

    public String getTrackingNumber() {
        return trackingNumber;
    }

    public void setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress != null ? deliveryAddress : "Not specified";
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson != null ? contactPerson : "Not specified";
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone != null ? contactPhone : "Not specified";
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
        this.date = deliveryDate;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
        this.deliveryDate = date;
    }

    public String getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(String deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
        this.status = deliveryStatus;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
        this.deliveryStatus = status;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getShippingMethod() {
        return shippingMethod;
    }

    public void setShippingMethod(String shippingMethod) {
        this.shippingMethod = shippingMethod != null ? shippingMethod : "Standard";
    }

    public double getShippingCost() {
        return shippingCost;
    }

    public void setShippingCost(double shippingCost) {
        this.shippingCost = shippingCost;
    }

    public String getShippingCurrency() {
        return shippingCurrency;
    }

    public void setShippingCurrency(String shippingCurrency) {
        this.shippingCurrency = shippingCurrency != null ? shippingCurrency : "USD";
    }

    public String getReferenceNumber() {
        if (referenceNumber == null || referenceNumber.isEmpty()) {
            if (trackingNumber != null && !trackingNumber.isEmpty()) {
                return "REF-" + trackingNumber;
            } else {
                return "REF-" + (deliveryId != null ? deliveryId : "UNKNOWN");
            }
        }
        return referenceNumber;
    }

    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
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

    public void markAsUpdated() {
        this.updatedAt = new Date();
    }

    /**
     * Relaxed validation that only checks fields that exist in your database
     */
    public void validateForSave() throws IllegalStateException {
        // Only validate fields that are actually required in your database
        if (exportationId == null || exportationId.trim().isEmpty()) {
            if (exportId == null || exportId.trim().isEmpty()) {
                throw new IllegalStateException("Export ID is required");
            }
        }
        
        if (trackingNumber == null || trackingNumber.trim().isEmpty()) {
            throw new IllegalStateException("Tracking Number is required");
        }
        
        // Status can be null - will default to PENDING
        // Date can be null - will default to current date
        // Notes can be null - will default to empty string
        
        // Don't validate fields that aren't in your database table:
        // - deliveryId (generated automatically)
        // - carrierName (not in your table)
        // - deliveryAddress (not in your table)
        // - contactPerson (not in your table)
    }

    /**
     * Minimal validation that only checks the absolute essentials
     */
    public void validateForDatabase() throws IllegalStateException {
        // Only validate what's absolutely required for database insert
        if ((exportationId == null || exportationId.trim().isEmpty()) && 
            (exportId == null || exportId.trim().isEmpty())) {
            throw new IllegalStateException("Export ID is required");
        }
        
        if (trackingNumber == null || trackingNumber.trim().isEmpty()) {
            throw new IllegalStateException("Tracking Number is required");
        }
    }

    /**
     * Check if this delivery has the minimum required data for your database
     */
    public boolean isValidForDatabase() {
        try {
            validateForDatabase();
            return true;
        } catch (IllegalStateException e) {
            return false;
        }
    }

    /**
     * Prepare this DTO for database operations by setting defaults
     */
    public void prepareForDatabase() {
        // Set defaults for required fields if they're null
        if (status == null || status.trim().isEmpty()) {
            setStatus("PENDING");
        }
        
        if (deliveryDate == null) {
            setDeliveryDate(new Date());
        }
        
        if (notes == null) {
            setNotes("");
        }
        
        if (trackingNumber == null) {
            setTrackingNumber("TRK-" + System.currentTimeMillis());
        }
        
        // Ensure timestamps are set
        if (createdAt == null) {
            setCreatedAt(new Date());
        }
        
        markAsUpdated();
    }

    @Override
    public String toString() {
        return "Delivery_InfDTO{" +
                "id=" + id +
                ", deliveryId='" + deliveryId + '\'' +
                ", exportId='" + exportId + '\'' +
                ", exportationId='" + exportationId + '\'' +
                ", trackingNumber='" + trackingNumber + '\'' +
                ", deliveryDate=" + deliveryDate +
                ", status='" + status + '\'' +
                ", notes='" + notes + '\'' +
                ", shippingCost=" + shippingCost +
                ", shippingCurrency='" + shippingCurrency + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }

    /**
     * Create a minimal delivery for testing
     */
    public static Delivery_InfDTO createTestDelivery(String exportId, String trackingNumber) {
        Delivery_InfDTO delivery = new Delivery_InfDTO();
        delivery.setExportId(exportId);
        delivery.setTrackingNumber(trackingNumber);
        delivery.setStatus("PENDING");
        delivery.setNotes("Test delivery");
        delivery.setDeliveryDate(new Date());
        delivery.prepareForDatabase();
        return delivery;
    }
}