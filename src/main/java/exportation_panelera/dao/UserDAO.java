package exportation_panelera.dao;

import exportation_panelera.Model.LoginDTO;
import exportation_panelera.db.DatabaseManager;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Data Access Object for user authentication and management.
 * Handles all database operations related to user accounts.
 * 
 * @author YourName
 * @version 1.0
 */
public class UserDAO {
    
    private static final Logger logger = Logger.getLogger(UserDAO.class.getName());
    
    // SQL Queries
    private static final String AUTHENTICATE_USER_SQL = 
        "SELECT id, username, password_hash, is_active, last_login, created_at " +
        "FROM users WHERE username = ? AND is_active = 1";
    
    private static final String UPDATE_LAST_LOGIN_SQL = 
        "UPDATE users SET last_login = ? WHERE username = ?";
    
    private static final String CREATE_USER_SQL = 
        "INSERT INTO users (username, password_hash, is_active, created_at) VALUES (?, ?, 1, ?)";
    
    private static final String UPDATE_PASSWORD_SQL = 
        "UPDATE users SET password_hash = ?, updated_at = ? WHERE username = ?";
    
    private static final String DEACTIVATE_USER_SQL = 
        "UPDATE users SET is_active = 0, updated_at = ? WHERE username = ?";
    
    /**
     * Authenticate a user with username and password
     * 
     * @param username The username
     * @param password The plain text password
     * @return true if authentication is successful, false otherwise
     */
    public boolean authenticateUser(String username, String password) {
        // Input validation
        if (username == null || username.trim().isEmpty()) {
            logger.warning("Authentication attempted with empty username");
            return false;
        }
        
        if (password == null || password.trim().isEmpty()) {
            logger.warning("Authentication attempted with empty password");
            return false;
        }
        
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = DatabaseManager.getConnection();
            
            // If no connection available, check for default admin account
            if (conn == null) {
                logger.info("Database unavailable, checking default credentials");
                return authenticateOffline(username, password);
            }
            
            stmt = conn.prepareStatement(AUTHENTICATE_USER_SQL);
            stmt.setString(1, username.trim());
            
            rs = stmt.executeQuery();
            
            if (rs.next()) {
                String storedHash = rs.getString("password_hash");
                boolean isActive = rs.getBoolean("is_active");
                
                if (!isActive) {
                    logger.warning("Authentication failed for inactive user: " + username);
                    return false;
                }
                
                // Verify password (in real app, use proper password hashing)
                boolean passwordValid = verifyPassword(password, storedHash);
                
                if (passwordValid) {
                    // Update last login time
                    updateLastLogin(username);
                    logger.info("User authenticated successfully: " + username);
                    return true;
                } else {
                    logger.warning("Authentication failed for user: " + username + " (invalid password)");
                    return false;
                }
            } else {
                logger.warning("Authentication failed for user: " + username + " (user not found)");
                return false;
            }
            
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Database error during authentication for user: " + username, e);
            // Fallback to offline authentication
            return authenticateOffline(username, password);
            
        } finally {
            closeResources(conn, stmt, rs);
        }
    }
    
    /**
     * Offline authentication for when database is unavailable
     */
    private boolean authenticateOffline(String username, String password) {
        // Default admin credentials for offline mode
        boolean isDefaultAdmin = "admin".equals(username) && "admin123".equals(password);
        
        if (isDefaultAdmin) {
            logger.info("Offline authentication successful for default admin");
            return true;
        }
        
        logger.warning("Offline authentication failed for user: " + username);
        return false;
    }
    
    /**
     * Create a new user account
     * 
     * @param loginDTO The user data
     * @return true if created successfully, false otherwise
     */
    public boolean createUser(LoginDTO loginDTO) {
        if (loginDTO == null) {
            logger.warning("Cannot create user: LoginDTO is null");
            return false;
        }
        
        try {
            loginDTO.validate();
        } catch (IllegalStateException e) {
            logger.warning("Cannot create user: " + e.getMessage());
            return false;
        }
        
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = DatabaseManager.getConnection();
            
            if (conn == null) {
                logger.warning("Cannot create user: Database unavailable");
                return false;
            }
            
            // Check if username already exists
            if (userExists(loginDTO.getUsername())) {
                logger.warning("Cannot create user: Username already exists: " + loginDTO.getUsername());
                return false;
            }
            
            stmt = conn.prepareStatement(CREATE_USER_SQL);
            stmt.setString(1, loginDTO.getUsername());
            stmt.setString(2, hashPassword(loginDTO.getPassword()));
            stmt.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
            
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows > 0) {
                logger.info("User created successfully: " + loginDTO.getUsername());
                return true;
            } else {
                logger.warning("Failed to create user: No rows affected");
                return false;
            }
            
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Database error creating user: " + loginDTO.getUsername(), e);
            return false;
            
        } finally {
            closeResources(conn, stmt, null);
        }
    }
    
    /**
     * Update a user's password
     * 
     * @param username The username
     * @param newPassword The new password
     * @return true if updated successfully, false otherwise
     */
    public boolean updatePassword(String username, String newPassword) {
        if (username == null || username.trim().isEmpty()) {
            logger.warning("Cannot update password: Username is empty");
            return false;
        }
        
        if (newPassword == null || newPassword.length() < 6) {
            logger.warning("Cannot update password: Invalid password");
            return false;
        }
        
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = DatabaseManager.getConnection();
            
            if (conn == null) {
                logger.warning("Cannot update password: Database unavailable");
                return false;
            }
            
            stmt = conn.prepareStatement(UPDATE_PASSWORD_SQL);
            stmt.setString(1, hashPassword(newPassword));
            stmt.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
            stmt.setString(3, username.trim());
            
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows > 0) {
                logger.info("Password updated successfully for user: " + username);
                return true;
            } else {
                logger.warning("Failed to update password: User not found: " + username);
                return false;
            }
            
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Database error updating password for user: " + username, e);
            return false;
            
        } finally {
            closeResources(conn, stmt, null);
        }
    }
    
    /**
     * Deactivate a user account
     * 
     * @param username The username to deactivate
     * @return true if deactivated successfully, false otherwise
     */
    public boolean deactivateUser(String username) {
        if (username == null || username.trim().isEmpty()) {
            logger.warning("Cannot deactivate user: Username is empty");
            return false;
        }
        
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = DatabaseManager.getConnection();
            
            if (conn == null) {
                logger.warning("Cannot deactivate user: Database unavailable");
                return false;
            }
            
            stmt = conn.prepareStatement(DEACTIVATE_USER_SQL);
            stmt.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
            stmt.setString(2, username.trim());
            
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows > 0) {
                logger.info("User deactivated successfully: " + username);
                return true;
            } else {
                logger.warning("Failed to deactivate user: User not found: " + username);
                return false;
            }
            
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Database error deactivating user: " + username, e);
            return false;
            
        } finally {
            closeResources(conn, stmt, null);
        }
    }
    
    /**
     * Check if a username already exists
     */
    private boolean userExists(String username) {
        String sql = "SELECT COUNT(*) FROM users WHERE username = ?";
        
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = DatabaseManager.getConnection();
            if (conn == null) return false;
            
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            
            rs = stmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
            
        } catch (SQLException e) {
            logger.log(Level.WARNING, "Error checking if user exists: " + username, e);
        } finally {
            closeResources(conn, stmt, rs);
        }
        
        return false;
    }
    
    /**
     * Update the last login time for a user
     */
    private void updateLastLogin(String username) {
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = DatabaseManager.getConnection();
            if (conn == null) return;
            
            stmt = conn.prepareStatement(UPDATE_LAST_LOGIN_SQL);
            stmt.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
            stmt.setString(2, username);
            
            stmt.executeUpdate();
            
        } catch (SQLException e) {
            logger.log(Level.WARNING, "Error updating last login for user: " + username, e);
        } finally {
            closeResources(conn, stmt, null);
        }
    }
    
    /**
     * Hash a password (simplified - use BCrypt or similar in production)
     */
    private String hashPassword(String password) {
        // In a real application, use BCrypt, Argon2, or similar
        // This is just a simple example
        return password + "_hashed"; // Replace with proper hashing
    }
    
    /**
     * Verify a password against its hash
     */
    private boolean verifyPassword(String password, String hash) {
        // In a real application, use proper password verification
        // This is just a simple example
        return (password + "_hashed").equals(hash);
    }
    
    /**
     * Safely close database resources
     */
    private void closeResources(Connection conn, PreparedStatement stmt, ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                logger.log(Level.WARNING, "Error closing ResultSet", e);
            }
        }
        
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                logger.log(Level.WARNING, "Error closing PreparedStatement", e);
            }
        }
        
        // Note: Don't close connection if it's managed by DatabaseManager
        if (conn != null && !DatabaseManager.isConnectionManaged(conn)) {
            try {
                conn.close();
            } catch (SQLException e) {
                logger.log(Level.WARNING, "Error closing Connection", e);
            }
        }
    }
}