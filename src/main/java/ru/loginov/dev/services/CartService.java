package ru.loginov.dev.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.loginov.dev.entities.Cart;
import ru.loginov.dev.exceptions.cartExceptions.CartNotFoundException;
import ru.loginov.dev.repositories.CartRepository;

@Service
public class CartService {
    private final CartRepository cartRepository;

    public CartService(CartRepository cartRepository){
        this.cartRepository = cartRepository;
    }

    @Transactional
    public Cart findCartByUserId(long userId){
        if(userId <= 0) throw new IllegalArgumentException("ID должен быть положительным");

        Cart cart = cartRepository.findCartByUserId(userId);

        if(cart == null) throw new CartNotFoundException("Корзина пользователя с ID: " + userId + " не найдена");

        return cart;
    }
}