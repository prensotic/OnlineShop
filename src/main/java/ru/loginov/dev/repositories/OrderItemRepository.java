package ru.loginov.dev.repositories;

import ru.loginov.dev.entities.OrderItem;

import java.util.List;

public interface OrderItemRepository {
    List<OrderItem> findOrderItemsByOrderId(long orderId);
    OrderItem findOrderItemByOrderIdAndProductId(long orderId, long productId);
    OrderItem findOrderItemById(long id);
    void saveOrderItem(OrderItem orderItem);
    void updateOrderItem(OrderItem orderItem);
    void deleteOrderItem(long id);
}
