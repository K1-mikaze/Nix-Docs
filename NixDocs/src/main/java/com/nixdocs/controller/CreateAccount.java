package com.nixdocs.controller;

import com.nixdocs.util.templateEngine.ThymeleafUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "createAccountServlet" ,urlPatterns = {"/signup"})
public class CreateAccount extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String path = request.getRequestURI().substring(request.getContextPath().length());
        try {
            Map<String, Object> variables = new HashMap<>();
            variables.put("pageTitle", "Sign In");

            ThymeleafUtil.renderTemplate(request, response, "createAccount", variables);
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}