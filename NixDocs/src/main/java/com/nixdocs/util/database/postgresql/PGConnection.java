package com.nixdocs.util.database.postgresql;

import com.nixdocs.util.environment.EnvironmentConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PGConnection {
    private static final String URL = EnvironmentConfig.getDbUrl();
    private static final String USER = EnvironmentConfig.getDbUsername();
    private static final String PASSWORD = EnvironmentConfig.getDbPassword();

    public static Connection getConnection() throws SQLException {

        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}