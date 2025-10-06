package com.nixdocs.util.database;

import com.nixdocs.util.environment.EnvironmentConfig;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseManager {
    private static final DatabaseManager INSTANCE;
    private final HikariDataSource dataSource;

    static {
        try {
            INSTANCE = new DatabaseManager();
        } catch (Exception e) {
            System.err.println("=======! DatabaseManager initialization failed: " + e.getMessage());
            throw new ExceptionInInitializerError(e);
        }
    }

    private DatabaseManager() {
        try {
            HikariConfig config = new HikariConfig();

            config.setJdbcUrl(EnvironmentConfig.getDbUrl());
            config.setUsername(EnvironmentConfig.getDbUsername());
            config.setPassword(EnvironmentConfig.getDbPassword());
            config.setDriverClassName(EnvironmentConfig.getDbDriver());

            // this is optional
            config.setMaximumPoolSize(EnvironmentConfig.getMaxPoolSize());
            config.setConnectionTimeout(EnvironmentConfig.getConnectionTimeout());

            dataSource = new HikariDataSource(config);

            try (Connection testConn = dataSource.getConnection()) {
                System.out.println(" =====> DatabaseManager: Successfully connected to database <=======");
            }

        } catch (Exception e) {
            System.err.println("=========! DatabaseManager: Failed to initialize database connection: " + e.getMessage()  );
            e.printStackTrace();
            throw new RuntimeException("=======! Database initialization failed", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return INSTANCE.dataSource.getConnection();
    }

    public static void closePool() {
        if (INSTANCE.dataSource != null && !INSTANCE.dataSource.isClosed()) {
            INSTANCE.dataSource.close();
            System.out.println("====> DatabaseManager: Connection pool closed <=====");
        }
    }
}