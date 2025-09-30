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
@WebServlet(name = "mainServlet", urlPatterns = {""})
public class Main extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {




        String path = request.getRequestURI().substring(request.getContextPath().length());

        if (path.startsWith("/static/core/") || path.startsWith("/static/image/") || path.startsWith("/static/style/")) {
            request.getRequestDispatcher(path).forward(request, response);
            return;
        }

        try {
            Map<String, Object> variables = new HashMap<>();
            variables.put("pageTitle", "Nix Docs");

            ThymeleafUtil.renderTemplate(request, response, "index", variables);
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}