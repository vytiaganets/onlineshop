package com.example.onlineshopproject.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CartInvalidArgumentException extends RuntimeException{
    public CartInvalidArgumentException(String message){
        super(message);
    }
}
