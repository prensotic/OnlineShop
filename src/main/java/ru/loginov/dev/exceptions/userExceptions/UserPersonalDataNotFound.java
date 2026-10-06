package ru.loginov.dev.exceptions.userExceptions;

public class UserPersonalDataNotFound extends RuntimeException {
    public UserPersonalDataNotFound(String message) {
        super(message);
    }
}
