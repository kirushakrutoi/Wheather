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
import ru.kirill.WheatherApp.dao.SessionDao;
import ru.kirill.WheatherApp.dao.UserDao;
import ru.kirill.WheatherApp.model.Session;
import ru.kirill.WheatherApp.model.User;

import java.io.IOException;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@WebServlet(urlPatterns = "/login")
public class SignInServlet extends CommonServlet{
    private UserDao userDao;
    private SessionDao sessionDao;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        sessionFactory = (SessionFactory) config.getServletContext().getAttribute("sessionFactory");
        userDao = new UserDao(sessionFactory);
        sessionDao = new SessionDao(sessionFactory);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        WebContext context = createWebContext(req, resp);

        if(!isAuthentication(req, resp)){
            templateEngine.process("sign-in", context, resp.getWriter());
            return;
        }

        resp.sendRedirect("/mainmenu");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        WebContext context = createWebContext(req, resp);

        String email = req.getParameter("email");
        String password = req.getParameter("password");

        if(email == null || password == null){
            templateEngine.process("error", context, resp.getWriter());
            return;
        }

        Optional<User> OUser = userDao.show(email);

        if(OUser.isEmpty()){
            templateEngine.process("error", context, resp.getWriter());
            return;
        }

        User user = OUser.get();
        if(user.getSession() == null) {

            if(!BCrypt.checkpw(password, user.getPassword())){
                templateEngine.process("error", context, resp.getWriter());
                return;
            }

            Session session = new Session(UUID.randomUUID(), new Date(), user);
            sessionDao.save(session);

            Cookie cookie = new Cookie("sessionId", session.getId().toString());
            cookie.setMaxAge(60 * 60 * 24);

            resp.addCookie(cookie);
            resp.sendRedirect("/mainmenu");
            return;
        }

        if(!BCrypt.checkpw(password, user.getPassword())){
            templateEngine.process("error", context, resp.getWriter());
            return;
        }

        Cookie cookie = new Cookie("sessionId", user.getSession().getId().toString());
        cookie.setMaxAge(60 * 60 * 24);

        resp.addCookie(cookie);
        resp.sendRedirect("/mainmenu");
    }
}
