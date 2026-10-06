package ru.loginov.dev.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.loginov.dev.entities.User;
import ru.loginov.dev.services.AuthService;

@Controller
@RequestMapping("/auth")
public class AuthorizationController {

    private final AuthService authService;

    public AuthorizationController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/registration")
    public String registrationPage(Model model) {
        model.addAttribute("user", new User());
        return "auth/registration";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "auth/login";
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute("user") User user) {
        authService.register(user.getUsername(), user.getEmail(), user.getPassword());
        return "redirect:/auth/login";
    }
}