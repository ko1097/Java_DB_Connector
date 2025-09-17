package com.example;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;



public class JdbcConnector {

   
    private static String url;
    private static String user;
    private static String password;

    // Static initializer block
    static {
        try (InputStream input = JdbcConnector.class
                .getClassLoader()
                .getResourceAsStream("application.properties")) {

            if (input == null) {
                throw new RuntimeException("Cannot find application.properties");
            }

            Properties props = new Properties();
            props.load(input);

            url = props.getProperty("spring.datasource.url");
            user = props.getProperty("spring.datasource.username");
            password = props.getProperty("spring.datasource.password");

        } catch (IOException e) {
            throw new RuntimeException("Failed to load DB properties", e);
        }
        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            
            // Example: SELECT
            String selectSql = "SELECT * FROM BOOK";
            try (PreparedStatement ps = conn.prepareStatement(selectSql);
                 ResultSet rs = ps.executeQuery()) {
                System.out.println(rs+"result output");
                while (rs.next()) {
                    int id = rs.getInt("RollNo");
                    System.out.println(id+"id output");
    
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

	 

}
