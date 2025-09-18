package nixdocs.repository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.sql.DataSource;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class UserRepository {

    private final DataSource dataSource;

    private enum DatabaseType {
        POSTGRES,
        MYSQL
    }

    public UserRepository() {
        dataSource = switch (DatabaseType.valueOf(System.getProperty("DB_TYPE", "POSTGRES"))) {
            case POSTGRES -> getPostgresDataSource();
            case MYSQL -> getMySQLDataSource(); 
        };
    }

    private static DataSource getPostgresDataSource() {
        HikariConfig config = new HikariConfig();

        config.setJdbcUrl(
            System.getProperty("DB_JDBC_URL", "jdbc:postgresql://127.0.0.1:5432/nixdocs")
        );
        config.setUsername(
            System.getProperty("DB_USERNAME", "postgres")
        );
        config.setPassword(
            System.getProperty("DB_PASSWORD", "postgres")
        );

        config.setMaximumPoolSize(10);
        config.setMinimumIdle(2);
        config.setIdleTimeout(30000);
        config.setMaxLifetime(1800000);

        return new HikariDataSource(config);
    }

    private static DataSource getMySQLDataSource() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(System.getProperty("DB_JDBC_URL", "jdbc:mysql://127.0.0.1:3306/nixdocs"));
        config.setUsername(System.getProperty("DB_USERNAME", "root"));
        config.setPassword(System.getProperty("DB_PASSWORD", "root"));
        return new HikariDataSource(config);
    }

    public List<UserEntity> users() {
        List<UserEntity> users = new ArrayList<>();
        String query = "SELECT id, email, name, password FROM users";

        try (
            var conn = dataSource.getConnection();
            var stmt = conn.prepareStatement(query);
            var rs = stmt.executeQuery()
        ) {
            while (rs.next()) {
                users.add(new UserEntity(
                    UUID.fromString(rs.getString("id")),
                    rs.getString("email"),
                    rs.getString("name"),
                    rs.getString("password")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener los usuarios", e);
        }

        return users;
    }
}
