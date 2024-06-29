package com.example.onlineshopproject.exceptions;

public class NotFoundInDbException extends RuntimeException {
    public NotFoundInDbException(String message) {
        super(message);
    }
}
