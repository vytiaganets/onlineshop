package com.example.onlineshopproject.security.service;

import com.example.onlineshopproject.dto.UserRequestDto;
import com.example.onlineshopproject.exceptions.ResponseException;
import com.example.onlineshopproject.security.jwt.JwtAuth;
import com.example.onlineshopproject.security.jwt.JwtRequest;
import com.example.onlineshopproject.security.jwt.JwtResponce;
import jakarta.security.auth.message.AuthException;
import jakarta.validation.constraints.NotNull;

public interface AuthService {
    JwtResponce login(JwtRequest authRequest)  throws AuthException;
    JwtResponce getAccessToken(@NotNull String refreshToken)  throws AuthException;
    JwtResponce refresh(@NotNull String refreshToken) throws AuthException;
    JwtAuth getAuthInfo();
    UserRequestDto createUser(UserRequestDto userCredentialDto)throws ResponseException;
}
