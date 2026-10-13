package ru.loginov.dev.services;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import ru.loginov.dev.entities.User;
import ru.loginov.dev.exceptions.userExceptions.UserAlreadyExistsException;
import ru.loginov.dev.exceptions.userExceptions.UserNotFoundException;
import ru.loginov.dev.repositories.UserRepository;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final CartService cartService;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, CartService cartService){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.cartService = cartService;
    }

    @Transactional
    public List<User> getAllUsers(){
        return userRepository.getAllUsers();
    }

    @Transactional
    public User findUserById(long id){
        if(id <= 0) throw new IllegalArgumentException("ID пользователя должен быть положительным");

        User user = userRepository.findUserById(id);

        if(user == null) throw new UserNotFoundException("Пользователь с ID: " + id + " не найден");

        return user;
    }

    @Transactional
    public User findUserByUsername(String username){
        if (username == null || username.isBlank())
            throw new IllegalArgumentException("Имя пользователя указано некорректно");

        User user = userRepository.findByUsername(username);

        if(user == null) throw new UserNotFoundException("Пользователь с именем: " + username + " не найден");

        return user;
    }

    @Transactional
    public User findUserByEmail(String email){
        if(email == null || email.isBlank()) throw new IllegalArgumentException("Email указан некорректно");

        User user = userRepository.findUserByEmail(email);

        if(user == null) throw new UserNotFoundException("Пользователь с почтой: " + email + " не найден");

        return user;
    }

    @Transactional
    public List<User> findUsersByRole(String role){
        if(role == null || role.isBlank()) throw new IllegalArgumentException("Роль пользователя указана некорректно");
        return userRepository.findUsersByRole(role);
    }

    @Transactional
    public void createUser(String username, String email, String password, String role) {
        if (username == null || username.isBlank()) throw new IllegalArgumentException("Имя указано некорректно");

        if (email == null || email.isBlank()) throw new IllegalArgumentException("Email указан некорректно");

        if (password == null || password.isBlank()) throw new IllegalArgumentException("Пароль указан некорректно");

        if (userRepository.findByUsername(username) != null)
            throw new UserAlreadyExistsException("Пользователь с таким именем уже существует");

        if (userRepository.findUserByEmail(email) != null)
            throw new UserAlreadyExistsException("Пользователь с такой почтой уже существует");

        User user = new User();

        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setActive(true);
        user.setRole(role);

        userRepository.saveUser(user);
        cartService.addCartToUser(user);
    }

    @Transactional
    public void updatePassword(long id, String password, String newPassword){
        if (id <= 0) throw new IllegalArgumentException("ID пользователя должен быть положительным");
        User user = findUserById(id);
        if(!passwordEncoder.matches(password, user.getPassword()))
            throw new IllegalArgumentException("Неверный пароль");
        user.setPassword(passwordEncoder.encode(newPassword));
    }

    @Transactional
    public void updateUserProfile(long id, String username, String email) {
        if (id <= 0) throw new IllegalArgumentException("ID пользователя должен быть положительным");

        if (username == null || username.isBlank())
            throw new IllegalArgumentException("Имя пользователя указано некорректно");

        if (email == null || email.isBlank()) throw new IllegalArgumentException("Email указан некорректно");

        User user = findUserById(id);

        user.setUsername(username);
        user.setEmail(email);
    }

    @Transactional
    public void deleteUser(long id){
        if(id <= 0) throw new IllegalArgumentException("ID пользователя должен быть положительным");

        User user = findUserById(id);

        userRepository.deleteUser(id);
    }

    @Transactional
    public void deactivateUser(long id){
        if(id <= 0) throw new IllegalArgumentException("ID пользователя должен быть положительным");

        User user = findUserById(id);

        user.setActive(false);
    }
}
