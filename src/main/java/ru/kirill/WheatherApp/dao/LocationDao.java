package ru.kirill.WheatherApp.dao;

import org.hibernate.SessionFactory;
import ru.kirill.WheatherApp.model.Location;
import ru.kirill.WheatherApp.model.Session;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class LocationDao {
    private final SessionFactory sessionFactory;

    public LocationDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public List<Location> index() {
        try(org.hibernate.Session session = sessionFactory.getCurrentSession()) {
            //session.beginTransaction();

            return session.createQuery("select l from Location s", Location.class).getResultList();
        }
    }

    public Optional<Location> show(int id){
        try(org.hibernate.Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();

            return Optional.ofNullable(session.get(Location.class, id));
        }
    }

    public void save(Location location) {
        try(org.hibernate.Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();

            session.persist(location);

            //session.flush();

            session.getTransaction().commit();
        }
    }

    public void update(int id, Location location){
        try(org.hibernate.Session session = sessionFactory.getCurrentSession()){
            session.beginTransaction();

            session.persist(location);
            session.flush();

            session.getTransaction().commit();
        }
    }

    public void delete(int id){
        try (org.hibernate.Session session = sessionFactory.getCurrentSession()){
            session.beginTransaction();
            Location userSession = session.get(Location.class, id);

            session.remove(userSession);
            session.flush();

            session.getTransaction().commit();
        }
    }
}
