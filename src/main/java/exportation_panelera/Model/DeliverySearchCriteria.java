package exportation_panelera.Model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.StringJoiner;

/**
 * Enhanced search criteria class for filtering delivery records.
 * This class uses the builder pattern for easy construction and provides
 * comprehensive search functionality with modern Java practices.
 * 
 * Example usage:
 * DeliverySearchCriteria criteria = new DeliverySearchCriteria()
 *     .withDeliveryId("DEL001")
 *     .withCarrierName("DHL")
 *     .withDateRange(LocalDate.of(2023, 1, 1), LocalDate.of(2023, 12, 31));
 */
public class DeliverySearchCriteria {
    
    // Search fields using modern Java types
    private String deliveryId;
    private String exportId;
    private String carrierName;
    private String status;
    private LocalDate startDate;  // Modern date handling
    private LocalDate endDate;
    private String trackingNumber;
    private String contactPerson;
    private String shippingMethod;
    
    // Additional useful search fields
    private String deliveryAddress;
    private String contactPhone;
    private Boolean isActive;  // Using Boolean (nullable) instead of boolean
    
    /**
     * Default constructor creates an empty search criteria
     */
    public DeliverySearchCriteria() {
        // All fields start as null, which means "no filter applied"
    }
    
    /**
     * Constructor for common search scenario - search by IDs and status
     * @param deliveryId the delivery ID to search for
     * @param exportId the export ID to search for  
     * @param status the delivery status to filter by
     */
    public DeliverySearchCriteria(String deliveryId, String exportId, String status) {
        this.deliveryId = cleanString(deliveryId);
        this.exportId = cleanString(exportId);
        this.status = cleanString(status);
    }
    
    /**
     * Constructor for date range searches
     * @param deliveryId the delivery ID (optional)
     * @param exportId the export ID (optional)
     * @param carrierName the carrier name (optional)
     * @param status the status (optional)
     * @param startDate the start date for filtering
     * @param endDate the end date for filtering
     */
    public DeliverySearchCriteria(String deliveryId, String exportId, String carrierName, 
                                  String status, LocalDate startDate, LocalDate endDate) {
        this.deliveryId = cleanString(deliveryId);
        this.exportId = cleanString(exportId);
        this.carrierName = cleanString(carrierName);
        this.status = cleanString(status);
        this.startDate = startDate;
        this.endDate = endDate;
        
        // Validate date range
        validateDateRange();
    }
    
    // ============= BUILDER PATTERN METHODS =============
    // These methods return 'this' so you can chain them together
    
    /**
     * Sets the delivery ID to search for
     * @param deliveryId the delivery ID (null or empty means no filter)
     * @return this criteria object for method chaining
     */
    public DeliverySearchCriteria withDeliveryId(String deliveryId) {
        this.deliveryId = cleanString(deliveryId);
        return this;
    }
    
    /**
     * Sets the export ID to search for
     * @param exportId the export ID (null or empty means no filter)
     * @return this criteria object for method chaining
     */
    public DeliverySearchCriteria withExportId(String exportId) {
        this.exportId = cleanString(exportId);
        return this;
    }
    
    /**
     * Sets the carrier name to search for
     * @param carrierName the carrier name (null or empty means no filter)
     * @return this criteria object for method chaining
     */
    public DeliverySearchCriteria withCarrierName(String carrierName) {
        this.carrierName = cleanString(carrierName);
        return this;
    }
    
    /**
     * Sets the delivery status to filter by
     * @param status the status (null or empty means no filter)
     * @return this criteria object for method chaining
     */
    public DeliverySearchCriteria withStatus(String status) {
        this.status = cleanString(status);
        return this;
    }
    
    /**
     * Sets a date range for filtering deliveries
     * @param startDate the earliest date to include (null means no start limit)
     * @param endDate the latest date to include (null means no end limit)
     * @return this criteria object for method chaining
     * @throws IllegalArgumentException if end date is before start date
     */
    public DeliverySearchCriteria withDateRange(LocalDate startDate, LocalDate endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
        validateDateRange();
        return this;
    }
    
    /**
     * Sets the tracking number to search for
     * @param trackingNumber the tracking number (null or empty means no filter)
     * @return this criteria object for method chaining
     */
    public DeliverySearchCriteria withTrackingNumber(String trackingNumber) {
        this.trackingNumber = cleanString(trackingNumber);
        return this;
    }
    
    /**
     * Sets the contact person name to search for
     * @param contactPerson the contact person name (null or empty means no filter)
     * @return this criteria object for method chaining
     */
    public DeliverySearchCriteria withContactPerson(String contactPerson) {
        this.contactPerson = cleanString(contactPerson);
        return this;
    }
    
    /**
     * Sets the shipping method to filter by
     * @param shippingMethod the shipping method (null or empty means no filter)
     * @return this criteria object for method chaining
     */
    public DeliverySearchCriteria withShippingMethod(String shippingMethod) {
        this.shippingMethod = cleanString(shippingMethod);
        return this;
    }
    
