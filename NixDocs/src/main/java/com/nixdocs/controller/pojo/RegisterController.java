package com.nixdocs.controller.pojo;

import com.nixdocs.util.templateEngine.ThymeleafUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class RegisterController implements Controller {

    @Override
    public void processGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, Object> variables = new HashMap<>();
        variables.put("pageTitle", "Create Account");
            ThymeleafUtil.renderTemplate(request, response, "createAccount", variables);
    }

    @Override
    public void processPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Implementación del registro puede agregarse aquí.
        response.sendError(HttpServletResponse.SC_NOT_IMPLEMENTED, "Registro no implementado aún en Front Controller");
    }
}