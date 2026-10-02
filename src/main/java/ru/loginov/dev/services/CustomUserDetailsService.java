package ru.loginov.dev.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.loginov.dev.entities.User;
import ru.loginov.dev.repositories.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        if(user == null)
            throw new UsernameNotFoundException("Пользователь с именем '" + username + "' не найден");


        return org.springframework.security.core.userdetails.User
                .withUsername(username)
                .password(user.getPasswordHash())
                .roles(user.getRole())
                .disabled(!user.isActive())
                .build();
    }
}
