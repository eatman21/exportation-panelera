package exportation_panelera.db;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.Statement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DatabaseTest {
    private static final Logger logger = LoggerFactory.getLogger(DatabaseTest.class);

    public static void main(String[] args) {
        testDatabaseConnection();
    }
    
    public static void testDatabaseConnection() {
        System.out.println("Testing database connection...");
        
        try {
            // Initialize the database
            DatabaseManager.initialize();
            System.out.println("✓ Database initialized successfully");
            
            // Get a connection
            try (Connection conn = DatabaseManager.getConnection()) {
                System.out.println("✓ Connection obtained successfully");
                
                // Print database information
                DatabaseMetaData metaData = conn.getMetaData();
                System.out.println("Database: " + metaData.getDatabaseProductName() + " " + 
                                  metaData.getDatabaseProductVersion());
                System.out.println("JDBC Driver: " + metaData.getDriverName() + " " + 
                                  metaData.getDriverVersion());
                
                // List all tables
                System.out.println("\nDatabase tables:");
                ResultSet tables = metaData.getTables(null, null, "%", new String[]{"TABLE"});
                int tableCount = 0;
                while (tables.next()) {
                    String tableName = tables.getString("TABLE_NAME");
                    System.out.println("- " + tableName);
                    
                    // Count rows in each table
                    try (Statement stmt = conn.createStatement();
                         ResultSet count = stmt.executeQuery("SELECT COUNT(*) FROM " + tableName)) {
                        if (count.next()) {
                            System.out.println("  Rows: " + count.getInt(1));
                        }
                    } catch (Exception e) {
                        System.out.println("  Error counting rows: " + e.getMessage());
                    }
                    tableCount++;
                }
                System.out.println("\nTotal tables: " + tableCount);
                
                // Test a simple query
                System.out.println("\nTesting a simple query...");
                try (Statement stmt = conn.createStatement();
                     ResultSet rs = stmt.executeQuery("SELECT 1")) {
                    if (rs.next()) {
                        System.out.println("✓ Query executed successfully");
                    }
                }
            }
            
            // Clean up
            DatabaseManager.shutdown();
            System.out.println("✓ Connection pool shutdown successfully");
            System.out.println("\nDatabase test completed successfully!");
            
        } catch (Exception e) {
            System.err.println("❌ Database test failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
}