    /**
     * Sets the delivery address to search for
     * @param deliveryAddress the delivery address (null or empty means no filter)
     * @return this criteria object for method chaining
     */
    public DeliverySearchCriteria withDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = cleanString(deliveryAddress);
        return this;
    }
    
    /**
     * Sets the contact phone to search for
     * @param contactPhone the contact phone (null or empty means no filter)
     * @return this criteria object for method chaining
     */
    public DeliverySearchCriteria withContactPhone(String contactPhone) {
        this.contactPhone = cleanString(contactPhone);
        return this;
    }
    
    /**
     * Sets whether to filter for active/inactive deliveries
     * @param isActive true for active deliveries, false for inactive, null for all
     * @return this criteria object for method chaining
     */
    public DeliverySearchCriteria withActiveStatus(Boolean isActive) {
        this.isActive = isActive;
        return this;
    }
    
    // ============= UTILITY METHODS =============
    
    /**
     * Checks if any search criteria have been specified
     * @return true if at least one search parameter is set, false if all are empty
     */
    public boolean hasSearchCriteria() {
        return hasValue(deliveryId) ||
               hasValue(exportId) ||
               hasValue(carrierName) ||
               hasValue(status) ||
               hasValue(trackingNumber) ||
               hasValue(contactPerson) ||
               hasValue(shippingMethod) ||
               hasValue(deliveryAddress) ||
               hasValue(contactPhone) ||
               startDate != null ||
               endDate != null ||
               isActive != null;
    }
    
    /**
     * Checks if this is an empty search (no criteria set)
     * @return true if no search criteria are specified
     */
    public boolean isEmpty() {
        return !hasSearchCriteria();
    }
    
    /**
     * Checks if this search is for a specific delivery ID only
     * @return true if only delivery ID is specified
     */
    public boolean isDeliveryIdOnlySearch() {
        return hasValue(deliveryId) && 
               !hasValue(exportId) && 
               !hasValue(carrierName) && 
               !hasValue(status) &&
               !hasValue(trackingNumber) && 
               !hasValue(contactPerson) && 
               !hasValue(shippingMethod) &&
               !hasValue(deliveryAddress) && 
               !hasValue(contactPhone) &&
               startDate == null && 
               endDate == null && 
               isActive == null;
    }
    
    /**
     * Checks if this search includes a date range
     * @return true if either start date or end date is specified
     */
    public boolean hasDateRange() {
        return startDate != null || endDate != null;
    }
    
    /**
     * Resets all search criteria to empty/null values
     * @return this criteria object for method chaining
     */
    public DeliverySearchCriteria clear() {
        deliveryId = null;
        exportId = null;
        carrierName = null;
        status = null;
        startDate = null;
        endDate = null;
        trackingNumber = null;
        contactPerson = null;
        shippingMethod = null;
        deliveryAddress = null;
        contactPhone = null;
        isActive = null;
        return this;
    }
    
    /**
     * Creates a copy of this search criteria
     * @return a new DeliverySearchCriteria with the same values
     */
    public DeliverySearchCriteria copy() {
        DeliverySearchCriteria copy = new DeliverySearchCriteria();
        copy.deliveryId = this.deliveryId;
        copy.exportId = this.exportId;
        copy.carrierName = this.carrierName;
        copy.status = this.status;
        copy.startDate = this.startDate;
        copy.endDate = this.endDate;
        copy.trackingNumber = this.trackingNumber;
        copy.contactPerson = this.contactPerson;
        copy.shippingMethod = this.shippingMethod;
        copy.deliveryAddress = this.deliveryAddress;
        copy.contactPhone = this.contactPhone;
        copy.isActive = this.isActive;
        return copy;
    }
    
    // ============= HELPER METHODS =============
    
    /**
     * Cleans a string by trimming whitespace and converting empty strings to null
     * @param value the string to clean
     * @return the cleaned string or null if empty
     */
    private String cleanString(String value) {
        if (value == null) {
            return null;
        }
        String trimmed = value.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }
    
    /**
     * Checks if a string has a meaningful value (not null and not empty)
     * @param value the string to check
     * @return true if the string has a value
     */
    private boolean hasValue(String value) {
        return value != null && !value.trim().isEmpty();
    }
    
    /**
     * Validates that the date range makes sense
     * @throws IllegalArgumentException if end date is before start date
     */
    private void validateDateRange() {
        if (startDate != null && endDate != null && endDate.isBefore(startDate)) {
            throw new IllegalArgumentException(
                String.format("End date (%s) cannot be before start date (%s)", 
                             endDate, startDate));
        }
    }
    
    // ============= STANDARD GETTERS AND SETTERS =============
    
    public String getDeliveryId() { return deliveryId; }
    public void setDeliveryId(String deliveryId) { this.deliveryId = cleanString(deliveryId); }
    
    public String getExportId() { return exportId; }
    public void setExportId(String exportId) { this.exportId = cleanString(exportId); }
    
    public String getCarrierName() { return carrierName; }
    public void setCarrierName(String carrierName) { this.carrierName = cleanString(carrierName); }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = cleanString(status); }
    
    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { 
        this.startDate = startDate; 
        validateDateRange();
    }
    
    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { 
        this.endDate = endDate; 
        validateDateRange();
    }
    
    public String getTrackingNumber() { return trackingNumber; }
    public void setTrackingNumber(String trackingNumber) { this.trackingNumber = cleanString(trackingNumber); }
    
    public String getContactPerson() { return contactPerson; }
    public void setContactPerson(String contactPerson) { this.contactPerson = cleanString(contactPerson); }
    
    public String getShippingMethod() { return shippingMethod; }
    public void setShippingMethod(String shippingMethod) { this.shippingMethod = cleanString(shippingMethod); }
    
    public String getDeliveryAddress() { return deliveryAddress; }
    public void setDeliveryAddress(String deliveryAddress) { this.deliveryAddress = cleanString(deliveryAddress); }
    
    public String getContactPhone() { return contactPhone; }
    public void setContactPhone(String contactPhone) { this.contactPhone = cleanString(contactPhone); }
    
    public Boolean getIsActive() { return isActive; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }
    
    // ============= OBJECT METHODS =============
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        
        DeliverySearchCriteria that = (DeliverySearchCriteria) obj;
        return Objects.equals(deliveryId, that.deliveryId) &&
               Objects.equals(exportId, that.exportId) &&
               Objects.equals(carrierName, that.carrierName) &&
               Objects.equals(status, that.status) &&
               Objects.equals(startDate, that.startDate) &&
               Objects.equals(endDate, that.endDate) &&
               Objects.equals(trackingNumber, that.trackingNumber) &&
               Objects.equals(contactPerson, that.contactPerson) &&
               Objects.equals(shippingMethod, that.shippingMethod) &&
               Objects.equals(deliveryAddress, that.deliveryAddress) &&
               Objects.equals(contactPhone, that.contactPhone) &&
               Objects.equals(isActive, that.isActive);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(deliveryId, exportId, carrierName, status, startDate, endDate, 
                           trackingNumber, contactPerson, shippingMethod, deliveryAddress, 
                           contactPhone, isActive);
    }
    
    @Override
    public String toString() {
        StringJoiner joiner = new StringJoiner(", ", "DeliverySearchCriteria{", "}");
        
        // Only include fields that have values to make output cleaner
        if (hasValue(deliveryId)) joiner.add("deliveryId='" + deliveryId + "'");
        if (hasValue(exportId)) joiner.add("exportId='" + exportId + "'");
        if (hasValue(carrierName)) joiner.add("carrierName='" + carrierName + "'");
        if (hasValue(status)) joiner.add("status='" + status + "'");
        if (hasValue(trackingNumber)) joiner.add("trackingNumber='" + trackingNumber + "'");
        if (hasValue(contactPerson)) joiner.add("contactPerson='" + contactPerson + "'");
        if (hasValue(shippingMethod)) joiner.add("shippingMethod='" + shippingMethod + "'");
        if (hasValue(deliveryAddress)) joiner.add("deliveryAddress='" + deliveryAddress + "'");
        if (hasValue(contactPhone)) joiner.add("contactPhone='" + contactPhone + "'");
        if (startDate != null) joiner.add("startDate=" + startDate);
        if (endDate != null) joiner.add("endDate=" + endDate);
        if (isActive != null) joiner.add("isActive=" + isActive);
        
        // If no criteria are set, show that it's empty
        if (!hasSearchCriteria()) {
            return "DeliverySearchCriteria{EMPTY}";
        }
        
        return joiner.toString();
    }
    
    /**
     * Creates a human-readable description of the search criteria
     * @return a formatted string describing what this search will find
     */
    public String getSearchDescription() {
        if (!hasSearchCriteria()) {
            return "All deliveries (no filters applied)";
        }
        
        StringJoiner description = new StringJoiner(" AND ", "Deliveries where ", "");
        
        if (hasValue(deliveryId)) description.add("delivery ID = '" + deliveryId + "'");
        if (hasValue(exportId)) description.add("export ID = '" + exportId + "'");
        if (hasValue(carrierName)) description.add("carrier name contains '" + carrierName + "'");
        if (hasValue(status)) description.add("status = '" + status + "'");
        if (hasValue(trackingNumber)) description.add("tracking number = '" + trackingNumber + "'");
        if (hasValue(contactPerson)) description.add("contact person contains '" + contactPerson + "'");
        if (hasValue(shippingMethod)) description.add("shipping method = '" + shippingMethod + "'");
        if (hasValue(deliveryAddress)) description.add("address contains '" + deliveryAddress + "'");
        if (hasValue(contactPhone)) description.add("phone contains '" + contactPhone + "'");
        
        if (startDate != null && endDate != null) {
            description.add("delivery date between " + startDate + " and " + endDate);
        } else if (startDate != null) {
            description.add("delivery date >= " + startDate);
        } else if (endDate != null) {
            description.add("delivery date <= " + endDate);
        }
        
        if (isActive != null) {
            description.add("active status = " + isActive);
        }
        
        return description.toString();
    }
}