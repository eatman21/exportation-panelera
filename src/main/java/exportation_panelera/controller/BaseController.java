package exportation_panelera.controller;

import exportation_panelera.db.DatabaseManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Base controller class with common functionality
 */
public abstract class BaseController {
    protected static final Logger logger = Logger.getLogger(BaseController.class.getName());
    
    /**
     * Try to reconnect to the database
     * @return true if connection is successful, false otherwise
     */
    public boolean tryReconnect() {
        try {
            // Try to establish a connection to the database
            return DatabaseManager.tryConnect();
        } catch (Exception e) {
            logger.log(Level.WARNING, "Failed to reconnect to database", e);
            return false;
        }
    }
    
    /**
     * Get a connection to the database
     * @return database connection or null if in offline mode
     * @throws SQLException if a database access error occurs
     */
    protected Connection getConnection() throws SQLException {
        // Get a connection from the DatabaseManager
        return DatabaseManager.getConnection();
    }
    
    /**
     * Safely close database resources
     * 
     * @param connection The database connection to close
     * @param statement The prepared statement to close
     * @param resultSet The result set to close
     */
    protected void closeResources(Connection connection, PreparedStatement statement, ResultSet resultSet) {
        // Close result set
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                logger.log(Level.WARNING, "Error closing result set", e);
            }
        }
        
        // Close statement
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                logger.log(Level.WARNING, "Error closing prepared statement", e);
            }
        }
        
        // Close connection (only if not managed externally)
        if (connection != null && !DatabaseManager.isConnectionManaged(connection)) {
            try {
                connection.close();
            } catch (SQLException e) {
                logger.log(Level.WARNING, "Error closing database connection", e);
            }
        }
    }
    
    /**
     * Execute operations within a transaction
     * 
     * @param operations The database operations to execute
     * @return true if all operations succeed, false if any fail
     */
    public boolean executeInTransaction(DatabaseOperation... operations) {
        // If offline mode is enabled, log and return success without saving
        if (DatabaseManager.isOfflineMode()) {
            logger.info("In offline mode - skipping transaction execution");
            return true;
        }
        
        Connection conn = null;
        boolean autoCommit = true;
        
        try {
            // Get a connection
            conn = getConnection();
            if (conn == null) {
                logger.warning("Database connection is null, cannot execute transaction");
                return false;
            }
            
            // Save the current auto-commit state
            autoCommit = conn.getAutoCommit();
            
            // Disable auto-commit for transaction
            conn.setAutoCommit(false);
            
            // Execute all operations
            for (DatabaseOperation operation : operations) {
                boolean operationResult = operation.execute(conn);
                if (!operationResult) {
                    // If any operation fails, roll back the transaction
                    conn.rollback();
                    logger.warning("Transaction rolled back due to operation failure");
                    return false;
                }
            }
            
            // If all operations succeed, commit the transaction
            conn.commit();
            logger.info("Transaction committed successfully");
            return true;
            
        } catch (SQLException e) {
            // Roll back the transaction on any SQL exception
            try {
                if (conn != null) {
                    conn.rollback();
                }
            } catch (SQLException rollbackEx) {
                logger.log(Level.SEVERE, "Error rolling back transaction", rollbackEx);
            }
            
            logger.log(Level.SEVERE, "Transaction failed", e);
            return false;
            
        } finally {
            // Restore the original auto-commit state
            try {
                if (conn != null) {
                    conn.setAutoCommit(autoCommit);
                }
            } catch (SQLException resetEx) {
                logger.log(Level.WARNING, "Error resetting auto-commit", resetEx);
            }
            
            // Don't close the connection here to avoid interfering with connection pools
        }
    }
    
    /**
     * Interface for database operations that can be executed within a transaction
     */
    @FunctionalInterface
    public interface DatabaseOperation {
        /**
         * Execute a database operation
         * 
         * @param conn The database connection
         * @return true if the operation succeeds, false otherwise
         * @throws SQLException if a database access error occurs
         */
        boolean execute(Connection conn) throws SQLException;
    }
}