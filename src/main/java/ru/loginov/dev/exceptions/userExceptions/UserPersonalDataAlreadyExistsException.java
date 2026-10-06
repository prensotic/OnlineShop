package ru.loginov.dev.exceptions.userExceptions;

public class UserPersonalDataAlreadyExistsException extends RuntimeException {
    public UserPersonalDataAlreadyExistsException(String message) {
        super(message);
    }
}
