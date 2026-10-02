package ru.loginov.dev.repositories;

import ru.loginov.dev.entities.Cart;

public interface CartRepository {
    public Cart findCartByUserId(long userId);
}
