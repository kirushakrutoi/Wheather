package ru.kirill.WheatherApp.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import ru.kirill.WheatherApp.model.Location;
import ru.kirill.WheatherApp.model.Session;
import ru.kirill.WheatherApp.model.User;

public class CreateSessionFactory {

    private final static SessionFactory sessionFactory;

    static {
        Configuration configuration = new Configuration().addAnnotatedClass(User.class)
                .addAnnotatedClass(Location.class)
                .addAnnotatedClass(Session.class);

        sessionFactory = configuration.buildSessionFactory();
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
