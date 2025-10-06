package com.nixdocs.controller;

import com.nixdocs.model.User;
import com.nixdocs.repository.PostgresUserRepository;
import com.nixdocs.repository.UserRepository;
import com.nixdocs.util.templateEngine.ThymeleafUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;

@WebServlet(name = "createAccountServlet" ,urlPatterns = {"/signup"})
public class CreateAccount extends HttpServlet {
    
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");
    private final UserRepository userRepository = new PostgresUserRepository();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String path = request.getRequestURI().substring(request.getContextPath().length());
        try {
            Map<String, Object> variables = new HashMap<>();
            variables.put("pageTitle", "Create Account");
            
            // Recuperar mensajes de error o éxito si existen
            String errorMessage = (String) request.getSession().getAttribute("errorMessage");
            String successMessage = (String) request.getSession().getAttribute("successMessage");
            
            if (errorMessage != null) {
                variables.put("errorMessage", errorMessage);
                request.getSession().removeAttribute("errorMessage");
            }
            
            if (successMessage != null) {
                variables.put("successMessage", successMessage);
                request.getSession().removeAttribute("successMessage");
            }

            ThymeleafUtil.renderTemplate(request, response, "createAccount", variables);
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Obtener parámetros del formulario
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        
        // Validaciones básicas
        if (username == null || username.trim().isEmpty() || 
            email == null || email.trim().isEmpty() || 
            password == null || password.trim().isEmpty()) {
            
            setErrorAndRedirect(request, response, "Todos los campos son obligatorios");
            return;
        }
        
        // Validar formato de email
        if (!EMAIL_PATTERN.matcher(email).matches()) {
            setErrorAndRedirect(request, response, "El formato del email no es válido");
            return;
        }
        
        // Validar longitud de contraseña
        if (password.length() < 8) {
            setErrorAndRedirect(request, response, "La contraseña debe tener al menos 8 caracteres");
            return;
        }
        
        try {
            // Verificar si el email ya existe
            Optional<User> existingUserByEmail = userRepository.findByEmail(email);
            if (existingUserByEmail.isPresent()) {
                setErrorAndRedirect(request, response, "El email ya está registrado");
                return;
            }
            
            // Verificar si el username ya existe
            Optional<User> existingUserByUsername = userRepository.findByUsername(username);
            if (existingUserByUsername.isPresent()) {
                setErrorAndRedirect(request, response, "El nombre de usuario ya está en uso");
                return;
            }
            
            // Crear y guardar el nuevo usuario
            User newUser = new User(username, email, password);
            userRepository.save(newUser);
            
            // Redirigir a la página de inicio de sesión con mensaje de éxito
            request.getSession().setAttribute("successMessage", "Cuenta creada exitosamente. Ahora puedes iniciar sesión.");
            response.sendRedirect(request.getContextPath() + "/signin");
            
        } catch (SQLException e) {
            setErrorAndRedirect(request, response, "Error al procesar la solicitud: " + e.getMessage());
        }
    }
    
    private void setErrorAndRedirect(HttpServletRequest request, HttpServletResponse response, String errorMessage) 
            throws IOException {
        request.getSession().setAttribute("errorMessage", errorMessage);
        response.sendRedirect(request.getContextPath() + "/signup");
    }
}