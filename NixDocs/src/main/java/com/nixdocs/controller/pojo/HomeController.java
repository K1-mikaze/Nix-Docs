package com.nixdocs.controller.pojo;

import com.nixdocs.util.templateEngine.ThymeleafUtil;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Controlador POJO para la página principal.
 */
public class HomeController implements Controller {

    @Override
    public void processGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, Object> variables = new HashMap<>();
        variables.put("pageTitle", "Home");

        // Renderizar plantilla de login (asegurado que Thymeleaf está inicializado por el Front Controller)
        try {
            ThymeleafUtil.renderTemplate(request, response, "signIn", variables);
        } catch (Exception e) {
            throw new ServletException("Error renderizando la plantilla signIn", e);
        }
    }

    @Override
    public void processPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendRedirect(request.getContextPath() + "/app/home");
    }
}