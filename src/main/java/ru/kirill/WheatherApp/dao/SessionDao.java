package ru.kirill.WheatherApp.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ru.kirill.WheatherApp.model.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class SessionDao {

    private final SessionFactory sessionFactory;

    public SessionDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public List<ru.kirill.WheatherApp.model.Session> index() {
        try(Session session = sessionFactory.getCurrentSession()) {
            //session.beginTransaction();

            return session.createQuery("select s from Session s", ru.kirill.WheatherApp.model.Session.class).getResultList();
        }
    }

    public Optional<ru.kirill.WheatherApp.model.Session> show(UUID id){
        try(Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();

            return Optional.ofNullable(session.get(ru.kirill.WheatherApp.model.Session.class, id));
        }
    }

    public void save(ru.kirill.WheatherApp.model.Session userSession) {
        try(Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();

            session.persist(userSession);

            //session.flush();

            session.getTransaction().commit();
        }
    }

    public void update(UUID id, ru.kirill.WheatherApp.model.Session userSession){
        try(Session session = sessionFactory.getCurrentSession()){
            session.beginTransaction();

            session.persist(userSession);
            session.flush();

            session.getTransaction().commit();
        }
    }

    public void delete(UUID id){
        try (Session session = sessionFactory.getCurrentSession()){
            session.beginTransaction();
            ru.kirill.WheatherApp.model.Session userSession = session.get(ru.kirill.WheatherApp.model.Session.class, id);

            session.remove(userSession);
            session.flush();

            session.getTransaction().commit();
        }
    }
}
