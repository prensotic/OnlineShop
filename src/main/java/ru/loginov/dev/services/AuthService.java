package ru.loginov.dev.services;

import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserService userService;

    public AuthService(UserService userService) {
        this.userService = userService;
    }

    public void register(String username, String email, String password){
        userService.createUser(username, email, password, "USER");
    }
}