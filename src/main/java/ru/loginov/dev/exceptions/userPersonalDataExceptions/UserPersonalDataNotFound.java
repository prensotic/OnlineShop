package ru.loginov.dev.exceptions.userPersonalDataExceptions;

public class UserPersonalDataNotFound extends RuntimeException {
    public UserPersonalDataNotFound(String message) {
        super(message);
    }
}
