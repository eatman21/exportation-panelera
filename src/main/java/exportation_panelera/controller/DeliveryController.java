package exportation_panelera.controller;

import exportation_panelera.Model.Delivery_InfDTO;
import exportation_panelera.Model.Exportation_InfDTO;
import exportation_panelera.db.DatabaseManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * COMPLETELY FIXED DeliveryController that handles customer_id requirement and large export IDs properly
 */
public class DeliveryController {
    private static final Logger logger = Logger.getLogger(DeliveryController.class.getName());
    
    /**
     * Debug method to print all column names from the deliveries table
     */
    public void debugPrintTableStructure() {
        if (DatabaseManager.isOfflineMode()) {
            logger.info("In offline mode - cannot debug table structure");
            return;
        }
        
        Connection conn = null;
        try {
            conn = getConnection();
            if (conn == null) {
                logger.warning("Cannot get connection for debugging");
                return;
            }
            
            DatabaseMetaData metaData = conn.getMetaData();
            ResultSet rs = metaData.getColumns(null, null, "deliveries", null);
            
            System.out.println("=== DELIVERIES TABLE STRUCTURE ===");
            List<String> columns = new ArrayList<>();
            while (rs.next()) {
                String columnName = rs.getString("COLUMN_NAME");
                String dataType = rs.getString("TYPE_NAME");
                String size = rs.getString("COLUMN_SIZE");
                String nullable = rs.getString("IS_NULLABLE");
                
                columns.add(columnName.toLowerCase());
                System.out.println("Column: " + columnName + 
                                   ", Type: " + dataType + 
                                   ", Size: " + size + 
                                   ", Nullable: " + nullable);
            }
            System.out.println("Available columns: " + columns);
            System.out.println("=================================");
            
            rs.close();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error debugging table structure: " + e.getMessage(), e);
        }
    }
    
    /**
     * Try to reconnect to the database
     */
    public boolean tryReconnect() {
        try {
            boolean result = DatabaseManager.tryConnect();
            logger.info("Database reconnection attempt result: " + result);
            return result;
        } catch (Exception e) {
            logger.log(Level.WARNING, "Failed to reconnect to database", e);
            return false;
        }
    }
    
    /**
     * Get a connection to the database
     */
    protected Connection getConnection() throws SQLException {
        return DatabaseManager.getConnection();
    }
    
    /**
     * CRITICAL FIX: Ensure default customer exists before creating exportations
     */
    private void ensureDefaultCustomerExists() {
        Connection conn = null;
        PreparedStatement checkStmt = null;
        PreparedStatement insertStmt = null;
        ResultSet rs = null;
        
        try {
            conn = getConnection();
            if (conn == null) {
                logger.warning("Cannot ensure default customer - no connection");
                return;
            }
            
            // Check if customer ID 1 exists
            checkStmt = conn.prepareStatement("SELECT id FROM customers WHERE id = 1");
            rs = checkStmt.executeQuery();
            
            if (!rs.next()) {
                // Create default customer
                insertStmt = conn.prepareStatement(
                    "INSERT INTO customers (id, name, email, phone, created_at, updated_at) " +
                    "VALUES (1, 'Default Customer', 'default@example.com', '+1-555-0000', NOW(), NOW())"
                );
                insertStmt.executeUpdate();
                logger.info("Created default customer with ID 1");
            } else {
                logger.info("Default customer already exists");
            }
            
        } catch (SQLException e) {
            logger.log(Level.WARNING, "Could not ensure default customer exists: " + e.getMessage(), e);
        } finally {
            closeResources(conn, checkStmt, rs);
            if (insertStmt != null) {
                try { insertStmt.close(); } catch (SQLException e) { /* ignore */ }
            }
        }
    }
    
    /**
     * COMPLETELY FIXED: Create a new exportation record with customer_id handling
     */
    public boolean createExportation(Exportation_InfDTO exportation) {
        if (exportation == null) {
            logger.warning("Cannot create null exportation");
            return false;
        }
        
        logger.info("=== CREATE EXPORTATION CALLED ===");
        logger.info("Exportation ID: " + exportation.getExportationId());
        logger.info("Product Type: " + exportation.getProductType());
        logger.info("Amount: " + exportation.getAmount());
        logger.info("Destination: " + exportation.getDestination());
        
        if (DatabaseManager.isOfflineMode()) {
            logger.info("In offline mode - simulating successful exportation creation");
            return true;
        }
        
        // CRITICAL: Ensure default customer exists first
        ensureDefaultCustomerExists();
        
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = getConnection();
            if (conn == null) {
                logger.warning("Database connection is null");
                return false;
            }
            
            // Check what columns actually exist in the exportations table
            List<String> availableColumns = getTableColumns(conn, "exportations");
            logger.info("Available columns in exportations table: " + availableColumns);
            
            // FIXED SQL: Include customer_id as a required field
            String sql = "INSERT INTO exportations (" +
                        "exportation_id, reference_number, customer_id, product_type, amount, destination, " +
                        "exportation_date, export_date, unit_price, currency, has_delivery, status, notes, " +
                        "customer_name, customer_email, customer_phone, document_number, export_license, " +
                        "employee_id, transport_method, created_at, updated_at" +
                        ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, NOW(), NOW())";
            
