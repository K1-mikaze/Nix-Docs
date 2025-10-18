package com.nixdocs.repository;

import com.nixdocs.model.User;
import com.nixdocs.util.database.DatabaseManager;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.*;
import java.time.ZoneId;
import java.util.Optional;
import java.util.UUID;

public class PostgresUserRepository implements UserRepository {

    @Override
    public void save(User user) throws SQLException {
        String hashedPassword = hashPassword(user.getPassword());
        user.setPassword(hashedPassword);

        if (user.getId() == null) {
            String sql = "INSERT INTO users (username, email, password,verified) VALUES (?, ?, ?,?) RETURNING id, creation";
            
            try (Connection conn = DatabaseManager.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {
                
                stmt.setString(1, user.getUsername());
                stmt.setString(2, user.getEmail());
                stmt.setString(3, user.getPassword());
                stmt.setBoolean(4, user.getVerified());

                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        user.setId(UUID.fromString(rs.getString("id")));
                        user.setCreation(rs.getTimestamp("creation").toInstant().atZone(ZoneId.systemDefault()));
                    }
                }
            }
        } else {
            String sql = "UPDATE users SET username = ?, email = ?, password = ? WHERE id = ?";
            
            try (Connection conn = DatabaseManager.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {
                
                stmt.setString(1, user.getUsername());
                stmt.setString(2, user.getEmail());
                stmt.setString(3, user.getPassword());
                stmt.setObject(4, user.getId());
                
                stmt.executeUpdate();
            }
        }
    }

    @Override
    public Optional<User> findByEmail(String email) throws SQLException {
        String sql = "SELECT id, username, email, password,verified, creation FROM users WHERE email = ?";
        
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


    @Override
    public Optional<User> findByUsername(String username) throws SQLException {
        String sql = "SELECT id, username, email, password,verified,creation FROM users WHERE username = ?";
        
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

    private User mapResultSetToUser(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(UUID.fromString(rs.getString("id")));
        user.setUsername(rs.getString("username"));
        user.setEmail(rs.getString("email"));
        user.setPassword(rs.getString("password"));
        user.setVerified(rs.getBoolean("verified"));
        user.setCreation(rs.getTimestamp("creation").toInstant().atZone(ZoneId.systemDefault()));
        return user;
    }

    public String hashPassword(String password){
        return BCrypt.hashpw(password, BCrypt.gensalt(12));
    }

    public boolean verifyPassword(String plainPassword, String hashedPassword) {
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }
}