package com.nixdocs.controller.pojo;

import com.nixdocs.model.User;
import com.nixdocs.repository.PostgresUserRepository;
import com.nixdocs.repository.UserRepository;
import com.nixdocs.util.templateEngine.ThymeleafUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class LoginController implements Controller {

    private final UserRepository userRepository = new PostgresUserRepository();

    @Override
    public void processGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
            showLoginForm(request, response);
    }

    @Override
    public void processPost(HttpServletRequest request, HttpServletResponse response) throws IOException,SQLException {
        handleLogin(request, response);
    }

    public void showLoginForm(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, Object> variables = new HashMap<>();
        variables.put("pageTitle", "Sign In");

        ThymeleafUtil.renderTemplate(request, response, "signIn", variables);
    }

    public void handleLogin(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        if (email == null || email.trim().isEmpty() ||
            password == null || password.trim().isEmpty()) {
            setErrorAndRedirect(request, response, "Todos los campos son obligatorios");
            return;
        }

            Optional<User> userOptional = userRepository.findByEmail(email);

            if (userOptional.isEmpty() || !userOptional.get().checkPassword(password)) {
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
            setErrorAndRedirect(request, response, "Error interno al procesar el inicio de sesión");
    }

    private void setErrorAndRedirect(HttpServletRequest request, HttpServletResponse response, String message) throws IOException {
        request.getSession().setAttribute("errorMessage", message);
        response.sendRedirect(request.getContextPath() + "/app/login");
    }
}