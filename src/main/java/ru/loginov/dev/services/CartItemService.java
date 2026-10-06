package ru.loginov.dev.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.loginov.dev.entities.Cart;
import ru.loginov.dev.entities.CartItem;
import ru.loginov.dev.entities.Product;
import ru.loginov.dev.exceptions.cartExceptions.CartItemNotFoundException;
import ru.loginov.dev.repositories.CartItemRepository;

import java.util.List;

@Service
public class CartItemService {
    private final CartItemRepository cartItemRepository;

    public CartItemService(CartItemRepository cartItemRepository){
        this.cartItemRepository = cartItemRepository;
    }

    @Transactional
    public CartItem findCartItemById(long id){
        if(id <= 0) throw new IllegalArgumentException("ID должен быть положительным");

        CartItem cartItem = cartItemRepository.findCartItemById(id);

        if(cartItem == null)
            throw new CartItemNotFoundException("Товара корзины с ID: " + id + " не существует");

        return cartItem;
    }

    @Transactional
    public List<CartItem> findCartItemsByCartId(long cartId){
        if(cartId <= 0) throw new IllegalArgumentException("ID должен быть положительным");
        return cartItemRepository.findCartItemsByCartId(cartId);
    }

    @Transactional
    public CartItem findCartItemByCartAndProduct(long cartId, long productId){
        if(cartId <= 0) throw new IllegalArgumentException("ID корзины должен быть положительным");

        if(productId <= 0) throw new IllegalArgumentException("ID товара должен быть положительным");

        CartItem cartItem = cartItemRepository.findByCartAndProduct(cartId, productId);

        if(cartItem == null)
            throw new CartItemNotFoundException("В корзине с ID: " + cartId + " нет товара с ID: " + productId);

        return cartItem;
    }

    @Transactional
    public void addProductToCart(Cart cart, Product product, int quantity){
        if(cart == null) throw new IllegalArgumentException("Корзина указана некорректно");

        if(product == null) throw new IllegalArgumentException("Товар указан некорректно");

        if(quantity <= 0) throw new IllegalArgumentException("Количество товаров должно быть положительным");

        CartItem existing = cartItemRepository.findByCartAndProduct(cart.getId(), product.getId());

        if (existing != null) {
            existing.setQuantity(existing.getQuantity() + quantity);
            return;
        }

        cartItemRepository.saveCartItem(new CartItem(cart, product, quantity));
    }

    @Transactional
    public void changeQuantity(long id, int quantity){
        CartItem cartItem = findCartItemById(id);

        if(quantity <= 0) throw new IllegalArgumentException("Количество товаров должно быть положительным");

        cartItem.setQuantity(quantity);
    }

    @Transactional
    public void removeProductFromCart(long id){
        findCartItemById(id);
        cartItemRepository.deleteCartItem(id);
    }

    @Transactional
    public void clearCart(long cartId) {
        if (cartId <= 0)
            throw new IllegalArgumentException("ID корзины должен быть положительным");

        cartItemRepository.deleteAllByCartId(cartId);
    }
}