package ru.loginov.dev.repositories;

import ru.loginov.dev.entities.Cart;

public interface CartRepository {
    Cart findCartByUserId(long userId);
    void addCartToUser(Cart cart);
}
