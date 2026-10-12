package ru.loginov.dev.controllers.adminControllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.loginov.dev.entities.User;
import ru.loginov.dev.services.OrderService;
import ru.loginov.dev.services.UserService;

@Controller
@RequestMapping("/dashboard")
public class AdminOrderController {
    private final OrderService orderService;
    private final UserService userService;

    public AdminOrderController(OrderService orderService, UserService userService) {
        this.orderService = orderService;
        this.userService = userService;
    }

    @GetMapping("/orders")
    public String ordersPage(Model model){
        model.addAttribute("orders", orderService.getAllOrders());
        return "dashboard/orders/orders";
    }

    @GetMapping("/orders/order-details/{id}")
    public String orderDetailsPage(@PathVariable("id") long id, Model model){
        model.addAttribute("order", orderService.findOrderWithItemsById(id));
        System.out.println(orderService.findOrderWithItemsById(id).getOrderItems());
        return "dashboard/orders/order_details";
    }

    @GetMapping("/orders/search")
    public String orderSearchByUsernamePage(@RequestParam("username") String username, Model model) {
        username = username.trim();
        if (username.isBlank()) {
            model.addAttribute("error", "Введите имя пользователя.");
            return "dashboard/orders/order_search";
        }

        User user = userService.findUserByUsername(username);

        if (user == null) {
            model.addAttribute("error", "Пользователь с именем «" + username + "» не найден.");
            model.addAttribute("searchedUsername", username);
            return "dashboard/orders/order_search";
        }

        model.addAttribute("searchedUsername", username);
        model.addAttribute("orders", orderService.findOrdersByUserId(user.getId()));

        return "dashboard/orders/order_search";
    }
}