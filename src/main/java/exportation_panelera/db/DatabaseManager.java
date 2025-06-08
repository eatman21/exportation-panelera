package exportation_panelera.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Manages database connections for the application with improved error handling,
 * connection pooling simulation, and offline mode support.
 * Compatible with Java 8+
 * 
 * @author YourName
 * @version 1.0
 */
public class DatabaseManager {
    
    private static final Logger logger = Logger.getLogger(DatabaseManager.class.getName());
    
    // Database configuration constants
    private static final String DB_URL = "jdbc:mysql://localhost:3308/exportation_panelera";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    
    // Connection management
    private static volatile Connection connection = null;
    private static final AtomicBoolean offlineMode = new AtomicBoolean(false);
    private static final AtomicBoolean initialized = new AtomicBoolean(false);
    
    // Connection health tracking
    private static LocalDateTime lastConnectionTest = null;
    private static final int CONNECTION_TEST_INTERVAL_SECONDS = 30;
    
    // Prevent instantiation of utility class
    private DatabaseManager() {
        throw new UnsupportedOperationException("Utility class - cannot be instantiated");
    }
    
    /**
     * Initializes the database connection with comprehensive error handling
     * 
     * @return true if connection successful, false if in offline mode
     */
    public static synchronized boolean initialize() {
        if (initialized.get() && isConnectionHealthy()) {
            logger.info("Database already initialized and healthy");
            return true;
        }
        
        try {
            // Load the MySQL JDBC driver
            Class.forName(JDBC_DRIVER);
            logger.info("JDBC driver loaded successfully");
            
            // Establish the connection with timeout settings
            connection = DriverManager.getConnection(
                DB_URL + "?connectTimeout=5000&socketTimeout=10000", 
                DB_USER, 
                DB_PASSWORD
            );
            
            // Test the connection
            if (connection != null && connection.isValid(5)) {
                offlineMode.set(false);
                initialized.set(true);
                lastConnectionTest = LocalDateTime.now();
                
                logger.info("Database connection initialized successfully");
                logger.info("Connected to: " + connection.getMetaData().getDatabaseProductName());
                return true;
            } else {
                throw new SQLException("Connection validation failed");
            }
            
        } catch (ClassNotFoundException e) {
            logger.log(Level.SEVERE, "MySQL JDBC driver not found - ensure mysql-connector-java is in classpath", e);
            setOfflineModeWithReason("JDBC driver not found");
            return false;
            
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Failed to connect to database: " + e.getMessage(), e);
            setOfflineModeWithReason("Database connection failed: " + e.getMessage());
            return false;
            
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Unexpected error during database initialization", e);
            setOfflineModeWithReason("Unexpected error: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Gets a connection to the database with automatic retry logic
     * 
     * @return database connection or null if in offline mode
     * @throws SQLException if a database access error occurs
     */
    public static Connection getConnection() throws SQLException {
        // If in offline mode, return null immediately
        if (offlineMode.get()) {
            logger.finest("In offline mode - returning null connection");
            return null;
        }
        
        // If not initialized, try to initialize
        if (!initialized.get()) {
            logger.info("Database not initialized, attempting to initialize");
            if (!initialize()) {
                throw new SQLException("Cannot establish database connection - system is in offline mode");
            }
        }
        
        // Check if we need to test the connection health
        if (shouldTestConnection()) {
            if (!isConnectionHealthy()) {
                logger.warning("Connection health check failed, attempting to reconnect");
                if (!initialize()) {
                    throw new SQLException("Database connection lost and reconnection failed");
                }
            }
        }
        
        if (connection == null) {
            throw new SQLException("Database connection is null");
        }
        
        return connection;
    }
    
    /**
     * Check if the connection needs to be tested based on time interval
     * 
     * @return true if connection should be tested
     */
    private static boolean shouldTestConnection() {
        return lastConnectionTest == null || 
               lastConnectionTest.plusSeconds(CONNECTION_TEST_INTERVAL_SECONDS).isBefore(LocalDateTime.now());
    }
    
    /**
     * Check if the current connection is healthy
     * 
     * @return true if connection is valid and healthy
     */
    private static boolean isConnectionHealthy() {
        try {
            boolean healthy = connection != null && 
                            !connection.isClosed() && 
                            connection.isValid(2);
            
            if (healthy) {
                lastConnectionTest = LocalDateTime.now();
            }
            
            return healthy;
            
        } catch (SQLException e) {
            logger.log(Level.WARNING, "Error checking connection health", e);
            return false;
        }
    }
    
    /**
     * Attempts to establish a new database connection
     * 
     * @return true if connection is successful, false otherwise
     */
    public static boolean tryConnect() {
        logger.info("Attempting to establish database connection");
        
        // Reset state for clean retry
        closeCurrentConnection();
        initialized.set(false);
        
        // Try to initialize
        boolean success = initialize();
        
        if (success) {
            logger.info("Database connection established successfully");
        } else {
            logger.warning("Failed to establish database connection");
        }
        
        return success;
    }
    
    /**
     * Close the current connection safely
     */
    private static void closeCurrentConnection() {
        if (connection != null) {
            try {
                if (!connection.isClosed()) {
                    connection.close();
                    logger.fine("Previous connection closed");
                }
            } catch (SQLException e) {
                logger.log(Level.WARNING, "Error closing previous connection", e);
            }
            connection = null;
        }
    }
    
    /**
     * Tests the database connection and returns detailed status
     * 
     * @return true if connection works, false otherwise
     */
    public static boolean testConnection() {
        try {
            if (offlineMode.get()) {
                logger.info("Currently in offline mode, attempting to reconnect");
                return tryConnect();
            }
            
            if (!initialized.get()) {
                logger.info("Database not initialized, attempting initialization");
                return initialize();
            }
            
            boolean healthy = isConnectionHealthy();
            
            if (!healthy) {
                logger.warning("Connection health check failed, attempting reconnection");
                return tryConnect();
            }
            
            logger.fine("Database connection test passed");
            return true;
            
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Connection test failed with exception", e);
            setOfflineModeWithReason("Connection test failed: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Checks if there is a valid connection to the database
     * 
     * @return true if connected, false otherwise
     */
    public static boolean isConnected() {
        return !offlineMode.get() && initialized.get() && isConnectionHealthy();
    }
    
    /**
     * Checks if the system is in offline mode
     * 
     * @return true if in offline mode, false if connected to database
     */
    public static boolean isOfflineMode() {
        return offlineMode.get();
    }
    
    /**
     * Set offline mode with a specific reason
     * 
     * @param reason The reason for entering offline mode
     */
    private static void setOfflineModeWithReason(String reason) {
        offlineMode.set(true);
        initialized.set(false);
        logger.warning("Entering offline mode: " + reason);
    }
    
    /**
     * Forces the system into or out of offline mode
     * 
     * @param mode true to enable offline mode, false to attempt reconnection
     */
    public static void setOfflineMode(boolean mode) {
        if (mode) {
            setOfflineModeWithReason("Manually set to offline mode");
        } else {
            logger.info("Attempting to exit offline mode");
            offlineMode.set(false);
            tryConnect();
        }
    }
    
    /**
     * Determines if a connection is managed by a connection pool
     * 
     * @param conn The connection to check
     * @return true if the connection is managed, false otherwise
     */
    public static boolean isConnectionManaged(Connection conn) {
        if (conn == null) {
            return false;
        }
        
        try {
            // Check if this is our singleton connection
            return conn == connection;
        } catch (Exception e) {
            logger.log(Level.FINE, "Error checking if connection is managed", e);
            return false;
        }
    }
    
    /**
     * Get database connection status information
     * 
     * @return Status information as a string
     */
    public static String getConnectionStatus() {
        if (offlineMode.get()) {
            return "OFFLINE - Database unavailable";
        }
        
        if (!initialized.get()) {
            return "NOT_INITIALIZED - Database not connected";
        }
        
        if (isConnectionHealthy()) {
            return "ONLINE - Database connected and healthy";
        } else {
            return "UNHEALTHY - Connection exists but may be stale";
        }
    }
    
    /**
     * Shutdown the database connection safely
     */
    public static synchronized void shutdown() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                logger.info("Database connection has been shut down gracefully");
            }
        } catch (SQLException e) {
            logger.log(Level.WARNING, "Error during database shutdown", e);
        } finally {
            connection = null;
            initialized.set(false);
            offlineMode.set(true);
            lastConnectionTest = null;
        }
    }
    
    /**
     * Get configuration information (without sensitive data)
     * 
     * @return Configuration summary
     */
    public static String getConfigurationSummary() {
        return String.format(
            "Database Configuration:\n" +
            "  URL: %s\n" +
            "  User: %s\n" +
            "  Driver: %s\n" +
            "  Status: %s\n" +
            "  Last Test: %s",
            DB_URL,
            DB_USER,
            JDBC_DRIVER,
            getConnectionStatus(),
            lastConnectionTest != null ? lastConnectionTest.toString() : "Never"
        );
    }
    
    /**
     * Close a database connection (for backward compatibility)
     */
    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                logger.info("Database connection closed");
            }
        } catch (SQLException e) {
            logger.log(Level.WARNING, "Error closing database connection", e);
        }
    }
    
