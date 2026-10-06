package ru.loginov.dev.repositories;

import ru.loginov.dev.entities.CartItem;

import java.util.List;

public interface CartItemRepository {
    CartItem findCartItemById(long id);
    CartItem findByCartAndProduct(long cartId, long productId);
    List<CartItem> findCartItemsByCartId(long cartId);
    void saveCartItem(CartItem cartItem);
    void updateCartItem(CartItem cartItem);
    void deleteCartItem(long id);
    void deleteAllByCartId(long cartId);
}
