package ru.loginov.dev.exceptions.orderExceptions;

public class OrderItemNotFoundExtension extends RuntimeException {
    public OrderItemNotFoundExtension(String message) {
        super(message);
    }
}
