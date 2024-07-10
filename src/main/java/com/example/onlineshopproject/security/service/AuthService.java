package com.example.onlineshopproject.security.service;

import com.example.onlineshopproject.dto.UserRequestDto;
import com.example.onlineshopproject.exceptions.ResponseException;
import com.example.onlineshopproject.security.jwt.JwtAuth;
import com.example.onlineshopproject.security.jwt.JwtRequest;
import com.example.onlineshopproject.security.jwt.JwtResponse;
import jakarta.security.auth.message.AuthException;
import jakarta.validation.constraints.NotNull;

public interface AuthService {
    JwtResponse login(JwtRequest authRequest)  throws AuthException;
    JwtResponse getAccessToken(@NotNull String refreshToken)  throws AuthException;
    JwtResponse refresh(@NotNull String refreshToken) throws AuthException;
    JwtAuth getAuthInfo();
    UserRequestDto createUser(UserRequestDto userCredentialDto)throws ResponseException;
}
