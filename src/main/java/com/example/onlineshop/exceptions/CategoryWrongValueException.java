package com.example.onlineshop.exceptions;

public class CategoryWrongValueException extends RuntimeException {
    public CategoryWrongValueException(String message){
        super(message);
    }
}
