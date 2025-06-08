package exportation_panelera.db;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Database configuration manager that loads settings from properties file
 * with fallback to default values
 */
public class DatabaseConfig {
    private static final Logger logger = Logger.getLogger(DatabaseConfig.class.getName());
    private static final String CONFIG_FILE = "database.properties";
    
    // Default values
    private static final String DEFAULT_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DEFAULT_URL = "jdbc:mysql://localhost:3308/exportation_panelera";
    private static final String DEFAULT_USERNAME = "root";
    private static final String DEFAULT_PASSWORD = "";
    private static final int DEFAULT_POOL_SIZE = 5;
    private static final int DEFAULT_CONNECTION_TIMEOUT = 5000;
    private static final int DEFAULT_SOCKET_TIMEOUT = 10000;
    
    private Properties properties;
    
    public DatabaseConfig() {
        properties = new Properties();
        loadConfiguration();
    }
    
    /**
     * Load configuration from properties file or use defaults
     */
    private void loadConfiguration() {
        try (InputStream input = getClass().getClassLoader().getResourceAsStream(CONFIG_FILE)) {
            if (input == null) {
                logger.info("Database properties file not found, using default configuration");
                setDefaults();
            } else {
                properties.load(input);
                logger.info("Database configuration loaded from properties file");
                
                // Validate required properties and set defaults if missing
                validateAndSetDefaults();
            }
        } catch (IOException ex) {
            logger.log(Level.WARNING, "Error reading database configuration: " + ex.getMessage());
            logger.info("Using default database configuration");
            setDefaults();
        }
    }
    
    /**
     * Set default configuration values
     */
    private void setDefaults() {
        properties.setProperty("db.driver", DEFAULT_DRIVER);
        properties.setProperty("db.url", DEFAULT_URL);
        properties.setProperty("db.username", DEFAULT_USERNAME);
        properties.setProperty("db.password", DEFAULT_PASSWORD);
        properties.setProperty("db.poolSize", String.valueOf(DEFAULT_POOL_SIZE));
        properties.setProperty("db.connectionTimeout", String.valueOf(DEFAULT_CONNECTION_TIMEOUT));
        properties.setProperty("db.socketTimeout", String.valueOf(DEFAULT_SOCKET_TIMEOUT));
    }
    
    /**
     * Validate loaded properties and set defaults for missing values
     */
    private void validateAndSetDefaults() {
        if (!properties.containsKey("db.driver")) {
            properties.setProperty("db.driver", DEFAULT_DRIVER);
        }
        if (!properties.containsKey("db.url")) {
            properties.setProperty("db.url", DEFAULT_URL);
        }
        if (!properties.containsKey("db.username")) {
            properties.setProperty("db.username", DEFAULT_USERNAME);
        }
        if (!properties.containsKey("db.password")) {
            properties.setProperty("db.password", DEFAULT_PASSWORD);
        }
        if (!properties.containsKey("db.poolSize")) {
            properties.setProperty("db.poolSize", String.valueOf(DEFAULT_POOL_SIZE));
        }
        if (!properties.containsKey("db.connectionTimeout")) {
            properties.setProperty("db.connectionTimeout", String.valueOf(DEFAULT_CONNECTION_TIMEOUT));
        }
        if (!properties.containsKey("db.socketTimeout")) {
            properties.setProperty("db.socketTimeout", String.valueOf(DEFAULT_SOCKET_TIMEOUT));
        }
    }
    
    // Getters with fallback to defaults
    public String getDriver() {
        return properties.getProperty("db.driver", DEFAULT_DRIVER);
    }
    
    public String getUrl() {
        return properties.getProperty("db.url", DEFAULT_URL);
    }
    
    public String getUsername() {
        return properties.getProperty("db.username", DEFAULT_USERNAME);
    }
    
    public String getPassword() {
        return properties.getProperty("db.password", DEFAULT_PASSWORD);
    }
    
    public int getPoolSize() {
        try {
            return Integer.parseInt(properties.getProperty("db.poolSize", String.valueOf(DEFAULT_POOL_SIZE)));
        } catch (NumberFormatException e) {
            logger.warning("Invalid pool size in configuration, using default");
            return DEFAULT_POOL_SIZE;
        }
    }
    
    public int getConnectionTimeout() {
        try {
            return Integer.parseInt(properties.getProperty("db.connectionTimeout", String.valueOf(DEFAULT_CONNECTION_TIMEOUT)));
        } catch (NumberFormatException e) {
            logger.warning("Invalid connection timeout in configuration, using default");
            return DEFAULT_CONNECTION_TIMEOUT;
        }
    }
    
    public int getSocketTimeout() {
        try {
            return Integer.parseInt(properties.getProperty("db.socketTimeout", String.valueOf(DEFAULT_SOCKET_TIMEOUT)));
        } catch (NumberFormatException e) {
            logger.warning("Invalid socket timeout in configuration, using default");
            return DEFAULT_SOCKET_TIMEOUT;
        }
    }
    
    /**
     * Get the full connection URL with timeout parameters
     * @return Complete JDBC URL with timeouts
     */
    public String getFullUrl() {
        return String.format("%s?connectTimeout=%d&socketTimeout=%d&useSSL=false&allowPublicKeyRetrieval=true",
                getUrl(), getConnectionTimeout(), getSocketTimeout());
    }
    
    /**
     * Print configuration summary (without password)
     * @return Configuration summary string
     */
    public String getConfigurationSummary() {
        return String.format(
            "Database Configuration:\n" +
            "  Driver: %s\n" +
            "  URL: %s\n" +
            "  Username: %s\n" +
            "  Password: %s\n" +
            "  Pool Size: %d\n" +
            "  Connection Timeout: %d ms\n" +
            "  Socket Timeout: %d ms",
            getDriver(),
            getUrl(),
            getUsername(),
            "*".repeat(getPassword().length()), // Mask password
            getPoolSize(),
            getConnectionTimeout(),
            getSocketTimeout()
        );
    }
    
    /**
     * Check if the configuration appears to be valid
     * @return true if configuration looks valid
     */
    public boolean isValid() {
        return getDriver() != null && !getDriver().trim().isEmpty() &&
               getUrl() != null && !getUrl().trim().isEmpty() &&
               getUsername() != null &&
               getPoolSize() > 0 &&
               getConnectionTimeout() > 0 &&
               getSocketTimeout() > 0;
    }
}