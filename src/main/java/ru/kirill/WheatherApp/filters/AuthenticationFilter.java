package ru.kirill.WheatherApp.filters;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.hibernate.SessionFactory;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.web.IWebExchange;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;
import ru.kirill.WheatherApp.dao.SessionDao;
import ru.kirill.WheatherApp.model.Session;

import java.io.IOException;
import java.net.http.HttpRequest;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

/*@WebFilter("/*")*/
public class AuthenticationFilter /*implements Filter*/ {
/*    private SessionDao sessionDao;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
        sessionDao = new SessionDao((SessionFactory) filterConfig.getServletContext().getAttribute("sessionFactory"));
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;

        String path = req.getRequestURI().substring(req.getContextPath().length());

        Optional<Cookie> cookie = Optional.empty();

        try {
            cookie = Arrays.stream(req.getCookies()).filter(cookie1 -> cookie1.getName().equals("sessionId")).findFirst();
        } catch (NullPointerException e){
            if(path.startsWith("/signup")){
                filterChain.doFilter(servletRequest, servletResponse);
                return;
            }
            resp.sendRedirect("/login");
            filterChain.doFilter(servletRequest, servletResponse);
        }

        Optional<Session> OSession = sessionDao.show(UUID.fromString(cookie.get().getValue()));

        if(OSession.isPresent()){
            Session session = OSession.get();
            if(path.)
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }


        if(cookie.isEmpty() && path.startsWith("/signup")){
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        if(cookie.isEmpty()){
            resp.sendRedirect("/login");
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }



        resp.sendRedirect("/login");
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }*/
}
