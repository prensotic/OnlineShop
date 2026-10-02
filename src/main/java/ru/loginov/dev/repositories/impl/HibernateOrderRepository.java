package ru.loginov.dev.repositories.impl;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.loginov.dev.entities.Order;
import ru.loginov.dev.repositories.OrderRepository;

import java.util.List;

@Repository
public class HibernateOrderRepository implements OrderRepository {
    private final SessionFactory sessionFactory;

    public HibernateOrderRepository(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }
    @Override
    public List<Order> getAllOrders() {
        return sessionFactory
                .getCurrentSession()
                .createQuery("select o from Order o", Order.class)
                .getResultList();
    }

    @Override
    public List<Order> findOrdersByUserId(long userId) {
        return sessionFactory
                .getCurrentSession()
                .createQuery("select o from Order o where o.user.id = :userId", Order.class)
                .setParameter("userId", userId)
                .getResultList();
    }

    @Override
    public Order findOrderById(long id) {
        return sessionFactory.getCurrentSession().get(Order.class, id);
    }

    @Override
    public void saveOrder(Order order) {
        sessionFactory.getCurrentSession().persist(order);
    }

    @Override
    public void updateOrder(Order order) {
        sessionFactory.getCurrentSession().merge(order);
    }

    @Override
    public void deleteOrder(long id) {
        Order order = findOrderById(id);
        if(order != null) sessionFactory.getCurrentSession().remove(order);
    }
}
