package ru.kirill.WheatherApp.util;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.WebApplicationTemplateResolver;
import org.thymeleaf.web.IWebApplication;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;

public class ThymeleafConfiguration {

    private static ITemplateEngine templateEngine;

    private static JakartaServletWebApplication application;

    private static ITemplateEngine templateEngine(IWebApplication application) {
        TemplateEngine templateEngine = new TemplateEngine();

        WebApplicationTemplateResolver templateResolver = templateResolver(application);
        templateEngine.setTemplateResolver(templateResolver);

        return templateEngine;
    }

    private static WebApplicationTemplateResolver templateResolver(IWebApplication application) {
        WebApplicationTemplateResolver templateResolver = new WebApplicationTemplateResolver(application);

        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setPrefix("/WEB-INF/templates/");
        templateResolver.setSuffix(".html");

        templateResolver.setCacheTTLMs(Long.valueOf(3600000L));

        templateResolver.setCacheable(true);

        return templateResolver;
    }

    public static ITemplateEngine getTemplateEngine(ServletContextEvent sce){
        application = JakartaServletWebApplication.buildApplication(sce.getServletContext());
        templateEngine = templateEngine(application);
        return templateEngine;
    }

}
