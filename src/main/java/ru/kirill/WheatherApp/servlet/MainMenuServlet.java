package ru.kirill.WheatherApp.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.thymeleaf.context.WebContext;
import ru.kirill.WheatherApp.model.Location;
import ru.kirill.WheatherApp.model.Session;
import ru.kirill.WheatherApp.model.User;
import ru.kirill.WheatherApp.service.WheatherService;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

@WebServlet(urlPatterns = "/mainmenu")
public class MainMenuServlet extends CommonServlet{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        WebContext context = createWebContext(req, resp);

        if(isAuthentication(req, resp)) {
            WheatherService wheatherService = new WheatherService();
            //wheatherService.test();
            User user = session.getUser();
            List<Location> locations = user.getLocations();
            context.setVariable("locations", wheatherService.getWeatherByCoords(locations));
            templateEngine.process("mainmenu", context, resp.getWriter());
            return;
        }

        //resp.sendRedirect("/login");
    }
}
