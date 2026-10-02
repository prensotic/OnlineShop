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
        return sessionFactory.getCurrentSession().createQuery("select u from User u", User.class).getResultList();
    }

    @Override
    public User findUserById(long id) {
        return sessionFactory.getCurrentSession().get(User.class, id);
    }

    @Override
    public User findUserByEmail(String email) {
        return sessionFactory.getCurrentSession().createQuery("select u from User u where u.email = :email", User.class)
                .setParameter("email", email)
                .uniqueResult();
    }

    @Override
    public User findByUsername(String username) {
        return sessionFactory.getCurrentSession().createQuery("select u from User u where u.username = :username", User.class)
                .setParameter("username", username)
                .uniqueResult();
    }

    @Override
    public void saveUser(User user) {
        sessionFactory.getCurrentSession().persist(user);
    }

    @Override
    public void updateUser(User user) {
        sessionFactory.getCurrentSession().merge(user);
    }

    @Override
    public void deleteUser(long id) {
        User user = findUserById(id);
        if(user != null) sessionFactory.getCurrentSession().remove(user);
    }
}
