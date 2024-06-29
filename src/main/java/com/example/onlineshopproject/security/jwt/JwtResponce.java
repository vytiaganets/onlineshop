package com.example.onlineshopproject.security.jwt;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class JwtResponce {
    private final String type = "Bearer";
    private String accessToken;
    private String refreshToken;
}
