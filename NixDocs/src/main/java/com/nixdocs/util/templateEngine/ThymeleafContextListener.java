package com.nixdocs.util.templateEngine;

import com.nixdocs.util.database.DatabaseManager;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class ThymeleafContextListener implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		// Inicializar Thymeleaf
		ThymeleafUtil.initialize(sce.getServletContext(),false); // Cachable should be true in production
		System.out.println("🔄 NixDocs: Thymeleaf initialized successfully");
		
		// La primera llamada a getConnection inicializará el pool de conexiones
		try {
			DatabaseManager.getConnection().close();
			System.out.println("🔄 NixDocs: Database connection pool initialized successfully");
		} catch (Exception e) {
			System.err.println("❌ NixDocs: Error initializing database connection pool: " + e.getMessage());
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// Cerrar el pool de conexiones
		DatabaseManager.closePool();
		System.out.println("🔄 NixDocs: Database connection pool closed");
		
		System.out.println("🔴 NixDocs: Application shutting down");
	}
}
