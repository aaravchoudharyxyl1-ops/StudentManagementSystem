package com.studentmanagement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class Database {

    private Database() {
        // Prevent object creation
    }

    public static Connection connect() throws SQLException {

        String url = System.getenv().getOrDefault(
                "DB_URL",
                "jdbc:mysql://localhost:3306/student_management"
        );

        String user = System.getenv().getOrDefault(
                "DB_USER",
                "student_app"
        );

        String password = System.getenv("DB_PASSWORD");

        if (password == null || password.isBlank()) {
            throw new SQLException(
                    "DB_PASSWORD is missing. Set the DB_PASSWORD environment variable."
            );
        }

        return DriverManager.getConnection(url, user, password);
    }
}