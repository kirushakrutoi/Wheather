package ru.kirill.WheatherApp.servlet;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.thymeleaf.context.WebContext;
import ru.kirill.WheatherApp.dao.LocationDao;
import ru.kirill.WheatherApp.dao.SessionDao;
import ru.kirill.WheatherApp.dao.UserDao;
import ru.kirill.WheatherApp.model.Location;
import ru.kirill.WheatherApp.model.User;
import ru.kirill.WheatherApp.model.dto.location.LocationDto;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Optional;

@WebServlet(urlPatterns = "/search")
public class SearchLocationServlet extends CommonServlet{
    private LocationDao locationDao;
    private UserDao userDao;
    private SessionDao sessionDao;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        locationDao = new LocationDao(sessionFactory);
        userDao = new UserDao(sessionFactory);
        sessionDao = new SessionDao(sessionFactory);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(isAuthentication(req, resp)) {
            WebContext context = createWebContext(req, resp);

            String cityName = req.getParameter("city");

            Optional<LocationDto> OLocationDto = wheatherService.getWeatherByCityName(cityName);

            if(OLocationDto.isEmpty()){
                resp.sendRedirect("error");
            }

            LocationDto locationDto = OLocationDto.get();
            context.setVariable("weather", locationDto);

            templateEngine.process("weather", context, resp.getWriter());

            return;
        }

        resp.sendRedirect("/login");

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String lonString = req.getParameter("lon");
        String latString = req.getParameter("lat");
        String name = req.getParameter("name");

        BigDecimal lon = new BigDecimal(lonString, new MathContext(12, RoundingMode.HALF_UP));
        BigDecimal lat = new BigDecimal(latString, new MathContext(12, RoundingMode.HALF_UP));
        User user = session.getUser();

        Location location = new Location(name, lat, lon, user);

        System.out.println(lon + " " + lat);
        locationDao.save(location);

        resp.sendRedirect("/mainmenu");
    }
}
