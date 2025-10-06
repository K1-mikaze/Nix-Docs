package com.nixdocs.controller.pojo;

import com.nixdocs.util.templateEngine.ThymeleafUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HomeController implements Controller {

    @Override
    public void processGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, Object> variables = new HashMap<>();
        variables.put("pageTitle", "Home");

        // TODO
        ThymeleafUtil.renderTemplate(request, response, "index", variables);
    }

    @Override
    public void processPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendRedirect(request.getContextPath() + "/app/home");
    }
}