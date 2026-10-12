package ru.loginov.dev.controllers.adminControllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.loginov.dev.entities.User;
import ru.loginov.dev.services.UserService;

@Controller
@RequestMapping("/dashboard")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public String usersPage(Model model){
        model.addAttribute("users", userService.getAllUsers());
        return "dashboard/users/users";
    }

    @GetMapping("/users/user-details/{id}")
    public String userDetailsPage(@PathVariable("id") long id, Model model){
        model.addAttribute("user", userService.findUserById(id));
        return "dashboard/users/user_details";
    }

    @PatchMapping("/users/detach-user/{id}")
    public String deactivateUser(@PathVariable("id") long id){
        userService.deactivateUser(id);
        return "redirect:/dashboard/users";
    }
}