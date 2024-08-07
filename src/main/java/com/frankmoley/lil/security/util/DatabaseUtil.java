package com.frankmoley.lil.security.util;

import org.h2.tools.RunScript;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseUtil {

    static Connection connection;

    static {
        var url = "jdbc:h2:mem:";
        try {
            connection = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println("Error creating connection: " + e.getMessage());
            throw new RuntimeException("database creation error");
        }
    }

    public static Connection getConnection() {
        return connection;
    }

    public static void loadFile(String fileName) throws IOException, SQLException {
        ClassLoader classLoader = DatabaseUtil.class.getClassLoader();
        InputStream resource = classLoader.getResourceAsStream(fileName);
        if (resource == null) {
            throw new FileNotFoundException("File not found: " + fileName);
        }
        try (InputStreamReader reader = new InputStreamReader(resource)) {
            RunScript.execute(getConnection(), reader);
        }
    }
}
