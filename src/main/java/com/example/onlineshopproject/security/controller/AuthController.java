package com.example.onlineshopproject.security.controller;

import com.example.onlineshopproject.dto.UserDto;
import com.example.onlineshopproject.exceptions.ResponseException;
import com.example.onlineshopproject.security.jwt.JwtRequest;
import com.example.onlineshopproject.security.jwt.JwtRequestRefresh;
import com.example.onlineshopproject.security.jwt.JwtResponce;
import com.example.onlineshopproject.security.service.AuthService;
import jakarta.security.auth.message.AuthException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<JwtResponce> login(@RequestBody JwtRequest authRequest) throws AuthException {
        final JwtResponce token = authService.login(authRequest);
        return ResponseEntity.ok(token);
    }

    @PostMapping("/token")
    public ResponseEntity<JwtResponce> getNewAccessToken(@RequestBody JwtRequestRefresh jwtRequestRefresh) throws AuthException {
        final JwtResponce token = authService.getAccessToken(jwtRequestRefresh.getRefreshToken());
        return ResponseEntity.ok(token);
    }

    @PostMapping("/refresh")
    public ResponseEntity<JwtResponce> getNewRefreshToken(@RequestBody JwtRequestRefresh jwtRequestRefresh) throws AuthException {
        final JwtResponce token = authService.refresh(jwtRequestRefresh.getRefreshToken());
        return ResponseEntity.ok(token);
    }

    @PostMapping("/registration")
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity<UserDto> register(@RequestBody UserDto userCredentialsDto) throws ResponseException {
        UserDto userDto = authService.createUser(userCredentialsDto);
        return ResponseEntity.ofNullable(userDto);
    }
}