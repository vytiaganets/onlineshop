package com.example.onlineshopproject.security.controller;

import com.example.onlineshopproject.dto.UserRequestDto;
import com.example.onlineshopproject.dto.UserResponseDto;
import com.example.onlineshopproject.exceptions.ResponseException;
import com.example.onlineshopproject.security.jwt.JwtRequest;
import com.example.onlineshopproject.security.jwt.JwtRequestRefresh;
import com.example.onlineshopproject.security.jwt.JwtResponce;
import com.example.onlineshopproject.security.service.AuthServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.security.auth.message.AuthException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class AuthController implements AuthControllerInterface {
    private final AuthServiceImpl authServiceImpl;

    @PostMapping("/login")
    public ResponseEntity<JwtResponce> login(@RequestBody JwtRequest authRequest) throws AuthException {
        final JwtResponce token = authServiceImpl.login(authRequest);
        return ResponseEntity.ok(token);
    }

    @PostMapping("/token")
    public ResponseEntity<JwtResponce> getNewAccessToken(@RequestBody JwtRequestRefresh jwtRequestRefresh) throws AuthException {
        final JwtResponce token = authServiceImpl.getAccessToken(jwtRequestRefresh.getRefreshToken());
        return ResponseEntity.ok(token);
    }

    @PostMapping("/refresh")
    public ResponseEntity<JwtResponce> getNewRefreshToken(@RequestBody JwtRequestRefresh jwtRequestRefresh) throws AuthException {
        final JwtResponce token = authServiceImpl.refresh(jwtRequestRefresh.getRefreshToken());
        return ResponseEntity.ok(token);
    }

    @PostMapping("/register")
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity<UserRequestDto> register(@RequestBody UserRequestDto userCredentialsDto) throws ResponseException {
        UserRequestDto userRequestDto = authServiceImpl.createUser(userCredentialsDto);
        return ResponseEntity.ofNullable(userRequestDto);
    }
}