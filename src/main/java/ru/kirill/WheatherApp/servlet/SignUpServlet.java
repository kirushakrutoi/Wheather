package ru.kirill.WheatherApp.servlet;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.hibernate.SessionFactory;
import org.mindrot.jbcrypt.BCrypt;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.web.IWebExchange;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;
import ru.kirill.WheatherApp.dao.SessionDao;
import ru.kirill.WheatherApp.dao.UserDao;
import ru.kirill.WheatherApp.model.Session;
import ru.kirill.WheatherApp.model.User;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

import java.io.IOException;

@WebServlet(urlPatterns = "/signup")
public class SignUpServlet extends CommonServlet {
    private UserDao userDao;
    private SessionDao sessionDao;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        userDao =
                new UserDao((SessionFactory) config.getServletContext().getAttribute("sessionFactory"));
        sessionDao =
                new SessionDao((SessionFactory) config.getServletContext().getAttribute("sessionFactory"));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        WebContext context = createWebContext(req, resp);

        if(!isAuthentication(req, resp)){
            templateEngine.process("sign-up", context, resp.getWriter());
            return;
        }

        resp.sendRedirect("/mainmenu");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        WebContext context = createWebContext(req, resp);

        String email = req.getParameter("email");
        String password = req.getParameter("password");

        if(email == null && password == null){
            templateEngine.process("error", context, resp.getWriter());
            return;
        }

        Optional<User> OUser = userDao.show(email);

        if(OUser.isPresent()){
            templateEngine.process("error", context, resp.getWriter());
            return;
        }

        String salt = BCrypt.gensalt();
        String hashedPassword = BCrypt.hashpw(password, salt);

        User user = new User(email, hashedPassword);
        Session session = new Session(UUID.randomUUID(), new Date(), user);
        user.setSession(session);

        userDao.save(user);

        sendCookieAndRedirect(session, resp);
    }
}
