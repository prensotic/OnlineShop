package ru.loginov.dev.exceptions.userExceptions;

public class UserAlreadyExistException extends RuntimeException {
    public UserAlreadyExistException(String message){
        super(message);
    }
}