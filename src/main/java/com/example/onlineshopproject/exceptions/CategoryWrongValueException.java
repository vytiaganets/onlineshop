package com.example.onlineshopproject.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CategoryWrongValueException extends RuntimeException {
    public CategoryWrongValueException(String message) {
        super(message);
    }
}
