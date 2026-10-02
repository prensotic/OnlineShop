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
        return sessionFactory.getCurrentSession().createQuery("select u from UserPersonalData u", UserPersonalData.class).getResultList();
    }

    @Override
    public UserPersonalData findUserPersonalDataById(long id) {
        return sessionFactory.getCurrentSession().get(UserPersonalData.class, id);
    }

    @Override
    public UserPersonalData findUserPersonalDataByUserId(long userId) {
        return sessionFactory.getCurrentSession().createQuery("select u from UserPersonalData u where u.user.id = :userId", UserPersonalData.class)
                .setParameter("userId", userId)
                .uniqueResult();
    }

    @Override
    public void saveUserPersonalData(UserPersonalData userPersonalData) {
        sessionFactory.getCurrentSession().persist(userPersonalData);
    }

    @Override
    public void updateUserPersonalData(UserPersonalData userPersonalData) {
        sessionFactory.getCurrentSession().merge(userPersonalData);
    }

    @Override
    public void deleteUserPersonalData(long id) {
        UserPersonalData userPersonalData = findUserPersonalDataById(id);
        if(userPersonalData != null) sessionFactory.getCurrentSession().remove(userPersonalData);
    }
}
