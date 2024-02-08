package ru.kirill.WheatherApp.servlet;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.hibernate.SessionFactory;
import ru.kirill.WheatherApp.dao.SessionDao;
import ru.kirill.WheatherApp.model.Session;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@WebServlet(urlPatterns = "/logout")
public class LogOutServlet extends CommonServlet{
    private SessionDao sessionDao;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        sessionDao = new SessionDao((SessionFactory) config.getServletContext().getAttribute("sessionFactory"));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Optional<Cookie> OCookie = findCookie(req);

        Optional<Session> deleteSession = sessionDao.show(UUID.fromString(OCookie.get().getValue()));

        sessionDao.delete(deleteSession.get().getId());

        Cookie cookie = new Cookie("sessionId", null);
        cookie.setMaxAge(0);

        resp.addCookie(cookie);

        resp.sendRedirect("/login");
    }
}
