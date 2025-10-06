package com.nixdocs.repository;

import com.nixdocs.model.User;
import com.nixdocs.util.database.DatabaseManager;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.*;
import java.time.ZoneId;
import java.util.Optional;
import java.util.UUID;

/**
 * Implementación de UserRepository para PostgreSQL.
 * Utiliza DatabaseManager para obtener conexiones del pool.
 */
public class PostgresUserRepository implements UserRepository {

    /**
     * Guarda un usuario en la base de datos.
     * Si el usuario no tiene ID, se crea uno nuevo.
     * Si ya tiene ID, se actualiza el existente.
     *
     * @param user El usuario a guardar
     * @throws SQLException Si ocurre un error en la base de datos
     */
    @Override
    public void save(User user) throws SQLException {
        // Hashear la contraseña antes de guardarla
        String hashedPassword = BCrypt.hashpw(user.getPasswordHash(), BCrypt.gensalt(12));
        user.setPasswordHash(hashedPassword);

        if (user.getId() == null) {
            // Insertar nuevo usuario
            String sql = "INSERT INTO users (username, email, password_hash) VALUES (?, ?, ?) RETURNING id, created_at";
            
            try (Connection conn = DatabaseManager.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {
                
                stmt.setString(1, user.getUsername());
                stmt.setString(2, user.getEmail());
                stmt.setString(3, user.getPasswordHash());
                
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        user.setId(UUID.fromString(rs.getString("id")));
                        user.setCreatedAt(rs.getTimestamp("created_at").toInstant().atZone(ZoneId.systemDefault()));
                    }
                }
            }
        } else {
            // Actualizar usuario existente
            String sql = "UPDATE users SET username = ?, email = ?, password_hash = ? WHERE id = ?";
            
            try (Connection conn = DatabaseManager.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {
                
                stmt.setString(1, user.getUsername());
                stmt.setString(2, user.getEmail());
                stmt.setString(3, user.getPasswordHash());
                stmt.setObject(4, user.getId());
                
                stmt.executeUpdate();
            }
        }
    }

    /**
     * Busca un usuario por su email.
     *
     * @param email El email del usuario a buscar
     * @return Un Optional con el usuario si existe, o vacío si no
     * @throws SQLException Si ocurre un error en la base de datos
     */
    @Override
    public Optional<User> findByEmail(String email) throws SQLException {
        String sql = "SELECT id, username, email, password_hash, created_at FROM users WHERE email = ?";
        
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, email);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToUser(rs));
                }
            }
        }
        
        return Optional.empty();
    }

    /**
     * Busca un usuario por su nombre de usuario.
     *
     * @param username El nombre de usuario a buscar
     * @return Un Optional con el usuario si existe, o vacío si no
     * @throws SQLException Si ocurre un error en la base de datos
     */
    @Override
    public Optional<User> findByUsername(String username) throws SQLException {
        String sql = "SELECT id, username, email, password_hash, created_at FROM users WHERE username = ?";
        
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, username);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToUser(rs));
                }
            }
        }
        
        return Optional.empty();
    }

    /**
     * Convierte un ResultSet en un objeto User.
     *
     * @param rs El ResultSet con los datos del usuario
     * @return El objeto User creado
     * @throws SQLException Si ocurre un error al acceder a los datos
     */
    private User mapResultSetToUser(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(UUID.fromString(rs.getString("id")));
        user.setUsername(rs.getString("username"));
        user.setEmail(rs.getString("email"));
        user.setPasswordHash(rs.getString("password_hash"));
        user.setCreatedAt(rs.getTimestamp("created_at").toInstant().atZone(ZoneId.systemDefault()));
        return user;
    }
}