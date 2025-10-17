package com.nixdocs.repository;

import com.nixdocs.model.User;

import java.sql.SQLException;
import java.util.Optional;

public interface UserRepository {
    
    void save(User user) throws SQLException;
    
    Optional<User> findByEmail(String email) throws SQLException;
    
    Optional<User> findByUsername(String username) throws SQLException;
}