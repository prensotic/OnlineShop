package ru.loginov.dev.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.loginov.dev.entities.Order;
import ru.loginov.dev.entities.User;
import ru.loginov.dev.exceptions.orderExceptions.OrderNotFoundException;
import ru.loginov.dev.repositories.OrderRepository;

import java.math.BigDecimal;
import java.util.List;

@Service
public class OrderService {
    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository){
        this.orderRepository = orderRepository;
    }

    @Transactional
    public List<Order> getAllOrders(){
        return orderRepository.getAllOrders();
    }

    @Transactional
    public List<Order> findOrdersByUserId(long userId){
        if(userId <= 0) throw new IllegalArgumentException("ID пользователя должен быть положительным");
        return orderRepository.findOrdersByUserId(userId);
    }

    @Transactional
    public Order findOrderById(long id){
        if(id <= 0) throw new IllegalArgumentException("ID должен быть положительным");

        Order order = orderRepository.findOrderById(id);

        if(order == null) throw new OrderNotFoundException("Заказ с ID: " + id + " не найден");

        return order;
    }

    @Transactional
    public void createOrder(User user, BigDecimal amount){
        if(user == null) throw new IllegalArgumentException("Пользователь указан некорректно");

        if(amount == null || amount.compareTo(BigDecimal.ZERO) <= 0)
            throw new IllegalArgumentException("Сумма заказа должна быть положительной");

        orderRepository.saveOrder(new Order(user, amount));
    }

    @Transactional
    public void markOrderAsConfirmed(long id){
        Order order = findOrderById(id);

        if(!"PENDING".equals(order.getStatus()))
            throw new IllegalStateException("Заказ нельзя подтвердить");

        order.setStatus("CONFIRMED");
    }

    @Transactional
    public void markOrderAsProcessing(long id){
        Order order = findOrderById(id);

        if(!"CONFIRMED".equals(order.getStatus()))
            throw new IllegalStateException("Заказ нельзя перевести в обработку");

        order.setStatus("PROCESSING");
    }

    @Transactional
    public void markOrderAsShipped(long id){
        Order order = findOrderById(id);

        if(!"PROCESSING".equals(order.getStatus()))
            throw new IllegalStateException("Заказ нельзя передать в доставку");

        order.setStatus("SHIPPED");
    }

    @Transactional
    public void markOrderAsDelivered(long id){
        Order order = findOrderById(id);

        if(!"SHIPPED".equals(order.getStatus()))
            throw new IllegalStateException("Заказ нельзя отметить как доставленный");

        order.setStatus("DELIVERED");
    }

    @Transactional
    public void markOrderAsCancelled(long id){
        Order order = findOrderById(id);

        if(!"PENDING".equals(order.getStatus()))
            throw new IllegalArgumentException("Заказ нельзя отменить");

        order.setStatus("CANCELLED");
    }

    @Transactional
    public void deleteOrder(long id){
        findOrderById(id);
        orderRepository.deleteOrder(id);
    }
}