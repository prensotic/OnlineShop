package ru.loginov.dev.controllers.clientControllers;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.loginov.dev.entities.Cart;
import ru.loginov.dev.entities.CartItem;
import ru.loginov.dev.entities.User;
import ru.loginov.dev.services.CartItemService;
import ru.loginov.dev.services.CartService;
import ru.loginov.dev.services.ProductService;
import ru.loginov.dev.services.UserService;

@Controller
@RequestMapping("/")
public class CartController {
    private final UserService userService;
    private final CartItemService cartItemService;
    private final CartService cartService;

    public CartController(UserService userService,
                          CartItemService cartItemService,
                          CartService cartService) {
        this.userService = userService;
        this.cartItemService = cartItemService;
        this.cartService = cartService;
    }

    @GetMapping("/cart")
    public String cartPage(Authentication authentication, Model model) {
        User user = userService.findUserByUsername(authentication.getName());
        Cart cart = cartService.findCartByUserId(user.getId());
        model.addAttribute("cartItems", cartItemService.findCartItemsByCartId(cart.getId()));
        return "client/cart";
    }

    @PostMapping("/cart/add-quantity/{productId}")
    public String addQuantity(Authentication authentication, @PathVariable("productId") long productId){
        User user = userService.findUserByUsername(authentication.getName());
        Cart cart = cartService.findCartByUserId(user.getId());
        CartItem cartItem = cartItemService.findCartItemByCartAndProduct(cart.getId(), productId);
        cartItemService.changeQuantity(cartItem.getId(), cartItem.getQuantity() + 1);
        return "redirect:/cart";
    }

    @PostMapping("/cart/reduce-quantity/{productId}")
    public String reduceQuantity(Authentication authentication, @PathVariable("productId") long productId){
        User user = userService.findUserByUsername(authentication.getName());
        Cart cart = cartService.findCartByUserId(user.getId());
        CartItem cartItem = cartItemService.findCartItemByCartAndProduct(cart.getId(), productId);

        if (cartItem.getQuantity() > 1)
            cartItemService.changeQuantity(cartItem.getId(), cartItem.getQuantity() - 1);
        else
            cartItemService.removeProductFromCart(cartItem.getId());

        return "redirect:/cart";
    }
}
