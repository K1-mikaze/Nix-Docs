package com.nixdocs.util.database;

import com.nixdocs.util.environment.EnvironmentConfig;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Clase Singleton que gestiona el pool de conexiones a la base de datos
 * utilizando HikariCP.
 */
public class DatabaseManager {
    private static final DatabaseManager INSTANCE = new DatabaseManager();
    private final HikariDataSource dataSource;

    private DatabaseManager() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(EnvironmentConfig.getDbUrl());
        config.setUsername(EnvironmentConfig.getDbUsername());
        config.setPassword(EnvironmentConfig.getDbPassword());
        
        // Configuración opcional desde variables de entorno
        config.setMaximumPoolSize(EnvironmentConfig.getMaxPoolSize());
        config.setConnectionTimeout(EnvironmentConfig.getConnectionTimeout());
        
        // Configuración adicional recomendada
        config.setAutoCommit(true);
        config.setMinimumIdle(5);
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        
        dataSource = new HikariDataSource(config);
    }

    /**
     * Obtiene una conexión del pool de conexiones.
     * 
     * @return Una conexión a la base de datos
     * @throws SQLException Si ocurre un error al obtener la conexión
     */
    public static Connection getConnection() throws SQLException {
        return INSTANCE.dataSource.getConnection();
    }

    /**
     * Cierra el pool de conexiones.
     * Este método debe ser llamado cuando la aplicación se detenga.
     */
    public static void closePool() {
        if (INSTANCE.dataSource != null && !INSTANCE.dataSource.isClosed()) {
            INSTANCE.dataSource.close();
        }
    }
}