package com.nixdocs.repository;

import com.nixdocs.model.User;

import org.mindrot.jbcrypt.BCrypt;

import java.sql.SQLException;
import java.util.Optional;

public interface UserRepository {
    
    void save(User user) throws SQLException;
    
    Optional<User> findByEmail(String email) throws SQLException;
    
    Optional<User> findByUsername(String username) throws SQLException;

     String hashPassword(String password);

    public boolean verifyPassword(String plainPassword, String hashedPassword);
}