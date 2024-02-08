package ru.kirill.WheatherApp.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.thymeleaf.context.WebContext;

import java.io.IOException;

@WebServlet(urlPatterns = "/mainmenu")
public class MainMenuServlet extends CommonServlet{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        WebContext context = createWebContext(req, resp);

        if(isAuthentication(req, resp)) {
            templateEngine.process("mainmenu", context, resp.getWriter());
            return;
        }

        //resp.sendRedirect("/login");
    }
}
