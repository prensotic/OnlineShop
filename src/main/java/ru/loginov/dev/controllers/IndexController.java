package ru.loginov.dev.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
    @GetMapping("/")
    public String indexPage(Authentication authentication, Model model) {
        model.addAttribute("authentication", authentication);
        return "index";
    }
}