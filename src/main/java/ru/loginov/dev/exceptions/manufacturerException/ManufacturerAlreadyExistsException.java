package ru.loginov.dev.exceptions.manufacturerException;

public class ManufacturerAlreadyExistsException extends RuntimeException{
    public ManufacturerAlreadyExistsException(String message) {
        super(message);
    }
}
