package com.steve.crud;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

/**
 * The DbConnection class will be used as a utility class to allow connection to the database and to execute
 * queries in the database.
 */

public class DbConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/cats?autoReconnect=true&useSSL=false";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "root";

    /**
     * The getConnection method will retrieve a connection to the mySQL database.
     * @return Connection to mySQL database.
     */
    public static Connection getConnection() {
        Connection conn;
        try {
            conn = DriverManager.getConnection(URL,
                    DB_USERNAME, DB_PASSWORD);
            return conn;
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            return null;
        }
    }
    /**
     * The executeQuery method will execute the query into the table for manipulating data.
     * @param query The query to execute.
     */
    public static void executeQuery(String query) {
        Connection conn = getConnection();
        Statement st;
        try {
            if (conn != null) {
                st = conn.createStatement();
                st.executeUpdate(query);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
