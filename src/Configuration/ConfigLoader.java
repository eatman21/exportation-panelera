package Configuration;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Configuration loader for reading application settings from config.properties
 *
 * @author Cris
 */
public class ConfigLoader {

    private static final Logger LOGGER = Logger.getLogger(ConfigLoader.class.getName());
    private static Properties properties = null;
    private static final String CONFIG_FILE = "config.properties";

    /**
     * Load configuration properties from file
     * @return Properties object with configuration
     */
    private static Properties loadProperties() {
        if (properties == null) {
            properties = new Properties();

            // Try loading from file system first
            try (InputStream input = new FileInputStream(CONFIG_FILE)) {
                properties.load(input);
                LOGGER.info("Configuration loaded from " + CONFIG_FILE);
            } catch (IOException ex) {
                LOGGER.log(Level.WARNING, "Could not load config.properties from file system, trying classpath", ex);

                // Try loading from classpath as fallback
                try (InputStream input = ConfigLoader.class.getClassLoader().getResourceAsStream(CONFIG_FILE)) {
                    if (input != null) {
                        properties.load(input);
                        LOGGER.info("Configuration loaded from classpath");
                    } else {
                        LOGGER.log(Level.SEVERE, "config.properties not found in classpath");
                        // Set default values
                        setDefaultProperties();
                    }
                } catch (IOException e) {
                    LOGGER.log(Level.SEVERE, "Failed to load configuration", e);
                    setDefaultProperties();
                }
            }
        }
        return properties;
    }

    /**
     * Set default properties if config file is not found
     */
    private static void setDefaultProperties() {
        properties.setProperty("db.host", "localhost");
        properties.setProperty("db.port", "3309");
        properties.setProperty("db.name", "panelera_exportation");
        properties.setProperty("db.user", "root");
        properties.setProperty("db.password", "");
        properties.setProperty("db.driver", "com.mysql.cj.jdbc.Driver");
        properties.setProperty("encryption.secret.key", "Valentina2425");
        LOGGER.warning("Using default configuration values");
    }

    /**
     * Get a configuration property value
     * @param key The property key
     * @return The property value or null if not found
     */
    public static String getProperty(String key) {
        return loadProperties().getProperty(key);
    }

    /**
     * Get a configuration property with a default value
     * @param key The property key
     * @param defaultValue Default value if key not found
     * @return The property value or default value
     */
    public static String getProperty(String key, String defaultValue) {
        return loadProperties().getProperty(key, defaultValue);
    }

    /**
     * Get database URL
     * @return JDBC connection URL
     */
    public static String getDatabaseURL() {
        String host = getProperty("db.host", "localhost");
        String port = getProperty("db.port", "3309");
        String dbName = getProperty("db.name", "Panelera_exportation");
        return "jdbc:mysql://" + host + ":" + port + "/" + dbName
                + "?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true&autoReconnect=true&useUnicode=true&characterEncoding=UTF-8";
    }

    /**
     * Get database username
     * @return Database username
     */
    public static String getDatabaseUser() {
        return getProperty("db.user", "root");
    }

    /**
     * Get database password
     * @return Database password
     */
    public static String getDatabasePassword() {
        return getProperty("db.password", "");
    }

    /**
     * Get JDBC driver class name
     * @return JDBC driver class
     */
    public static String getDatabaseDriver() {
        return getProperty("db.driver", "com.mysql.cj.jdbc.Driver");
    }

    /**
     * Get encryption secret key
     * @return Encryption secret key
     */
    public static String getEncryptionKey() {
        return getProperty("encryption.secret.key", "Valentina2425");
    }

    /**
     * Reload configuration from file
     */
    public static void reload() {
        properties = null;
        loadProperties();
    }
}
