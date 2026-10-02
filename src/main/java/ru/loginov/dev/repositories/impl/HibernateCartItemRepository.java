package ru.loginov.dev.repositories.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.loginov.dev.entities.CartItem;
import ru.loginov.dev.repositories.CartItemRepository;

import java.util.List;

@Repository
public class HibernateCartItemRepository implements CartItemRepository {
    private final SessionFactory sessionFactory;

    public HibernateCartItemRepository(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }

    @Override
    public CartItem findCartItemById(long id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(CartItem.class, id);
    }

    @Override
    public CartItem findByCartAndProduct(long cartId, long productId) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select c from CartItem c " +
                                "where c.cart.id = :cartId and c.product.id = :productId", CartItem.class)
                .setParameter("cartId", cartId)
                .setParameter("productId", productId)
                .uniqueResult();
    }

    @Override
    public List<CartItem> findByCartId(long cartId) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select c from CartItem c where c.cart.id = :cartId", CartItem.class)
                .setParameter("cartId", cartId)
                .getResultList();
    }

    @Override
    public void saveCartItem(CartItem cartItem) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(cartItem);
    }

    @Override
    public void updateCartItem(CartItem cartItem) {
        Session session = sessionFactory.getCurrentSession();
        session.merge(cartItem);
    }

    @Override
    public void deleteCartItem(long id) {
        Session session = sessionFactory.getCurrentSession();
        CartItem cartItem = findCartItemById(id);
        if(cartItem != null) session.remove(cartItem);
    }
}