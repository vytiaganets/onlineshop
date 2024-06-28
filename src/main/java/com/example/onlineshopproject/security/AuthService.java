package com.example.onlineshopproject.security;

import com.example.onlineshopproject.security.model.JwtAuthResponce;
import com.example.onlineshopproject.security.model.SignInRequest;

public interface AuthService {
    JwtAuthResponce authenticate(SignInRequest request);
}
