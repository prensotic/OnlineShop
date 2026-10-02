package ru.loginov.dev.repositories.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.loginov.dev.repositories.UserPersonalData;

import java.util.List;

@Repository
public class HibernateUserPersonalData implements UserPersonalData {
    private final SessionFactory sessionFactory;

    public HibernateUserPersonalData(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<UserPersonalData> getAllUsersPersonalData() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select u from UserPersonalData u", UserPersonalData.class).list();
    }

    @Override
    public UserPersonalData findUserPersonalDataById(long id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(UserPersonalData.class, id);
    }

    @Override
    public UserPersonalData findUserPersonalDataByUserId(long userId) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select u from UserPersonalData u where u.user.id = :userId", UserPersonalData.class)
                .setParameter("userId", userId)
                .getSingleResult();
    }

    @Override
    public void saveUserPersonalData(UserPersonalData userPersonalData) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(userPersonalData);
    }

    @Override
    public void updateUserPersonalData(UserPersonalData userPersonalData) {
        Session session = sessionFactory.getCurrentSession();
        session.merge(userPersonalData);
    }

    @Override
    public void deleteUserPersonalData(long id) {
        Session session = sessionFactory.getCurrentSession();
        UserPersonalData userPersonalData = findUserPersonalDataById(id);
        if(userPersonalData != null) session.remove(userPersonalData);
    }
}
