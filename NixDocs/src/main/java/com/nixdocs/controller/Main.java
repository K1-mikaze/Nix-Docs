package com.nixdocs.controller;

import com.nixdocs.util.templateEngine.ThymeleafUtil;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet; 
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/")
public class Main extends HttpServlet {

    @Override
    public void init() throws ServletException {
        // ... the rest of your code is perfect ...
        ThymeleafUtil.initialize(getServletContext(), false);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
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