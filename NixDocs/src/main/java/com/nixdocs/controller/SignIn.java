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
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@WebServlet(name = "signInServlet", urlPatterns = {"/signin"})
public class SignIn extends HttpServlet {

    private final UserRepository userRepository = new PostgresUserRepository();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String path = request.getRequestURI().substring(request.getContextPath().length());
        try {
            Map<String, Object> variables = new HashMap<>();
            variables.put("pageTitle", "Sign In");
            
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

            ThymeleafUtil.renderTemplate(request, response, "signIn", variables);
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Obtener parámetros del formulario
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        
        // Validaciones básicas
        if (email == null || email.trim().isEmpty() || 
            password == null || password.trim().isEmpty()) {
            
            setErrorAndRedirect(request, response, "Todos los campos son obligatorios");
            return;
        }
        
        try {
            // Buscar usuario por email
            Optional<User> userOptional = userRepository.findByEmail(email);
            
            // Si no se encuentra el usuario, verificar si se ingresó un nombre de usuario en lugar de email
            if (!userOptional.isPresent()) {
                userOptional = userRepository.findByUsername(email);
            }
            
            // Si no se encuentra el usuario o la contraseña es incorrecta
            if (!userOptional.isPresent() || !userOptional.get().checkPassword(password)) {
                setErrorAndRedirect(request, response, "Credenciales inválidas");
                return;
            }
            
            // Usuario autenticado correctamente
            User authenticatedUser = userOptional.get();
            
            // Crear sesión HTTP
            HttpSession session = request.getSession(true);
            
            // Guardar información del usuario en la sesión (nunca la contraseña)
            session.setAttribute("userId", authenticatedUser.getId().toString());
            session.setAttribute("username", authenticatedUser.getUsername());
            session.setAttribute("email", authenticatedUser.getEmail());
            session.setAttribute("authenticated", true);
            
            // Redirigir a la página principal
            response.sendRedirect(request.getContextPath() + "/");
            
        } catch (SQLException e) {
            setErrorAndRedirect(request, response, "Error al procesar la solicitud: " + e.getMessage());
        }
    }
    
    private void setErrorAndRedirect(HttpServletRequest request, HttpServletResponse response, String errorMessage) 
            throws IOException {
        request.getSession().setAttribute("errorMessage", errorMessage);
        response.sendRedirect(request.getContextPath() + "/signin");
    }
}