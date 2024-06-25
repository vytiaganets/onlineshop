package com.example.onlineshop.security;

import com.example.onlineshop.security.model.JwtAuthResponce;
import com.example.onlineshop.security.model.SignInRequest;

public interface AuthService {
    JwtAuthResponce authenticate(SignInRequest request);
}
