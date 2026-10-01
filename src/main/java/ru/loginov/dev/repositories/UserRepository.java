package ru.loginov.dev.repositories;

import ru.loginov.dev.entities.User;

import java.util.List;

public interface UserRepository {
    public List<User> getAllUsers();
    public User findUserById(long id);
    public User findUserByEmail(String email);
    public User findByUsername(String username);
    public void saveUser(User user);
    public void updateUser(User user);
    public void deleteUser(long id);
}
