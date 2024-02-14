package ru.kirill.WheatherApp.dao;

import jakarta.persistence.NoResultException;
import jakarta.transaction.Transactional;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import ru.kirill.WheatherApp.model.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class UserDao {

    private final SessionFactory sessionFactory;

    public UserDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    public List<User> index() {
        try(Session session = sessionFactory.getCurrentSession()) {
            //session.beginTransaction();

            return session.createQuery("select u from User u", User.class).getResultList();
        }
    }

    public Optional<User> show(String email){
        try(Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();

            Query<User> queryUser = session.createQuery("FROM User where email = :email", User.class);

            queryUser.setParameter("email", email);
            User user = queryUser.getSingleResult();

            return Optional.of(queryUser.getSingleResult());
        } catch (NoResultException e){
            return Optional.empty();
        }
    }

    public void save(User user) {
        try(Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();

            session.persist(user);
            session.persist(user.getSession());

            session.flush();

            session.getTransaction().commit();
        }
    }

    public void update(int id, User user){
        try(Session session = sessionFactory.getCurrentSession()){
            session.beginTransaction();

            session.persist(user);
            session.flush();

            session.getTransaction().commit();
        }
    }

    public void delete(int id){
        try (Session session = sessionFactory.getCurrentSession()){
            User user = session.get(User.class, id);

            session.remove(user);
            session.flush();

            session.getTransaction().commit();
        }
    }

    public void test(){
        System.out.println("test");
    }
}
