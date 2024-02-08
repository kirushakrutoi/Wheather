package ru.kirill.WheatherApp.listener;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.hibernate.SessionFactory;
import org.thymeleaf.ITemplateEngine;
import ru.kirill.WheatherApp.model.Session;
import ru.kirill.WheatherApp.util.CreateSessionFactory;
import ru.kirill.WheatherApp.util.ThymeleafConfiguration;

@WebListener
public class ContextListener implements ServletContextListener {

    private SessionFactory sessionFactory;
    private ITemplateEngine templateEngine;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        sessionFactory = CreateSessionFactory.getSessionFactory();
        templateEngine = ThymeleafConfiguration.getTemplateEngine(sce);

        sce.getServletContext().setAttribute("templateEngine", templateEngine);
        sce.getServletContext().setAttribute("sessionFactory", sessionFactory);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
