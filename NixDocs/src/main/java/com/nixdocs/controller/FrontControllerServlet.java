package com.nixdocs.controller;

import com.nixdocs.controller.pojo.Controller;
import com.nixdocs.controller.pojo.LoginController;
import com.nixdocs.controller.pojo.RegisterController;
import com.nixdocs.controller.pojo.HomeController;
import com.nixdocs.util.templateEngine.ThymeleafUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Front Controller que maneja todas las solicitudes y las delega a los controladores apropiados.
 * Implementa el patrón Front Controller para centralizar el manejo de solicitudes.
 */
public class FrontControllerServlet extends HttpServlet {

    private final Map<String, Controller> controllers = new HashMap<>();

    @Override
    public void init() throws ServletException {
        // Asegurar que el motor de plantillas esté inicializado
        ThymeleafUtil.initialize(getServletContext(), false);
        // Registrar todos los controladores disponibles
        controllers.put("login", new LoginController());
        controllers.put("register", new RegisterController());
        controllers.put("home", new HomeController());
        // Agregar más controladores según sea necesario
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

    /**
     * Procesa la solicitud y la delega al controlador apropiado.
     */
    private void processRequest(HttpServletRequest request, HttpServletResponse response, String method)
            throws ServletException, IOException {
        
        // Obtener la ruta de la solicitud
        String pathInfo = request.getPathInfo();
        
        // Si la ruta es nula o es solo "/", redirigir a la página principal
        if (pathInfo == null || pathInfo.equals("/")) {
            response.sendRedirect(request.getContextPath() + "/app/home");
            return;
        }
        
        // Eliminar la barra inicial y obtener el nombre del controlador
        String controllerName = pathInfo.substring(1);
        
        // Verificar si hay parámetros adicionales en la URL
        if (controllerName.contains("/")) {
            controllerName = controllerName.split("/")[0];
        }
        
        // Buscar el controlador correspondiente
        Controller controller = controllers.get(controllerName);
        
        if (controller != null) {
            // Delegar la solicitud al controlador apropiado
            if (method.equals("GET")) {
                controller.processGet(request, response);
            } else if (method.equals("POST")) {
                controller.processPost(request, response);
            }
        } else {
            // Si no se encuentra un controlador, mostrar página de error 404
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Página no encontrada");
        }
    }
}