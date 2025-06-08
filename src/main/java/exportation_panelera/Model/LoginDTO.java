package exportation_panelera.Model;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Data Transfer Object for user authentication information.
 * Represents login credentials with validation and security considerations.
 * 
 * @author YourName
 * @version 1.0
 */
public class LoginDTO {
    
    private String username;
    private String password;
    private LocalDateTime lastLoginTime;
    private boolean isActive;
    
    // Constants for validation
    private static final int MIN_USERNAME_LENGTH = 3;
    private static final int MAX_USERNAME_LENGTH = 50;
    private static final int MIN_PASSWORD_LENGTH = 6;
    
    /**
     * Default constructor
     */
    public LoginDTO() {
        this.isActive = true; // Default to active
    }
    
    /**
     * Constructor with username and password
     * 
     * @param username The user's username
     * @param password The user's password
     */
    public LoginDTO(String username, String password) {
        this();
        setUsername(username);
        setPassword(password);
    }
    
    /**
     * Get the username
     * 
     * @return The username
     */
    public String getUsername() {
        return username;
    }
    
    /**
     * Set the username with validation
     * 
     * @param username The username to set
     * @throws IllegalArgumentException if username is invalid
     */
    public void setUsername(String username) {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be null or empty");
        }
        
        String trimmedUsername = username.trim();
        
        if (trimmedUsername.length() < MIN_USERNAME_LENGTH) {
            throw new IllegalArgumentException(
                String.format("Username must be at least %d characters long", MIN_USERNAME_LENGTH));
        }
        
        if (trimmedUsername.length() > MAX_USERNAME_LENGTH) {
            throw new IllegalArgumentException(
                String.format("Username cannot exceed %d characters", MAX_USERNAME_LENGTH));
        }
        
        // Check for valid characters (alphanumeric and underscore only)
        if (!trimmedUsername.matches("^[a-zA-Z0-9_]+$")) {
            throw new IllegalArgumentException(
                "Username can only contain letters, numbers, and underscores");
        }
        
        this.username = trimmedUsername;
    }
    
    /**
     * Get the password
     * 
     * @return The password
     */
    public String getPassword() {
        return password;
    }
    
    /**
     * Set the password with validation
     * 
     * @param password The password to set
     * @throws IllegalArgumentException if password is invalid
     */
    public void setPassword(String password) {
        if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }
        
        if (password.length() < MIN_PASSWORD_LENGTH) {
            throw new IllegalArgumentException(
                String.format("Password must be at least %d characters long", MIN_PASSWORD_LENGTH));
        }
        
        this.password = password;
    }
    
    /**
     * Get the last login time
     * 
     * @return The last login timestamp
     */
    public LocalDateTime getLastLoginTime() {
        return lastLoginTime;
    }
    
    /**
     * Set the last login time
     * 
     * @param lastLoginTime The timestamp to set
     */
    public void setLastLoginTime(LocalDateTime lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }
    
    /**
     * Check if the user account is active
     * 
     * @return true if active, false otherwise
     */
    public boolean isActive() {
        return isActive;
    }
    
    /**
     * Set the account active status
     * 
     * @param active The active status to set
     */
    public void setActive(boolean active) {
        this.isActive = active;
    }
    
    /**
     * Validate the complete login DTO
     * 
     * @throws IllegalStateException if the DTO is in an invalid state
     */
    public void validate() {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalStateException("Username is required");
        }
        
        if (password == null || password.trim().isEmpty()) {
            throw new IllegalStateException("Password is required");
        }
    }
    
    /**
     * Check if this represents a valid login attempt
     * 
     * @return true if both username and password are set
     */
    public boolean isValidForLogin() {
        return username != null && !username.trim().isEmpty() &&
               password != null && !password.trim().isEmpty();
    }
    
    /**
     * Clear sensitive data (password) from memory
     */
    public void clearSensitiveData() {
        if (password != null) {
            // In a real application, you might want to overwrite the string in memory
            password = null;
        }
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        
        LoginDTO loginDTO = (LoginDTO) obj;
        return Objects.equals(username, loginDTO.username);
        // Note: We don't compare passwords in equals() for security reasons
    }
    
    @Override
    public int hashCode() {
        // Only use username for hash code, not password
        return Objects.hash(username);
    }
    
    @Override
    public String toString() {
        return String.format("LoginDTO{username='%s', isActive=%s, lastLoginTime=%s}", 
                           username, isActive, lastLoginTime);
        // Note: Password is not included in toString() for security reasons
    }
}