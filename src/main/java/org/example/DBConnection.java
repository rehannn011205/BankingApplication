package org.example;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection{
    static String URL = "jdbc:postgresql://localhost:5432/banking_db";
    static String USER = "postgres";
    static String PASSWORD = "Rehannn011205";

    public static Connection getConnection() throws SQLException{
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}