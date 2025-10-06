package com.nixdocs.controller.pojo;

import com.nixdocs.model.User;
import com.nixdocs.repository.PostgresUserRepository;
import com.nixdocs.repository.UserRepository;
import com.nixdocs.util.templateEngine.ThymeleafUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Controlador POJO para manejo de autenticación (login).
 */
public class LoginController implements Controller {

    private final UserRepository userRepository = new PostgresUserRepository();

    @Override
    public void processGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        showLoginForm(request, response);
    }

    @Override
    public void processPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        handleLogin(request, response);
    }

    public void showLoginForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, Object> variables = new HashMap<>();
        variables.put("pageTitle", "Sign In");

        // Mensajes desde la sesión si existen
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

        // Renderizar plantilla de login (asegurado que Thymeleaf está inicializado por el Front Controller)
        try {
            ThymeleafUtil.renderTemplate(request, response, "signIn", variables);
        } catch (Exception e) {
            throw new ServletException("Error renderizando la plantilla signIn", e);
        }
    }

    public void handleLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String emailOrUsername = request.getParameter("email");
        String password = request.getParameter("password");

        if (emailOrUsername == null || emailOrUsername.trim().isEmpty() ||
            password == null || password.trim().isEmpty()) {
            setErrorAndRedirect(request, response, "Todos los campos son obligatorios");
            return;
        }

        try {
            Optional<User> userOptional = userRepository.findByEmail(emailOrUsername);
            if (!userOptional.isPresent()) {
                userOptional = userRepository.findByUsername(emailOrUsername);
            }

            if (!userOptional.isPresent() || !userOptional.get().checkPassword(password)) {
                setErrorAndRedirect(request, response, "Credenciales inválidas");
                return;
            }

            // Usuario autenticado
            User authenticatedUser = userOptional.get();
            HttpSession session = request.getSession(true);
            session.setAttribute("userId", authenticatedUser.getId().toString());
            session.setAttribute("username", authenticatedUser.getUsername());
            session.setAttribute("email", authenticatedUser.getEmail());
            session.setAttribute("authenticated", true);

            // Redirigir a la nueva ruta de home gestionada por el Front Controller
            response.sendRedirect(request.getContextPath() + "/app/home");
        } catch (Exception e) {
            setErrorAndRedirect(request, response, "Error interno al procesar el inicio de sesión");
        }
    }

    private void setErrorAndRedirect(HttpServletRequest request, HttpServletResponse response, String message) throws IOException {
        request.getSession().setAttribute("errorMessage", message);
        response.sendRedirect(request.getContextPath() + "/app/login");
    }
}