package ru.loginov.dev.services;

import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.loginov.dev.entities.User;
import ru.loginov.dev.exceptions.userExceptions.UserAlreadyExistException;
import ru.loginov.dev.repositories.UserRepository;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void register(String username, String email, String password){
        if(userRepository.findByUsername(username) != null)
            throw new UserAlreadyExistException("Пользователь с таким именем уже существует");

        if(userRepository.findUserByEmail(email) != null)
            throw new UserAlreadyExistException("Пользователь с такой почтой уже существует");

        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPasswordHash(passwordEncoder.encode(password));
        user.setActive(true);
        user.setRole("USER");

        userRepository.saveUser(user);
    }
}