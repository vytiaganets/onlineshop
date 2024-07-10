package com.example.onlineshopproject.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class OrderInvalidArgumentException extends RuntimeException {

    public OrderInvalidArgumentException(String message) {
        super(message);
    }
}

