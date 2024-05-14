package databaseDAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import models.dictionaryManagement;

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

    /**
     * Loads data from the database.
     *
     * @return List of Word objects loaded from the database.
     */
    public static List<dictionaryManagement> loadDataDAO() {
        List<dictionaryManagement> wordList = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            // Connect to the database
            conn = connectDb();

            // Prepare SQL statement for selecting data
            String sql = "SELECT * FROM dictionary_english_vietnamese";
            stmt = conn.prepareStatement(sql);

            // Execute query
            rs = stmt.executeQuery();

            // Process result set
            while (rs.next()) {
                String wordId = rs.getString("word_id");
                String englishWord = rs.getString("english_word");
                String vietnameseMeaning = rs.getString("vietnamese_meaning");
                dictionaryManagement word = new dictionaryManagement(wordId, englishWord, vietnameseMeaning);
                wordList.add(word);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close resources
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return wordList;
    }
    
}
