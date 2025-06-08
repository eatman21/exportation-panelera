package exportation_panelera.dao;

import exportation_panelera.Model.Delivery_InfDTO;
import exportation_panelera.db.DatabaseManager;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DeliveryDAO {
    private static final Logger logger = Logger.getLogger(DeliveryDAO.class.getName());
    private Connection connection;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    
    public DeliveryDAO() {
        try {
            this.connection = DatabaseManager.getConnection();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error creating DeliveryDAO: " + e.getMessage(), e);
        }
    }
    
    /**
     * Get all deliveries from the database
     */
    public List<Delivery_InfDTO> getAllDeliveries() {
        List<Delivery_InfDTO> deliveries = new ArrayList<>();
        
        try {
            // Make sure we have a connection
            if (connection == null || connection.isClosed()) {
                connection = DatabaseManager.getConnection();
                if (connection == null) {
                    logger.warning("Could not establish database connection");
                    return deliveries; // Return empty list
                }
            }
            
            String query = "SELECT * FROM deliveries ORDER BY id ASC";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet rs = statement.executeQuery();
            
            logger.info("Executing query: " + query);
            
            while (rs.next()) {
                Delivery_InfDTO delivery = mapResultSetToDTO(rs);
                deliveries.add(delivery);
            }
            
            rs.close();
            statement.close();
            
            logger.info("Retrieved " + deliveries.size() + " deliveries from database");
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error retrieving deliveries: " + e.getMessage(), e);
        }
        
        return deliveries;
    }
    
    /**
     * Get a delivery by its ID
     */
    public Delivery_InfDTO getDeliveryById(int id) {
        Delivery_InfDTO delivery = null;
        
        try {
            // Make sure we have a connection
            if (connection == null || connection.isClosed()) {
                connection = DatabaseManager.getConnection();
                if (connection == null) {
                    logger.warning("Could not establish database connection");
                    return null;
                }
            }
            
            String query = "SELECT * FROM deliveries WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            
            logger.info("Executing query: " + query + " with ID: " + id);
            
            ResultSet rs = statement.executeQuery();
            
            if (rs.next()) {
                delivery = mapResultSetToDTO(rs);
                logger.info("Found delivery with ID: " + id);
            } else {
                logger.warning("No delivery found with ID: " + id);
            }
            
            rs.close();
            statement.close();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error retrieving delivery with ID " + id + ": " + e.getMessage(), e);
        }
        
        return delivery;
    }
    
    /**
     * Insert a new delivery
     */
    public boolean insertDelivery(Delivery_InfDTO delivery) {
        try {
            // Make sure we have a connection
            if (connection == null || connection.isClosed()) {
                connection = DatabaseManager.getConnection();
                if (connection == null) {
                    logger.warning("Could not establish database connection");
                    return false;
                }
            }
            
            String query = "INSERT INTO deliveries (exportation_id, delivery_date, tracking_number, status, notes) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            
            // Get export ID without "EXP" prefix if present
            String exportId = delivery.getExportId();
            if (exportId != null && exportId.startsWith("EXP")) {
                exportId = exportId.substring(3);
            }
            
            statement.setString(1, exportId);
            
            // Convert delivery date to string
            if (delivery.getDeliveryDate() != null) {
                statement.setString(2, dateFormat.format(delivery.getDeliveryDate()));
            } else {
                statement.setNull(2, java.sql.Types.DATE);
            }
            
            statement.setString(3, delivery.getTrackingNumber());
            statement.setString(4, delivery.getStatus() != null ? delivery.getStatus() : delivery.getDeliveryStatus());
            statement.setString(5, delivery.getNotes());
            
            int result = statement.executeUpdate();
            statement.close();
            
            logger.info("Insert result: " + result + " rows affected");
            return result > 0;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error inserting delivery: " + e.getMessage(), e);
            return false;
        }
    }
    
    /**
     * Update an existing delivery
     */
    public boolean updateDelivery(Delivery_InfDTO delivery) {
        try {
            // Make sure we have a connection
            if (connection == null || connection.isClosed()) {
                connection = DatabaseManager.getConnection();
                if (connection == null) {
                    logger.warning("Could not establish database connection");
                    return false;
                }
            }
            
            String query = "UPDATE deliveries SET exportation_id = ?, delivery_date = ?, tracking_number = ?, status = ?, notes = ? WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            
            // Get export ID without "EXP" prefix if present
            String exportId = delivery.getExportId();
            if (exportId != null && exportId.startsWith("EXP")) {
                exportId = exportId.substring(3);
            }
            
            statement.setString(1, exportId);
            
            // Convert delivery date to string
            if (delivery.getDeliveryDate() != null) {
                statement.setString(2, dateFormat.format(delivery.getDeliveryDate()));
            } else {
                statement.setNull(2, java.sql.Types.DATE);
            }
            
            statement.setString(3, delivery.getTrackingNumber());
            statement.setString(4, delivery.getStatus() != null ? delivery.getStatus() : delivery.getDeliveryStatus());
            statement.setString(5, delivery.getNotes());
            statement.setInt(6, delivery.getId());
            
            int result = statement.executeUpdate();
            statement.close();
            
            logger.info("Update result: " + result + " rows affected");
            return result > 0;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error updating delivery: " + e.getMessage(), e);
            return false;
        }
    }
    
    /**
     * Delete a delivery by its ID
     */
    public boolean deleteDelivery(int id) {
        try {
            // Make sure we have a connection
            if (connection == null || connection.isClosed()) {
                connection = DatabaseManager.getConnection();
                if (connection == null) {
                    logger.warning("Could not establish database connection");
                    return false;
                }
            }
            
            String query = "DELETE FROM deliveries WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            
            int result = statement.executeUpdate();
            statement.close();
            
            logger.info("Delete result: " + result + " rows affected");
            return result > 0;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error deleting delivery with ID " + id + ": " + e.getMessage(), e);
            return false;
        }
    }
    
    /**
     * Map ResultSet to DTO
     */
    private Delivery_InfDTO mapResultSetToDTO(ResultSet rs) throws SQLException {
        Delivery_InfDTO delivery = new Delivery_InfDTO();
        
        try {
            // Basic fields that should always be present
            int id = rs.getInt("id");
            delivery.setId(id);
            delivery.setDeliveryId("DEL" + String.format("%03d", id));
            
            // Export ID field
            String exportIdStr = rs.getString("exportation_id");
            delivery.setExportId("EXP" + exportIdStr);
            delivery.setExportationId(exportIdStr); // For compatibility
            
            // Tracking number
            delivery.setTrackingNumber(rs.getString("tracking_number"));
            
            // Status
            String status = rs.getString("status");
            delivery.setStatus(status);
            delivery.setDeliveryStatus(status); // For compatibility
            
            // Notes
            delivery.setNotes(rs.getString("notes"));
            
            // Handle date fields
            String deliveryDateStr = rs.getString("delivery_date");
            try {
                if (deliveryDateStr != null && !deliveryDateStr.isEmpty()) {
                    java.util.Date parsedDate = dateFormat.parse(deliveryDateStr);
                    delivery.setDeliveryDate(parsedDate);
                    delivery.setDate(parsedDate); // For compatibility
                }
            } catch (Exception e) {
                logger.log(Level.WARNING, "Error parsing delivery date: " + e.getMessage(), e);
            }
            
            // Handle timestamps
            try {
                Timestamp createdTimestamp = rs.getTimestamp("created_at");
                if (createdTimestamp != null) {
                    delivery.setCreatedAt(new java.util.Date(createdTimestamp.getTime()));
                }
                
                Timestamp updatedTimestamp = rs.getTimestamp("updated_at");
                if (updatedTimestamp != null) {
                    delivery.setUpdatedAt(new java.util.Date(updatedTimestamp.getTime()));
                }
            } catch (SQLException e) {
                // These columns might not exist in all environments
                logger.log(Level.FINE, "Timestamp columns may not exist: " + e.getMessage());
            }
            
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error mapping ResultSet to DTO: " + e.getMessage(), e);
            throw e;
        }
        
        return delivery;
    }
}