package ru.loginov.dev.controllers.clientControllers;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.loginov.dev.entities.CartItem;
import ru.loginov.dev.services.CartItemService;
import ru.loginov.dev.services.CartService;
import ru.loginov.dev.services.ProductService;

@Controller
public class IndexController {
    private final ProductService productService;

    public IndexController(ProductService productService){
        this.productService = productService;
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
}