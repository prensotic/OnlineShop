package ru.loginov.dev.exceptions.userPersonalDataExceptions;

public class UserPersonalDataAlreadyExistsException extends RuntimeException {
    public UserPersonalDataAlreadyExistsException(String message) {
        super(message);
    }
}
