package com.nixdocs.util.templateEngine;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class ThymeleafContextListener implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		ThymeleafUtil.initialize(sce.getServletContext(),false); // Cachable should be true in production
		System.out.println("🔄 NixDocs: Thymeleaf initialized successfully");
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		System.out.println("🔴 NixDocs: Application shutting down");
	}
}
