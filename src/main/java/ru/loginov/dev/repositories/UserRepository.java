package ru.loginov.dev.repositories;

import ru.loginov.dev.entities.User;

import java.util.List;

public interface UserRepository {
    List<User> getAllUsers();
    User findUserById(long id);
    User findUserByEmail(String email);
    User findByUsername(String username);
    boolean existsByRole(String role);
    List<User> findUsersByRole(String role);
    void saveUser(User user);
    void updateUser(User user);
    void deleteUser(long id);
}
