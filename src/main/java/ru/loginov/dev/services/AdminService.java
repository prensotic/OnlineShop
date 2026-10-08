package ru.loginov.dev.services;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.loginov.dev.entities.User;
import ru.loginov.dev.repositories.UserRepository;

@Service
public class AdminService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final CartService cartService;

    public AdminService(UserRepository userRepository, PasswordEncoder passwordEncoder, CartService cartService){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.cartService = cartService;
    }

    @Transactional
    public void createInitialSuperAdmin(){
        if(userRepository.existsByRole("SUPER_ADMIN")){
            return;
        }

        User admin = new User();

        admin.setUsername("super_admin");
        admin.setEmail("super_admin@online_shop.com");
        admin.setPassword(passwordEncoder.encode("online_shop_super_admin"));
        admin.setActive(true);
        admin.setRole("SUPER_ADMIN");

        userRepository.saveUser(admin);
        cartService.addCartToUser(admin);
    }
}