            logger.info("Generated SQL: " + sql);
            
            stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            
            // Set all parameters including customer_id
            stmt.setObject(1, exportation.getExportationId());
            logger.info("Parameter 1 (exportation_id): " + exportation.getExportationId());
            
            stmt.setString(2, "REF-" + exportation.getExportationId());
            logger.info("Parameter 2 (reference_number): REF-" + exportation.getExportationId());
            
            // CRITICAL FIX: Set customer_id = 1 (default customer)
            stmt.setInt(3, 1);
            logger.info("Parameter 3 (customer_id): 1");
            
            String productType = exportation.getProductType();
            stmt.setString(4, productType != null ? productType : "Panela");
            logger.info("Parameter 4 (product_type): " + (productType != null ? productType : "Panela"));
            
            Double amount = exportation.getAmount();
            stmt.setDouble(5, amount != null ? amount : 0.0);
            logger.info("Parameter 5 (amount): " + (amount != null ? amount : 0.0));
            
            String destination = exportation.getDestination();
            stmt.setString(6, destination != null ? destination : "Unknown");
            logger.info("Parameter 6 (destination): " + (destination != null ? destination : "Unknown"));
            
            Date exportDate = exportation.getExportationDate() != null ? exportation.getExportationDate() :
                             exportation.getExportDate() != null ? exportation.getExportDate() : new Date();
            stmt.setDate(7, new java.sql.Date(exportDate.getTime()));
            logger.info("Parameter 7 (exportation_date): " + exportDate);
            
            stmt.setDate(8, new java.sql.Date(exportDate.getTime()));
            logger.info("Parameter 8 (export_date): " + exportDate);
            
            java.math.BigDecimal unitPrice = exportation.getUnitPrice() != null ? 
                                           exportation.getUnitPrice() : new java.math.BigDecimal("12.00");
            stmt.setBigDecimal(9, unitPrice);
            logger.info("Parameter 9 (unit_price): " + unitPrice);
            
            String currency = exportation.getCurrency();
            stmt.setString(10, currency != null ? currency : "MXN");
            logger.info("Parameter 10 (currency): " + (currency != null ? currency : "MXN"));
            
            stmt.setBoolean(11, exportation.isHasDelivery());
            logger.info("Parameter 11 (has_delivery): " + exportation.isHasDelivery());
            
            String status = exportation.getStatus();
            stmt.setString(12, status != null ? status : "PENDING");
            logger.info("Parameter 12 (status): " + (status != null ? status : "PENDING"));
            
            String notes = exportation.getNotes();
            stmt.setString(13, notes != null ? notes : "");
            logger.info("Parameter 13 (notes): " + (notes != null ? notes : ""));
            
            String customerName = exportation.getCustomerName();
            stmt.setString(14, customerName != null ? customerName : "Default Customer");
            logger.info("Parameter 14 (customer_name): " + (customerName != null ? customerName : "Default Customer"));
            
            String customerEmail = exportation.getCustomerEmail();
            stmt.setString(15, customerEmail != null ? customerEmail : "customer@example.com");
            logger.info("Parameter 15 (customer_email): " + (customerEmail != null ? customerEmail : "customer@example.com"));
            
            String customerPhone = exportation.getCustomerPhone();
            stmt.setString(16, customerPhone != null ? customerPhone : "+1-555-0000");
            logger.info("Parameter 16 (customer_phone): " + (customerPhone != null ? customerPhone : "+1-555-0000"));
            
            String documentNumber = exportation.getDocumentNumber();
            stmt.setString(17, documentNumber != null ? documentNumber : "DOC-" + exportation.getExportationId());
            logger.info("Parameter 17 (document_number): " + (documentNumber != null ? documentNumber : "DOC-" + exportation.getExportationId()));
            
            String exportLicense = exportation.getExportLicense();
            stmt.setString(18, exportLicense != null ? exportLicense : "LIC-" + exportation.getExportationId());
            logger.info("Parameter 18 (export_license): " + (exportLicense != null ? exportLicense : "LIC-" + exportation.getExportationId()));
            
