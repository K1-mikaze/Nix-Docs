package com.nixdocs.util.templateEngine;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

@WebServlet(urlPatterns = {"/static/core/*", "/static/image/*", "/static/style/*"})
public class StaticResourceServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws  IOException {

        String path = request.getRequestURI().substring(request.getContextPath().length());

        InputStream resource = getServletContext().getResourceAsStream(path);

        if (resource != null) {
            if (path.endsWith(".css")) {
                response.setContentType("text/css");
            } else if (path.endsWith(".js")) {
                response.setContentType("application/javascript");
            } else if (path.endsWith(".png")) {
                response.setContentType("image/png");
            }
            try (InputStream in = resource;
                 OutputStream out = response.getOutputStream()) {
                in.transferTo(out);
            }
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}