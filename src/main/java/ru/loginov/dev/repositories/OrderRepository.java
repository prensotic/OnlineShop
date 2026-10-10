package ru.loginov.dev.repositories;

import ru.loginov.dev.entities.Order;

import java.util.List;

public interface OrderRepository {
    List<Order> getAllOrders();
    List<Order> findOrdersByUserId(long userId);
    Order findOrderById(long id);
    void saveOrder(Order order);
    void updateOrder(Order order);
    void deleteOrder(long id);
    List<Order> findOrdersWithItemsByUserId(long userId);
}