    /**
     * Create database tables if they don't exist
     * 
     * @return true if tables were created or already exist
     */
    public static boolean createTablesIfNotExist() {
        if (offlineMode.get()) {
            logger.info("In offline mode - skipping table creation");
            return true;
        }
        
        try {
            Connection conn = getConnection();
            if (conn == null) {
                logger.warning("Cannot create tables - no database connection");
                return false;
            }
            
            // Create deliveries table using traditional string concatenation
            String createDeliveriesTable = "CREATE TABLE IF NOT EXISTS deliveries (" +
                "id INT AUTO_INCREMENT PRIMARY KEY," +
                "delivery_id VARCHAR(50) UNIQUE," +
                "exportation_id VARCHAR(50)," +
                "carrier_name VARCHAR(100)," +
                "tracking_number VARCHAR(100)," +
                "delivery_address TEXT," +
                "contact_person VARCHAR(100)," +
                "contact_phone VARCHAR(20)," +
                "delivery_date DATE," +
                "status VARCHAR(50)," +
                "notes TEXT," +
                "shipping_method VARCHAR(50)," +
                "shipping_cost DECIMAL(10,2)," +
                "shipping_currency VARCHAR(3)," +
                "reference_number VARCHAR(100)," +
                "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                "updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP" +
                ")";
            
            // Create users table
            String createUsersTable = "CREATE TABLE IF NOT EXISTS users (" +
                "id INT AUTO_INCREMENT PRIMARY KEY," +
                "username VARCHAR(50) UNIQUE NOT NULL," +
                "password_hash VARCHAR(255) NOT NULL," +
                "is_active BOOLEAN DEFAULT TRUE," +
                "last_login TIMESTAMP NULL," +
                "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                "updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP" +
                ")";
            
            // Create exportations table
            String createExportationsTable = "CREATE TABLE IF NOT EXISTS exportations (" +
                "id INT AUTO_INCREMENT PRIMARY KEY," +
                "exportation_id VARCHAR(50) UNIQUE," +
                "product_type VARCHAR(100)," +
                "amount DECIMAL(10,2)," +
                "destination VARCHAR(100)," +
                "exportation_date DATE," +
                "unit_price DECIMAL(10,2)," +
                "currency VARCHAR(3)," +
                "has_delivery BOOLEAN DEFAULT FALSE," +
                "status VARCHAR(50)," +
                "notes TEXT," +
                "customer_name VARCHAR(100)," +
                "customer_email VARCHAR(100)," +
                "customer_phone VARCHAR(20)," +
                "document_number VARCHAR(50)," +
                "export_license VARCHAR(50)," +
                "employee_id VARCHAR(50)," +
                "transport_method VARCHAR(50)," +
                "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                "updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP" +
                ")";
            
            // Execute table creation
            conn.createStatement().execute(createDeliveriesTable);
            conn.createStatement().execute(createUsersTable);
            conn.createStatement().execute(createExportationsTable);
            
            logger.info("Database tables created or verified successfully");
            
            // Create default admin user if users table is empty
            createDefaultAdminUser(conn);
            
            return true;
            
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error creating database tables", e);
            return false;
        }
    }
    
    /**
     * Create a default admin user if the users table is empty
     */
    private static void createDefaultAdminUser(Connection conn) {
        try {
            // Check if users table is empty
            var rs = conn.createStatement().executeQuery("SELECT COUNT(*) FROM users");
            if (rs.next() && rs.getInt(1) == 0) {
                // Insert default admin user
                String insertAdmin = "INSERT INTO users (username, password_hash, is_active) VALUES (?, ?, ?)";
                var stmt = conn.prepareStatement(insertAdmin);
                stmt.setString(1, "admin");
                stmt.setString(2, "admin123_hashed"); // In production, use proper password hashing
                stmt.setBoolean(3, true);
                stmt.executeUpdate();
                
                logger.info("Default admin user created (username: admin, password: admin123)");
                stmt.close();
            }
            rs.close();
        } catch (SQLException e) {
            logger.log(Level.WARNING, "Error creating default admin user", e);
        }
    }
}