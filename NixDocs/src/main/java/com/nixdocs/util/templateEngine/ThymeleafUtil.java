package com.nixdocs.util.templateEngine;

import java.io.IOException;
import java.util.Map;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.WebApplicationTemplateResolver;
import org.thymeleaf.web.IWebApplication;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;

public class ThymeleafUtil {

	private static TemplateEngine templateEngine;
	private static boolean initialized = false;

	public static void initialize(ServletContext servletContext, boolean cachable) {
		if (initialized) {
			return;
		}

		try {
			WebApplicationTemplateResolver templateResolver = getWebApplicationTemplateResolver(servletContext,cachable);

			templateEngine = new TemplateEngine();
			templateEngine.setTemplateResolver(templateResolver);

			initialized = true;
		} catch (Exception e) {
			throw new RuntimeException("=====! NixDocs: Failed to initialize Thymeleaf !========\n", e);
		}
	}

    private static WebApplicationTemplateResolver getWebApplicationTemplateResolver(ServletContext servletContext,boolean cachable) {
        IWebApplication webApplication = JakartaServletWebApplication.buildApplication(servletContext);

		WebApplicationTemplateResolver templateResolver = new WebApplicationTemplateResolver(webApplication);
		templateResolver.setTemplateMode(TemplateMode.HTML);
		templateResolver.setPrefix("/template/");
		templateResolver.setSuffix(".html");
		templateResolver.setCharacterEncoding("UTF-8");
		templateResolver.setCacheable(cachable);
		templateResolver.setCacheTTLMs(3600000L);
		return templateResolver;
	}

	public static TemplateEngine getTemplateEngine() {
		if (!initialized) {
			throw new IllegalStateException("======! NixDocs: Thymeleaf not initialized !=========");
		}
		return templateEngine;
	}



	public static void renderTemplate(HttpServletRequest request,
									  HttpServletResponse response,
									  String templateName,
									  Map<String, Object> variables) throws IOException {

		response.setContentType("text/html;charset=UTF-8");

        WebContext context = new WebContext(
            JakartaServletWebApplication.buildApplication(request.getServletContext())
                .buildExchange(request, response),
            request.getLocale()
        );

		if (variables != null) {
			variables.forEach(context::setVariable);
		}
		getTemplateEngine().process(templateName, context, response.getWriter());
	}

	public static String getParsedTemplate (String templateName,WebContext context){
		TemplateEngine templateEngine = getTemplateEngine();
		return templateEngine.process(templateName,context);
	}
}
