package com.nixdocs.util.environment;

import io.github.cdimascio.dotenv.Dotenv;

public class EnvironmentConfig {

    private static final Dotenv dotenv;

    static {
        Dotenv loadedDotenv = null;
        try {
            loadedDotenv = Dotenv.configure()
                    .ignoreIfMissing()
                    .load();

        } catch (Exception e) {
            System.err.println("========! EnvironmentConfig: Dotenv initialization failed: " + e.getMessage());
        }

        dotenv = loadedDotenv;

        if (dotenv != null && dotenv.get("DB_URL") != null && dotenv.get("DB_USERNAME") != null && dotenv.get("DB_PASSWORD") != null && dotenv.get("EMAIL_ADDRESS") != null && dotenv.get("EMAIL_PASSWORD") != null) {
            System.out.println("======> EnvironmentConfig: .env configuration loaded <====");
        } else {
            System.err.println("======! EnvironmentConfig: Using system properties, Create the .env file with the appropriate keys or read the README.md file!=======");
        }
    }

    public static String getDbUrl() {
        return getValue("DB_URL", true);
    }

    public static String getDbUsername() {
        return getValue("DB_USERNAME", true);
    }

    public static String getDbPassword() {
        return getValue("DB_PASSWORD", true);
    }

    public static String getEmailAddress() {
        return getValue("EMAIL_ADDRESS", true);
    }

    public static String getEmailPassword() {
        return getValue("EMAIL_PASSWORD", true);
    }

    public static String getEmailHost() {
        return getValue("EMAIL_HOST", false, "smtp.gmail.com");
    }

    public static String getDbDriver() {
        return getValue("DB_DRIVER", false, "org.postgresql.Driver");
    }

    public static int getMaxPoolSize() {
        String value = getValue("DB_MAX_POOL_SIZE", false, "10");
        return Integer.parseInt(value);
    }

    public static int getConnectionTimeout() {
        String value = getValue("DB_CONNECTION_TIMEOUT", false, "30000");
        return Integer.parseInt(value);
    }


    private static String getValue(String key, boolean required) {
        return getValue(key, required, null);
    }

    private static String getValue(String key, boolean required, String defaultValue) {
        if (dotenv != null) {
            String value = dotenv.get(key);
            if (value != null) return value;
        }

        String systemValue = System.getProperty(key);
        if (systemValue != null) return systemValue;

        String envValue = System.getenv(key);
        if (envValue != null) return envValue;

        if (required) {
            throw new IllegalStateException(
                    "====! Required configuration '" + key + "' not found in .env file, " +
                            "system properties, or environment variables !======"
            );
        }
        return defaultValue;
    }
}