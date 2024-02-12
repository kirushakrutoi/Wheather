package ru.kirill.WheatherApp.servlet;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.hibernate.SessionFactory;
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.web.IWebExchange;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;
import ru.kirill.WheatherApp.dao.SessionDao;
import ru.kirill.WheatherApp.model.Session;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

public class CommonServlet extends HttpServlet {

    protected ITemplateEngine templateEngine;
    protected SessionFactory sessionFactory;
    private SessionDao sessionDao;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        templateEngine = (ITemplateEngine) config.getServletContext().getAttribute("templateEngine");
        sessionFactory = (SessionFactory) config.getServletContext().getAttribute("sessionFactory");
        sessionDao = new SessionDao(sessionFactory);
    }

    public WebContext createWebContext(HttpServletRequest req, HttpServletResponse resp) {
        IWebExchange webExchange = JakartaServletWebApplication.buildApplication(getServletContext())
                .buildExchange(req, resp);

        return new WebContext(webExchange);
    }

    protected boolean isAuthentication(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String path = req.getRequestURI().substring(req.getContextPath().length());

        Optional<Cookie> OCookie = findCookie(req);

        if(OCookie.isEmpty() && path.startsWith("/signup")) return false;

        if(OCookie.isEmpty() && path.startsWith("/login")) return false;

        if(OCookie.isEmpty()){
            resp.sendRedirect("/login");
            return false;
        }

        Cookie cookie = OCookie.get();
        Optional<Session> OSession = sessionDao.show(UUID.fromString(cookie.getValue()));

        if(OSession.isEmpty()){
            sendNullCookieAndRedirect(resp);
            return false;
        }

        return true;
    }

    protected Optional<Cookie> findCookie(HttpServletRequest req) {
        try {
            Optional<Cookie> OCookie =
                    Arrays.stream(req.getCookies()).filter(cookie1 -> cookie1.getName().equals("sessionId")).findFirst();
            return OCookie;
        } catch (NullPointerException e){
            return Optional.empty();
        }
    }

    protected void sendNullCookieAndRedirect(HttpServletResponse resp) throws IOException {
        Cookie newCookie = new Cookie("sessionId", null);
        newCookie.setMaxAge(0);

        resp.addCookie(newCookie);
        resp.sendRedirect("/login");
    }

    protected void sendCookieAndRedirect(Session session, HttpServletResponse resp) throws IOException {
        Cookie cookie = new Cookie("sessionId", session.getId().toString());
        cookie.setMaxAge(60 * 60 * 24);

        resp.addCookie(cookie);
        resp.sendRedirect("/login");
    }
}
