package database;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Utility class for managing database connections.
 */
public class Database_management {

    /**
     * Establishes a connection to the database.
     *
     * @return Connection object if connection is successful, null otherwise.
     */
    public static Connection connectDb() {
        try {
            // Load the MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish connection to the database
            Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/db_dictionary", "root", "vanbns2");

            // Print connection status to console
            System.out.println("Connection established successfully.");
            return con;
        } catch (Exception e) {
            // Print stack trace in case of exception
            e.printStackTrace();
        }
        return null; // Return null if connection fails
    }
}
