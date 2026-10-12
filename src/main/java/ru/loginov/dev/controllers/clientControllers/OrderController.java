package ru.loginov.dev.controllers.clientControllers;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.loginov.dev.entities.*;
import ru.loginov.dev.services.*;

import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping("/")
public class OrderController {
    private final UserService userService;
    private final OrderService orderService;
    private final CartService cartService;
    private final OrderItemService orderItemService;
    private final CartItemService cartItemService;
    public OrderController(UserService userService,
                           OrderService orderService,
                           CartService cartService,
                           OrderItemService orderItemService, CartItemService cartItemService){
        this.userService = userService;
        this.orderService = orderService;
        this.cartService = cartService;
        this.orderItemService = orderItemService;
        this.cartItemService = cartItemService;
    }

    @GetMapping("/orders")
    public String ordersPage(Authentication authentication, Model model) {
        User user = userService.findUserByUsername(authentication.getName());

        List<Order> orders =
                orderService.findOrdersWithItemsByUserId(user.getId());

        model.addAttribute("orders", orders);
        return "client/orders";
    }

    @PostMapping("/orders/checkout")
    public String checkout(Authentication authentication) {
        User user = userService.findUserByUsername(authentication.getName());
        Cart cart = cartService.findCartByUserId(user.getId());
        List<CartItem> cartItems = cartItemService.findCartItemsByCartId(cart.getId());
        BigDecimal sum = new BigDecimal(0);

        for(CartItem cartItem: cartItems) {
            sum = sum.add(cartItem.getProduct().getPrice());
        }

        Order order = orderService.createOrder(user, sum);

        for(CartItem cartItem: cartItems){
            orderItemService.addProductToOrder(
                    order,
                    cartItem.getProduct(),
                    cartItem.getQuantity());
        }

        cartItemService.clearCart(cart.getId());

        return "redirect:/orders";
    }

    @GetMapping("/orders/order-details/{order-id}")
    public String orderDetailsPage(@PathVariable("order-id") long id, Authentication authentication, Model model){
        User user = userService.findUserByUsername(authentication.getName());
        List<Order> orders = orderService.findOrdersByUserId(user.getId());

        Order order = orders.stream().filter(o -> o.getId() == id).findAny().orElse(null);//Пока так, потом сделаю отдельный метод в бд

        if (order == null)
            return "redirect:/orders";

        model.addAttribute("order", order);
        model.addAttribute("orderItems", orderItemService.findOrderItemsByOrderId(order.getId()));

        return "client/order_details";
    }

    @PatchMapping("/orders/cancel-order/{id}")
    public String cancelOrder(@PathVariable("id") long id, Authentication authentication){
        User user = userService.findUserByUsername(authentication.getName());
        List<Order> orders = orderService.findOrdersByUserId(user.getId());

        Order order = orders.stream().filter(o -> o.getId() == id).findFirst().orElse(null); //Пока так, потом сделаю отдельный метод в бд

        if(order == null)
            return "redirect:/orders";

        orderService.markOrderAsCancelled(id);

        return "redirect:/orders";
    }
}