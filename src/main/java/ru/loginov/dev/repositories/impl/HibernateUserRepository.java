package ru.loginov.dev.repositories.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.loginov.dev.entities.User;
import ru.loginov.dev.repositories.UserRepository;

import java.util.List;

@Repository
public class HibernateUserRepository implements UserRepository {
    private final SessionFactory sessionFactory;

    public HibernateUserRepository(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<User> getAllUsers(){
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select u from User u", User.class).getResultList();
    }

    @Override
    public User findUserById(long id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(User.class, id);
    }

    @Override
    public User findUserByEmail(String email) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select u from User u where u.email = :email", User.class)
                .setParameter("email", email)
                .uniqueResult();
    }

    @Override
    public User findByUsername(String username) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select u from User u where u.username = :username", User.class)
                .setParameter("username", username)
                .uniqueResult();
    }

    @Override
    public void saveUser(User user) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(user);
    }

    @Override
    public void updateUser(User user) {
        Session session = sessionFactory.getCurrentSession();
        session.merge(user);
    }

    @Override
    public void deleteUser(long id) {
        Session session = sessionFactory.getCurrentSession();
        User user = findUserById(id);
        if(user != null) session.remove(user);
    }
}
