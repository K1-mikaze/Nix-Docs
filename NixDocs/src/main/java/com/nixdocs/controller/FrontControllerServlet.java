package com.nixdocs.controller;

import com.nixdocs.controller.pojo.Controller;
import com.nixdocs.controller.pojo.LoginController;
import com.nixdocs.controller.pojo.RegisterController;
import com.nixdocs.controller.pojo.HomeController;
import com.nixdocs.util.templateEngine.ThymeleafUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "baseServlet", urlPatterns = "/*")
public class FrontControllerServlet extends HttpServlet {

    private final Map<String, Controller> controllers = new HashMap<>();

    @Override
    public void init(){
        ThymeleafUtil.initialize(getServletContext(), false);

        controllers.put("app/login", new LoginController());
        controllers.put("app/register", new RegisterController());
        controllers.put("app/home", new HomeController());
        // Add more controller if is Necessary
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response, "GET");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response, "POST");
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response, String method)
            throws ServletException, IOException {
        
        String pathInfo = request.getPathInfo();

        if (pathInfo == null || pathInfo.equals("/")) {
            controllers.get("app/home").processGet(request,response);
            return;
        }

        String controllerName = pathInfo.substring(1);

        Controller controller = controllers.get(controllerName);
        if (controller != null) {
            if (method.equals("GET")) {
                controller.processGet(request, response);
            } else if (method.equals("POST")) {
                controller.processPost(request, response);
            }
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Page not found");
        }
    }
}