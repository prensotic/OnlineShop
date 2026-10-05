package ru.loginov.dev.exceptions.productExceptions;

public class ProductNotFoundExceptions extends RuntimeException {
    public ProductNotFoundExceptions(String message) {
        super(message);
    }
}
