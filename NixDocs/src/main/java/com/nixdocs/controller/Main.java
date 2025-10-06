package com.nixdocs.controller;

import com.nixdocs.util.templateEngine.ThymeleafUtil;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "mainServlet",urlPatterns = {""})
public class Main extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        // ... the rest of your code is perfect ...
        try {
            String path = request.getRequestURI().substring(request.getContextPath().length());

            if (path.startsWith("/static/")) {
                request.getRequestDispatcher(path).forward(request, response);
                return;
            }

            Map<String, Object> variables = new HashMap<>();
            variables.put("pageTitle", "Nix Docs");

            ThymeleafUtil.renderTemplate(request, response, "index", variables);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Template rendering failed: " + e.getMessage());
        }
    }
}