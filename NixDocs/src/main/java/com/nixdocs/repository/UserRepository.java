package com.nixdocs.repository;

import com.nixdocs.model.User;

import java.sql.SQLException;
import java.util.Optional;

/**
 * Interfaz para el acceso a datos de usuarios.
 */
public interface UserRepository {
    
    /**
     * Guarda un usuario en la base de datos.
     * Si el usuario ya existe, lo actualiza.
     * 
     * @param user El usuario a guardar
     * @throws SQLException Si ocurre un error en la base de datos
     */
    void save(User user) throws SQLException;
    
    /**
     * Busca un usuario por su email.
     * 
     * @param email El email del usuario a buscar
     * @return Un Optional con el usuario si existe, o vacío si no
     * @throws SQLException Si ocurre un error en la base de datos
     */
    Optional<User> findByEmail(String email) throws SQLException;
    
    /**
     * Busca un usuario por su nombre de usuario.
     * 
     * @param username El nombre de usuario a buscar
     * @return Un Optional con el usuario si existe, o vacío si no
     * @throws SQLException Si ocurre un error en la base de datos
     */
    Optional<User> findByUsername(String username) throws SQLException;
}