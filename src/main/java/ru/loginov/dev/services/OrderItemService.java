package ru.loginov.dev.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.loginov.dev.entities.Order;
import ru.loginov.dev.entities.OrderItem;
import ru.loginov.dev.entities.Product;
import ru.loginov.dev.exceptions.cartExceptions.CartItemNotFoundException;
import ru.loginov.dev.exceptions.orderExceptions.OrderItemNotFoundExtension;
import ru.loginov.dev.repositories.OrderItemRepository;

import java.util.List;

@Service
public class OrderItemService {
    private final OrderItemRepository orderItemRepository;

    public OrderItemService(OrderItemRepository orderItemRepository){
        this.orderItemRepository = orderItemRepository;
    }

    @Transactional
    public OrderItem findOrderItemById(long id){
        if(id <= 0) throw new IllegalArgumentException("ID должен быть положительным");

        OrderItem orderItem = orderItemRepository.findOrderItemById(id);

        if(orderItem == null)
            throw new OrderItemNotFoundExtension("Товара заказа с ID: " + id + " не существует");

        return orderItem;
    }

    @Transactional
    public List<OrderItem> findOrderItemsByOrderId(long orderId){
        if(orderId <= 0) throw new IllegalArgumentException("ID должен быть положительным");
        return orderItemRepository.findOrderItemsByOrderId(orderId);
    }

    @Transactional
    public OrderItem findOrderItemByOrderAndProduct(long orderId, long productId){
        if(orderId <= 0) throw new IllegalArgumentException("ID заказа должен быть положительным");

        if(productId <= 0) throw new IllegalArgumentException("ID товара должен быть положительным");

        OrderItem orderItem = orderItemRepository.findOrderItemByOrderIdAndProductId(orderId, productId);

        if(orderItem == null)
            throw new OrderItemNotFoundExtension("В заказе с ID: " + orderId + " нет товара с ID: " + productId);

        return orderItem;
    }

    @Transactional
    public void addProductToOrder(Order order, Product product, int quantity){
        if(order == null) throw new IllegalArgumentException("Заказ указан некорректно");

        if(product == null) throw new IllegalArgumentException("Товар указан некорректно");

        if(quantity <= 0) throw new IllegalArgumentException("Количество товаров должно быть положительным");

        OrderItem existing = orderItemRepository.findOrderItemByOrderIdAndProductId(order.getId(), product.getId());

        if (!"PENDING".equals(order.getStatus()))
            throw new IllegalStateException("Нельзя изменить состав заказа");

        if (existing != null) {
            existing.setQuantity(existing.getQuantity() + quantity);
            return;
        }

        orderItemRepository.saveOrderItem(new OrderItem(order, product, quantity));
    }

    @Transactional
    public void changeQuantity(long id, int quantity) {
        OrderItem orderItem = findOrderItemById(id);

        if (!"PENDING".equals(orderItem.getOrder().getStatus()))
            throw new IllegalStateException("Нельзя изменить состав заказа");

        if (quantity <= 0)
            throw new IllegalArgumentException("Количество товаров должно быть положительным");

        orderItem.setQuantity(quantity);
    }

    @Transactional
    public void removeProductFromOrder(long id) {
        OrderItem orderItem = findOrderItemById(id);

        if (!"PENDING".equals(orderItem.getOrder().getStatus()))
            throw new IllegalStateException("Нельзя изменить состав заказа");

        orderItemRepository.deleteOrderItem(id);
    }
}
