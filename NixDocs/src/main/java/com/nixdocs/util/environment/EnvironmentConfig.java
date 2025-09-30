package com.nixdocs.util.environment;

import io.github.cdimascio.dotenv.Dotenv;

public class EnvironmentConfig {

        private static final Dotenv dotenv = Dotenv.configure().load();

        public static String getDbUrl() {
            return dotenv.get("DB_URL");
        }

        public static String getDbUsername() {
            return dotenv.get("DB_USERNAME");
        }

        public static String getDbPassword() {
            return dotenv.get("DB_PASSWORD");
        }

        public static String getDbDriver() {
            return dotenv.get("DB_DRIVER");
        }

        // Optional: Get with default values
        public static int getMaxPoolSize() {
            String size = dotenv.get("DB_MAX_POOL_SIZE");
            return size != null ? Integer.parseInt(size) : 10;
        }

        public static int getConnectionTimeout() {
            String timeout = dotenv.get("DB_CONNECTION_TIMEOUT");
            return timeout != null ? Integer.parseInt(timeout) : 30000;
        }
    }
