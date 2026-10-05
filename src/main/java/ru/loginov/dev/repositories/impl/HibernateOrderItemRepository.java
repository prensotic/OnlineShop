package ru.loginov.dev.repositories.impl;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.loginov.dev.entities.OrderItem;
import ru.loginov.dev.repositories.OrderItemRepository;

import java.util.List;

@Repository
public class HibernateOrderItemRepository implements OrderItemRepository {
    private final SessionFactory sessionFactory;

    public HibernateOrderItemRepository(SessionFactory sessionFactory){
        this.sessionFactory =  sessionFactory;
    }
    @Override
    public List<OrderItem> findOrderItemsByOrderId(long orderId) {
        return sessionFactory
                .getCurrentSession()
                .createQuery("select o from OrderItem o where o.order.id = :orderId", OrderItem.class)
                .setParameter("orderId", orderId)
                .getResultList();
    }

    @Override
    public OrderItem findOrderItemByOrderIdAndProductId(long orderId, long productId) {
        return sessionFactory
                .getCurrentSession()
                .createQuery(
                        "select o from OrderItem o where o.order.id = :orderId and o.product.id = :productId",
                        OrderItem.class)
                .setParameter("orderId", orderId)
                .setParameter("productId", productId)
                .uniqueResult();
    }

    @Override
    public OrderItem findOrderItemById(long id) {
        return sessionFactory.getCurrentSession().get(OrderItem.class, id);
    }

    @Override
    public void saveOrderItem(OrderItem orderItem) {
        sessionFactory.getCurrentSession().persist(orderItem);
    }

    @Override
    public void updateOrderItem(OrderItem orderItem) {
        sessionFactory.getCurrentSession().merge(orderItem);
    }

    @Override
    public void deleteOrderItem(long id) {
        OrderItem orderItem = findOrderItemById(id);
        if(orderItem != null) sessionFactory.getCurrentSession().remove(orderItem);
    }
}
