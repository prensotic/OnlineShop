package ru.loginov.dev.controllers.clientControllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.loginov.dev.services.CartItemService;
import ru.loginov.dev.services.CartService;
import ru.loginov.dev.services.ProductService;
import ru.loginov.dev.services.UserService;

@Controller
@RequestMapping("/cart")
public class CartController {
    private final CartItemService cartItemService;
    private final CartService cartService;
    private final ProductService productService;
    private final UserService userService;

    public CartController(CartItemService cartItemService,
                          CartService cartService,
                          ProductService productService,
                          UserService userService) {
        this.cartItemService = cartItemService;
        this.cartService = cartService;
        this.productService = productService;
        this.userService = userService;
    }

//    @GetMapping("/{cartId}")
//    private String cartPage(@PathVariable("cartId") long cartId, Model model){
//
//    }
}
