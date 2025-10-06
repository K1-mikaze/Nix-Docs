package com.nixdocs.util.templateEngine;

import com.nixdocs.util.database.DatabaseManager;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;


@WebListener
public class ThymeleafContextListener implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		ThymeleafUtil.initialize(sce.getServletContext(),false); // Cachable should be true in production
		System.out.println("======> Thymeleaf initialized <========");
		
		try {
			DatabaseManager.getConnection().close();
			System.out.println("======> Database connection pool initialized  <========");
		} catch (Exception e) {
			System.err.println("======! Error initializing database connection pool: " + e.getMessage());
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {

		DatabaseManager.closePool();
		System.out.println("=====> Database connection pool closed <======");
	}
}
