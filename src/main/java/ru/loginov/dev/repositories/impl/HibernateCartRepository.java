package ru.loginov.dev.repositories.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.loginov.dev.entities.Cart;
import ru.loginov.dev.repositories.CartRepository;

@Repository
public class HibernateCartRepository implements CartRepository {
    private final SessionFactory sessionFactory;

    public HibernateCartRepository(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Cart findCartByUserId(long userId) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select c from Cart c where c.user.id = :userId", Cart.class)
                .setParameter("userId", userId)
                .getSingleResult();
    }
}