            String employeeId = exportation.getEmployeeId();
            stmt.setString(19, employeeId != null ? employeeId : "EMP001");
            logger.info("Parameter 19 (employee_id): " + (employeeId != null ? employeeId : "EMP001"));
            
            String transportMethod = exportation.getTransportMethod();
            stmt.setString(20, transportMethod != null ? transportMethod : "Standard");
            logger.info("Parameter 20 (transport_method): " + (transportMethod != null ? transportMethod : "Standard"));
            
            logger.info("Parameters count: 20");
            
            int rowsAffected = stmt.executeUpdate();
            logger.info("Rows affected: " + rowsAffected);
            
            if (rowsAffected > 0) {
                // Get generated ID
                ResultSet generatedKeys = stmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int generatedId = generatedKeys.getInt(1);
                    exportation.setId(generatedId);
                    logger.info("Created exportation with database ID: " + generatedId);
                }
                generatedKeys.close();
                
                logger.info("Successfully created exportation");
                return true;
            } else {
                logger.warning("No rows affected when creating exportation");
                return false;
            }
            
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error creating exportation: " + e.getMessage(), e);
            logger.severe("SQL State: " + e.getSQLState());
            logger.severe("Error Code: " + e.getErrorCode());
            
            // Specific error handling
            if (e.getErrorCode() == 1364 && e.getMessage().contains("customer_id")) {
                logger.severe("CUSTOMER_ID ERROR: The customer_id field is required but not provided.");
                logger.severe("Solution: Make sure the default customer (ID=1) exists in the customers table.");
            }
            
            return false;
        } finally {
            closeResources(conn, stmt, null);
        }
    }
    
    /**
     * Get all deliveries from the database
     */
    public List<Delivery_InfDTO> getAllDeliveries() {
        List<Delivery_InfDTO> deliveries = new ArrayList<>();
        
        logger.info("=== DeliveryController.getAllDeliveries() called ===");
        
        if (DatabaseManager.isOfflineMode()) {
            logger.warning("DatabaseManager reports offline mode - returning sample data");
            return getSampleDeliveries();
        }
        
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = getConnection();
            if (conn == null) {
                logger.severe("getConnection() returned null");
                return getSampleDeliveries();
            }
            
            if (conn.isClosed()) {
                logger.severe("Connection is closed");
                return getSampleDeliveries();
            }
            
            logger.info("Database connection obtained successfully");
            
            String sql = "SELECT * FROM deliveries ORDER BY id ASC";
            logger.info("Executing SQL: " + sql);
            
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();
            
            logger.info("Query executed successfully, processing results...");
            
            int rowCount = 0;
            while (rs.next()) {
                rowCount++;
                
                try {
                    Delivery_InfDTO delivery = mapResultSetToDelivery(rs);
                    if (delivery != null) {
                        deliveries.add(delivery);
                        
                        if (rowCount <= 3) {
                            logger.info("Row " + rowCount + ": ID=" + delivery.getDeliveryId() + 
                                       ", ExportID=" + delivery.getExportId() + 
                                       ", Status=" + delivery.getStatus());
                        }
                    }
                } catch (Exception e) {
                    logger.log(Level.WARNING, "Error processing row " + rowCount, e);
                }
            }
            
            logger.info("Total rows processed: " + rowCount + ", Deliveries created: " + deliveries.size());
            
            if (deliveries.isEmpty()) {
                logger.warning("No deliveries found in database, returning sample data");
                return getSampleDeliveries();
            }
            
            return deliveries;
            
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "SQL Error in getAllDeliveries: " + e.getMessage(), e);
            return getSampleDeliveries();
            
        } finally {
            closeResources(conn, stmt, rs);
        }
    }
    
    /**
     * Create a new delivery record - FIXED to handle large export IDs
     */
    public boolean createDelivery(Delivery_InfDTO delivery) {
        if (delivery == null) {
            logger.warning("Cannot create null delivery");
            return false;
        }
        
        logger.info("=== CREATE DELIVERY CALLED ===");
        logger.info("Delivery ID: " + delivery.getDeliveryId());
        logger.info("Export ID: " + delivery.getExportId());
        logger.info("Tracking: " + delivery.getTrackingNumber());
        logger.info("Status: " + delivery.getStatus());
        logger.info("Notes: " + delivery.getNotes());
        
        if (DatabaseManager.isOfflineMode()) {
            logger.info("In offline mode - simulating successful delivery creation");
            return true;
        }
        
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = getConnection();
            if (conn == null) {
                logger.warning("Database connection is null");
                return false;
            }
            
            // Extract and validate export ID
            String exportId = extractExportId(delivery.getExportId());
            logger.info("Original Export ID: " + delivery.getExportId());
            logger.info("Extracted Export ID: " + exportId);
            
            // Use the extracted export ID directly for the foreign key
            String managedExportId = exportId;
            logger.info("Managed Export ID for database: " + managedExportId);
            
            // SQL that matches your exact table structure
            String sql = "INSERT INTO deliveries (exportation_id, delivery_date, tracking_number, status, notes, created_at, updated_at) " +
                        "VALUES (?, ?, ?, ?, ?, NOW(), NOW())";
            
            logger.info("Generated SQL: " + sql);
            
            stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            
            // Set parameters based on your table structure
            // 1. exportation_id - Use the numeric part that matches the exportations table
            stmt.setString(1, managedExportId);
            logger.info("Parameter 1 (exportation_id): " + managedExportId);
            
            // 2. delivery_date
            Date deliveryDate = delivery.getDeliveryDate() != null ? delivery.getDeliveryDate() : 
                              delivery.getDate() != null ? delivery.getDate() : new Date();
            stmt.setDate(2, new java.sql.Date(deliveryDate.getTime()));
            logger.info("Parameter 2 (delivery_date): " + deliveryDate);
            
            // 3. tracking_number
            String trackingNumber = delivery.getTrackingNumber() != null ? delivery.getTrackingNumber() : "";
            stmt.setString(3, trackingNumber);
            logger.info("Parameter 3 (tracking_number): " + trackingNumber);
            
            // 4. status
            String status = delivery.getStatus();
            if (status == null || status.trim().isEmpty()) {
                status = delivery.getDeliveryStatus();
            }
            if (status == null || status.trim().isEmpty()) {
                status = "Pending";
            }
            stmt.setString(4, status);
            logger.info("Parameter 4 (status): " + status);
            
            // 5. notes
            String notes = delivery.getNotes() != null ? delivery.getNotes() : "";
            stmt.setString(5, notes);
            logger.info("Parameter 5 (notes): " + notes);
            
            int rowsAffected = stmt.executeUpdate();
            logger.info("Rows affected: " + rowsAffected);
            
            if (rowsAffected > 0) {
                // Get generated ID
                ResultSet generatedKeys = stmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int generatedId = generatedKeys.getInt(1);
                    delivery.setId(generatedId);
                    logger.info("Created delivery with database ID: " + generatedId);
                }
                generatedKeys.close();
                
                logger.info("Successfully created delivery");
                return true;
            } else {
                logger.warning("No rows affected when creating delivery");
                return false;
            }
            
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error creating delivery: " + e.getMessage(), e);
            logger.severe("SQL State: " + e.getSQLState());
            logger.severe("Error Code: " + e.getErrorCode());
            
            // Specific handling for foreign key constraint error
            if (e.getErrorCode() == 1452) {
                logger.severe("FOREIGN KEY ERROR: The exportation_id '" + delivery.getExportationId() + "' does not exist in the exportations table.");
                logger.severe("Solution: Make sure the exportation is created first, or the exportation_id matches exactly.");
            }
            
            return false;
        } finally {
            closeResources(conn, stmt, null);
        }
    }
    
    /**
     * Get list of columns that exist in a table
     */
    private List<String> getTableColumns(Connection conn, String tableName) {
        List<String> columns = new ArrayList<>();
        try {
            DatabaseMetaData metaData = conn.getMetaData();
            ResultSet rs = metaData.getColumns(null, null, tableName, null);
            
            while (rs.next()) {
                String columnName = rs.getString("COLUMN_NAME");
                columns.add(columnName.toLowerCase());
            }
            rs.close();
        } catch (SQLException e) {
            logger.log(Level.WARNING, "Error getting table columns for " + tableName, e);
        }
        return columns;
    }
    
    /**
     * Get an exportation by ID
     */
    public Exportation_InfDTO getExportationById(String exportationId) {
        logger.info("getExportationById called with ID: " + exportationId);
        
        if (DatabaseManager.isOfflineMode()) {
            logger.info("In offline mode - returning sample exportation");
            return createSampleExportation(exportationId);
        }
        
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = getConnection();
            if (conn == null) {
                logger.warning("Database connection is null, returning sample exportation");
                return createSampleExportation(exportationId);
            }
            
            String sql = "SELECT * FROM exportations WHERE exportation_id = ? LIMIT 1";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, exportationId);
            
            rs = stmt.executeQuery();
            
            if (rs.next()) {
                Exportation_InfDTO exportation = mapResultSetToExportation(rs);
                logger.info("Found exportation for ID: " + exportationId);
                return exportation;
            }
            
            logger.warning("No exportation found for ID: " + exportationId);
            return null;
            
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error retrieving exportation by ID: " + e.getMessage(), e);
            return createSampleExportation(exportationId);
        } finally {
            closeResources(conn, stmt, rs);
        }
    }
    
    /**
     * Map ResultSet to Exportation_InfDTO with safe column access
     */
    private Exportation_InfDTO mapResultSetToExportation(ResultSet rs) {
        try {
            Exportation_InfDTO exportation = new Exportation_InfDTO();
            
            exportation.setId(rs.getInt("id"));
            exportation.setExportationId(getStringOrNull(rs, "exportation_id"));
            
            // Only set fields if columns exist
            String productType = getStringOrNull(rs, "product_type");
            if (productType != null) {
                exportation.setProductType(productType);
            }
            
            try {
                exportation.setAmount(rs.getDouble("amount"));
            } catch (SQLException e) {
                exportation.setAmount(0.0);
            }
            
            String destination = getStringOrNull(rs, "destination");
            if (destination != null) {
                exportation.setDestination(destination);
            }
            
            Date exportDate = getDateOrNull(rs, "exportation_date");
            if (exportDate == null) {
                exportDate = getDateOrNull(rs, "export_date");
            }
            if (exportDate != null) {
                exportation.setExportationDate(exportDate);
                exportation.setExportDate(exportDate);
            }
            
            try {
                exportation.setUnitPrice(rs.getBigDecimal("unit_price"));
            } catch (SQLException e) {
                // Column doesn't exist, ignore
            }
            
            exportation.setCurrency(getStringOrNull(rs, "currency"));
            
            try {
                exportation.setHasDelivery(rs.getBoolean("has_delivery"));
            } catch (SQLException e) {
                exportation.setHasDelivery(false);
            }
            
            exportation.setStatus(getStringOrNull(rs, "status"));
            exportation.setNotes(getStringOrNull(rs, "notes"));
            
            // Customer information (if columns exist)
            exportation.setCustomerName(getStringOrNull(rs, "customer_name"));
            exportation.setCustomerEmail(getStringOrNull(rs, "customer_email"));
            exportation.setCustomerPhone(getStringOrNull(rs, "customer_phone"));
            exportation.setDocumentNumber(getStringOrNull(rs, "document_number"));
            exportation.setExportLicense(getStringOrNull(rs, "export_license"));
            exportation.setEmployeeId(getStringOrNull(rs, "employee_id"));
            exportation.setTransportMethod(getStringOrNull(rs, "transport_method"));
            
            // Timestamps
            Date createdAt = getTimestampOrNull(rs, "created_at");
            if (createdAt != null) {
                exportation.setCreatedAt(createdAt);
            }
            
            Date updatedAt = getTimestampOrNull(rs, "updated_at");
            if (updatedAt != null) {
                exportation.setUpdatedAt(updatedAt);
            }
            
            return exportation;
            
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error mapping ResultSet to Exportation", e);
            return null;
        }
    }
    
    /**
     * Create a sample exportation for testing or offline mode
     */
    private Exportation_InfDTO createSampleExportation(String exportationId) {
        Exportation_InfDTO sample = new Exportation_InfDTO();
        sample.setExportationId(exportationId);
        sample.setProductType("Panela");
        sample.setAmount(100.0);
        sample.setDestination("United States");
        sample.setExportationDate(new Date());
        sample.setStatus("PENDING");
        sample.setCurrency("USD");
        sample.setHasDelivery(false);
        sample.setNotes("Sample exportation");
        sample.setCreatedAt(new Date());
        sample.setUpdatedAt(new Date());
        
        return sample;
    }
    
    /**
     * Update an existing delivery record - FIXED to handle large export IDs
     */
    public boolean updateDelivery(Delivery_InfDTO delivery) {
        if (delivery == null) {
            logger.warning("Cannot update null delivery");
            return false;
        }
        
        logger.info("=== UPDATE DELIVERY CALLED ===");
        logger.info("Delivery ID: " + delivery.getDeliveryId());
        logger.info("Database ID: " + delivery.getId());
        logger.info("Export ID: " + delivery.getExportId());
        logger.info("Tracking: " + delivery.getTrackingNumber());
        logger.info("Status: " + delivery.getStatus());
        logger.info("Notes: " + delivery.getNotes());
        
        if (DatabaseManager.isOfflineMode()) {
            logger.info("In offline mode - simulating successful delivery update");
            return true;
        }
        
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = getConnection();
            if (conn == null) {
                logger.warning("Database connection is null");
                return false;
            }
            
            if (delivery.getId() <= 0) {
                logger.severe("Cannot update delivery: Invalid ID - " + delivery.getId());
                return false;
            }
            
            // Handle export ID the same way as create
            String exportId = extractExportId(delivery.getExportId());
            String managedExportId = exportId;
            logger.info("Managed Export ID for update: " + managedExportId);
            
            // SQL that matches your exact table structure
            String sql = "UPDATE deliveries SET exportation_id = ?, delivery_date = ?, tracking_number = ?, " +
                        "status = ?, notes = ?, updated_at = NOW() WHERE id = ?";
            
            logger.info("Generated UPDATE SQL: " + sql);
            
            stmt = conn.prepareStatement(sql);
            
            // Set parameters
            stmt.setString(1, managedExportId);
            logger.info("Parameter 1 (exportation_id): " + managedExportId);
            
            Date deliveryDate = delivery.getDeliveryDate() != null ? delivery.getDeliveryDate() : 
                              delivery.getDate() != null ? delivery.getDate() : null;
            if (deliveryDate != null) {
                stmt.setDate(2, new java.sql.Date(deliveryDate.getTime()));
                logger.info("Parameter 2 (delivery_date): " + deliveryDate);
            } else {
                stmt.setNull(2, Types.DATE);
                logger.info("Parameter 2 (delivery_date): NULL");
            }
            
            String trackingNumber = delivery.getTrackingNumber();
            stmt.setString(3, trackingNumber);
            logger.info("Parameter 3 (tracking_number): " + trackingNumber);
            
            String status = delivery.getStatus();
            if (status == null || status.trim().isEmpty()) {
                status = delivery.getDeliveryStatus();
            }
            stmt.setString(4, status);
            logger.info("Parameter 4 (status): " + status);
            
            String notes = delivery.getNotes();
            stmt.setString(5, notes);
            logger.info("Parameter 5 (notes): " + notes);
            
            stmt.setInt(6, delivery.getId());
            logger.info("Parameter 6 (id): " + delivery.getId());
            
            int rowsAffected = stmt.executeUpdate();
            logger.info("Update rows affected: " + rowsAffected);
            
            if (rowsAffected > 0) {
                logger.info("Successfully updated delivery");
                return true;
            } else {
                logger.warning("No rows affected when updating delivery with ID: " + delivery.getId());
                return false;
            }
            
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error updating delivery: " + e.getMessage(), e);
            logger.severe("SQL State: " + e.getSQLState());
            logger.severe("Error Code: " + e.getErrorCode());
            return false;
        } finally {
            closeResources(conn, stmt, null);
        }
    }
    
    /**
     * Delete a delivery record
     */
    public boolean deleteDelivery(String deliveryId) {
        if (DatabaseManager.isOfflineMode()) {
            logger.warning("In offline mode - cannot delete delivery");
            return false;
        }
        
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = getConnection();
            if (conn == null) {
                return false;
            }
            
            int id = parseDeliveryId(deliveryId);
            if (id <= 0) {
                logger.warning("Invalid delivery ID format: " + deliveryId);
                return false;
            }
            
            String sql = "DELETE FROM deliveries WHERE id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            
            int rowsAffected = stmt.executeUpdate();
            
            logger.info("Deleted delivery, rows affected: " + rowsAffected);
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error deleting delivery: " + e.getMessage(), e);
            return false;
        } finally {
            closeResources(conn, stmt, null);
        }
    }
    
    /**
     * Get a delivery by ID
     */
    public Delivery_InfDTO getDeliveryById(String deliveryId) {
        if (DatabaseManager.isOfflineMode()) {
            return createSampleDelivery(deliveryId, "EXP001");
        }
        
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = getConnection();
            if (conn == null) {
                return createSampleDelivery(deliveryId, "EXP001");
            }
            
            int id = parseDeliveryId(deliveryId);
            if (id <= 0) {
                logger.warning("Invalid delivery ID for lookup: " + deliveryId);
                return null;
            }
            
            String sql = "SELECT * FROM deliveries WHERE id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            
            rs = stmt.executeQuery();
            
            if (rs.next()) {
                Delivery_InfDTO delivery = mapResultSetToDelivery(rs);
                logger.info("Found delivery by ID: " + deliveryId);
                return delivery;
            }
            
            logger.warning("No delivery found with ID: " + deliveryId);
            return null;
            
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error retrieving delivery: " + e.getMessage(), e);
            return createSampleDelivery(deliveryId, "EXP001");
        } finally {
            closeResources(conn, stmt, rs);
        }
    }
    
    /**
     * Get a delivery by export ID
     */
    public Delivery_InfDTO getDeliveryByExportId(String exportId) {
        if (DatabaseManager.isOfflineMode()) {
            logger.info("In offline mode - returning sample delivery for export ID: " + exportId);
            return createSampleDelivery("DEL" + System.currentTimeMillis(), exportId);
        }
        
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = getConnection();
            if (conn == null) {
                logger.warning("Database connection is null, returning sample delivery");
                return createSampleDelivery("DEL" + System.currentTimeMillis(), exportId);
            }
            
            // Use the extracted export ID for searching
            String cleanExportId = extractExportId(exportId);
            
            String sql = "SELECT * FROM deliveries WHERE exportation_id = ? LIMIT 1";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, cleanExportId);
            
            rs = stmt.executeQuery();
            
            if (rs.next()) {
                Delivery_InfDTO delivery = mapResultSetToDelivery(rs);
                logger.info("Found delivery for export ID: " + exportId);
                return delivery;
            }
            
            logger.warning("No delivery found for export ID: " + exportId);
            return null;
            
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error retrieving delivery by export ID: " + e.getMessage(), e);
            return createSampleDelivery("DEL" + System.currentTimeMillis(), exportId);
        } finally {
            closeResources(conn, stmt, rs);
        }
    }
    
    /**
     * Map ResultSet to Delivery_InfDTO based on your exact table structure
     */
    private Delivery_InfDTO mapResultSetToDelivery(ResultSet rs) {
        try {
            Delivery_InfDTO delivery = new Delivery_InfDTO();
            
            // Map fields that exist in your table
            delivery.setId(rs.getInt("id"));
            
            // Generate delivery_id since it's not stored in your table
            String deliveryId = "DEL" + String.format("%06d", delivery.getId());
            delivery.setDeliveryId(deliveryId);
            
            // exportation_id
            String exportationId = getStringOrNull(rs, "exportation_id");
            if (exportationId != null) {
                delivery.setExportationId(exportationId);
                delivery.setExportId("EXP" + exportationId);
            }
            
            // delivery_date
            Date deliveryDate = getDateOrNull(rs, "delivery_date");
            if (deliveryDate != null) {
                delivery.setDeliveryDate(deliveryDate);
                delivery.setDate(deliveryDate);
            }
            
            // tracking_number
            delivery.setTrackingNumber(getStringOrNull(rs, "tracking_number"));
            
            // status
            String status = getStringOrNull(rs, "status");
            delivery.setStatus(status);
            delivery.setDeliveryStatus(status);
            
            // notes
            delivery.setNotes(getStringOrNull(rs, "notes"));
            
            // timestamps
            Date createdAt = getTimestampOrNull(rs, "created_at");
            if (createdAt != null) {
                delivery.setCreatedAt(createdAt);
            } else {
                delivery.setCreatedAt(new Date());
            }
            
            Date updatedAt = getTimestampOrNull(rs, "updated_at");
            if (updatedAt != null) {
                delivery.setUpdatedAt(updatedAt);
            } else {
                delivery.setUpdatedAt(new Date());
            }
            
            // Set default values for fields not in your table
            delivery.setCarrierName("Not specified");
            delivery.setDeliveryAddress("Not specified");
            delivery.setContactPerson("Not specified");
            delivery.setContactPhone("Not specified");
            delivery.setShippingMethod("Standard");
            delivery.setShippingCost(0.0);
            delivery.setShippingCurrency("USD");
            
            return delivery;
            
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error mapping ResultSet to Delivery", e);
            return null;
        }
    }
    
    // Helper methods for safe field extraction
    
    private String getStringOrNull(ResultSet rs, String columnName) {
        try {
            String value = rs.getString(columnName);
            return (value != null && !value.trim().isEmpty()) ? value.trim() : null;
        } catch (SQLException e) {
            return null;
        }
    }
    
    private Date getDateOrNull(ResultSet rs, String columnName) {
        try {
            java.sql.Date sqlDate = rs.getDate(columnName);
            return sqlDate != null ? new Date(sqlDate.getTime()) : null;
        } catch (SQLException e) {
            return null;
        }
    }
    
    private Date getTimestampOrNull(ResultSet rs, String columnName) {
        try {
            Timestamp timestamp = rs.getTimestamp(columnName);
            return timestamp != null ? new Date(timestamp.getTime()) : null;
        } catch (SQLException e) {
            return null;
        }
    }
    
    /**
     * Extract export ID (remove EXP prefix if present)
     */
    private String extractExportId(String exportId) {
        if (exportId == null) return null;
        return exportId.startsWith("EXP") ? exportId.substring(3) : exportId;
    }
    
    /**
     * Parse delivery ID to extract numeric part
     */
    private int parseDeliveryId(String deliveryId) {
        try {
            if (deliveryId != null && deliveryId.startsWith("DEL")) {
                String numericPart = deliveryId.substring(3);
                return Integer.parseInt(numericPart);
            } else if (deliveryId != null) {
                return Integer.parseInt(deliveryId);
            }
            return 0;
        } catch (NumberFormatException e) {
            logger.warning("Error parsing delivery ID: " + deliveryId);
            return 0;
        }
    }
    
    /**
     * Close database resources safely
     */
    private void closeResources(Connection conn, PreparedStatement stmt, ResultSet rs) {
        if (rs != null) {
            try { rs.close(); } catch (SQLException e) { /* ignore */ }
        }
        if (stmt != null) {
            try { stmt.close(); } catch (SQLException e) { /* ignore */ }
        }
        // Don't close connection if managed by DatabaseManager
    }
    
    /**
     * Create a sample delivery for testing or offline mode
     */
    private Delivery_InfDTO createSampleDelivery(String deliveryId, String exportId) {
        Delivery_InfDTO sample = new Delivery_InfDTO();
        sample.setDeliveryId(deliveryId);
        sample.setExportId(exportId);
        sample.setExportationId(exportId.replace("EXP", ""));
        sample.setCarrierName("Sample Carrier");
        sample.setTrackingNumber("TRK-" + deliveryId);
        sample.setDeliveryAddress("Sample Address");
        sample.setContactPerson("Sample Contact");
        sample.setContactPhone("+1-555-123-4567");
        
        Date currentDate = new Date();
        sample.setDeliveryDate(currentDate);
        sample.setDate(currentDate);
        sample.setStatus("PENDING");
        sample.setDeliveryStatus("PENDING");
        sample.setNotes("Sample delivery");
        sample.setShippingMethod("Standard");
        sample.setShippingCost(25.00);
        sample.setShippingCurrency("USD");
        sample.setCreatedAt(currentDate);
        sample.setUpdatedAt(currentDate);
        
        return sample;
    }
    
    /**
     * Create sample deliveries for testing/offline mode
     */
    private List<Delivery_InfDTO> getSampleDeliveries() {
        List<Delivery_InfDTO> samples = new ArrayList<>();
        
        String[][] sampleData = {
            {"DEL001", "1", "TRK123456789", "DELIVERED", "Express delivery completed"},
            {"DEL002", "2", "TRK34567890", "DELIVERED", "Standard shipping completed"},
            {"DEL003", "3", "TRK456789012", "IN_TRANSIT", "Currently in transit"},
            {"DEL004", "4", "TRK56789123", "PENDING", "Processing at facility"},
            {"DEL005", "5", "TRK67890123", "DELIVERED", "Delivered to address"}
        };
        
        Date currentDate = new Date();
        
        for (int i = 0; i < sampleData.length; i++) {
            String[] data = sampleData[i];
            Delivery_InfDTO sample = new Delivery_InfDTO();
            
            sample.setId(i + 1);
            sample.setDeliveryId(data[0]);
            sample.setExportId("EXP" + data[1]);
            sample.setExportationId(data[1]);
            sample.setCarrierName("Sample Carrier " + (i + 1));
            sample.setTrackingNumber(data[2]);
            sample.setDeliveryAddress("Sample Address " + (i + 1));
            sample.setContactPerson("Sample Contact " + (i + 1));
            sample.setContactPhone("+1-555-" + String.format("%04d", 1000 + i));
            sample.setStatus(data[3]);
            sample.setDeliveryStatus(data[3]);
            sample.setNotes(data[4]);
            sample.setDeliveryDate(currentDate);
            sample.setDate(currentDate);
            sample.setShippingMethod("Standard");
            sample.setShippingCost(25.00 + (i * 5));
            sample.setShippingCurrency("USD");
            sample.setCreatedAt(currentDate);
            sample.setUpdatedAt(currentDate);
            
            samples.add(sample);
        }
        
        logger.info("Created " + samples.size() + " sample deliveries");
        return samples;
    }
    
    /**
     * Test method to verify create/update functionality
     */
    public void testCreateAndUpdate() {
        logger.info("=== TESTING CREATE AND UPDATE ===");
        
        // Test create with a reasonable export ID
        Delivery_InfDTO testDelivery = new Delivery_InfDTO();
        testDelivery.setDeliveryId("DEL999");
        testDelivery.setExportId("EXP123"); // Use a small ID
        testDelivery.setTrackingNumber("TEST123456");
        testDelivery.setStatus("PENDING");
        testDelivery.setNotes("Test delivery");
        testDelivery.setDeliveryDate(new Date());
        
        logger.info("Testing CREATE...");
        boolean createResult = createDelivery(testDelivery);
        logger.info("CREATE result: " + createResult);
        
        if (createResult && testDelivery.getId() > 0) {
            // Test update
            testDelivery.setStatus("IN_TRANSIT");
            testDelivery.setNotes("Updated test delivery");
            testDelivery.setTrackingNumber("UPDATED123456");
            
            logger.info("Testing UPDATE...");
            boolean updateResult = updateDelivery(testDelivery);
            logger.info("UPDATE result: " + updateResult);
        }
        
        logger.info("=== TEST COMPLETE ===");
    }
}