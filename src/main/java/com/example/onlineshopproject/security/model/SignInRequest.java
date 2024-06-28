package com.example.onlineshopproject.security.model;

import lombok.Data;

@Data
public class SignInRequest {
    private String email;
    private String password;
}
