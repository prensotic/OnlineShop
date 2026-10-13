package ru.loginov.dev.controllers.clientControllers;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.loginov.dev.entities.Cart;
import ru.loginov.dev.entities.CartItem;
import ru.loginov.dev.entities.Product;
import ru.loginov.dev.entities.User;
import ru.loginov.dev.services.CartItemService;
import ru.loginov.dev.services.CartService;
import ru.loginov.dev.services.ProductService;
import ru.loginov.dev.services.UserService;

import java.util.Collections;

@Controller
public class IndexController {
    private final ProductService productService;
    private final CartService cartService;
    private final CartItemService cartItemService;
    private final UserService userService;

    public IndexController(ProductService productService,
                           CartService cartService,
                           CartItemService cartItemService,
                           UserService userService){
        this.productService = productService;
        this.cartService = cartService;
        this.cartItemService = cartItemService;
        this.userService = userService;
    }

    @GetMapping("/")
    public String indexPage(Authentication authentication, Model model) {
        model.addAttribute("authentication", authentication);
        model.addAttribute("products", productService.getAllProducts());
        return "client/index";
    }

    @GetMapping("/product/{id}")
    public String productDetailsPage(@PathVariable("id") long id, Model model){
        model.addAttribute("product", productService.findProductById(id));
        return "client/product_details";
    }

    @PostMapping("/product/add-product-to-cart/{productId}")
    public String addProductToCart(@PathVariable("productId") long productId,
                                   Authentication authentication){
        String username = authentication.getName();
        User user = userService.findUserByUsername(username);

        Cart cart = cartService.findCartByUserId(user.getId());

        Product product = productService.findProductById(productId);

        cartItemService.addProductToCart(cart, product, 1);

        return "redirect:/";
    }

    @PostMapping("/search")
    public String searchProductPage(@RequestParam("productName") String productName, Model model){
        if (productName == null || productName.isBlank()) {
            model.addAttribute("products", Collections.emptyList());
            model.addAttribute("searchQuery", "");
            model.addAttribute("message", "Введите название товара для поиска");
            return "client/search_product";
        }

        productName = productName.trim();
        model.addAttribute("products", productService.findProductsByName(productName));
        model.addAttribute("searchQuery", productName);
        return "client/search_product";
    }
}