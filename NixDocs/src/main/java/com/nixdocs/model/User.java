package com.nixdocs.model;

import java.time.ZonedDateTime;
import java.util.UUID;

/**
 * Clase que representa un usuario en el sistema.
 * Implementada como un JavaBean con getters y setters.
 */
public class User {
    private UUID id;
    private String username;
    private String email;
    private String passwordHash;
    private ZonedDateTime createdAt;

    // Constructor vacío requerido para JavaBean
    public User() {
    }

    // Constructor con parámetros para facilitar la creación
    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        // La contraseña se almacena sin hashear aquí, será hasheada en el repositorio
        // antes de guardarla en la base de datos
        this.passwordHash = password;
    }

    // Getters y setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // Getter para passwordHash necesario para el repositorio
    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * Método para verificar si una contraseña coincide con el hash almacenado.
     * Utiliza BCrypt para la verificación segura.
     * 
     * @param password La contraseña a verificar
     * @return true si la contraseña coincide, false en caso contrario
     */
    public boolean checkPassword(String password) {
        return org.mindrot.jbcrypt.BCrypt.checkpw(password, this.passwordHash);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}