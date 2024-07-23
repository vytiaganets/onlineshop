package com.example.onlineshopproject.security.controller;

import com.example.onlineshopproject.dto.UserRequestDto;
import com.example.onlineshopproject.exceptions.ResponseException;
import com.example.onlineshopproject.security.jwt.JwtRequest;
import com.example.onlineshopproject.security.jwt.JwtRequestRefresh;
import com.example.onlineshopproject.security.jwt.JwtResponse;
import com.example.onlineshopproject.security.service.AuthService;
import jakarta.security.auth.message.AuthException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class AuthController implements AuthControllerInterface {
//    private final AuthServiceImpl authServiceImpl;
        private final AuthService authServiceImpl;
    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest authRequest) throws AuthException {
        final JwtResponse token = authServiceImpl.login(authRequest);
        return ResponseEntity.ok(token);
    }

    @PostMapping("/token")
    public ResponseEntity<JwtResponse> getNewAccessToken(@RequestBody JwtRequestRefresh jwtRequestRefresh) throws AuthException {
        final JwtResponse token = authServiceImpl.getAccessToken(jwtRequestRefresh.getRefreshToken());
        return ResponseEntity.ok(token);
    }

    @PostMapping("/refresh")
    public ResponseEntity<JwtResponse> getNewRefreshToken(@RequestBody JwtRequestRefresh jwtRequestRefresh) throws AuthException {
        final JwtResponse token = authServiceImpl.refresh(jwtRequestRefresh.getRefreshToken());
        return ResponseEntity.ok(token);
    }

    @PostMapping("/register")
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity<UserRequestDto> register(@RequestBody UserRequestDto userCredentialsDto) throws ResponseException {
        UserRequestDto userRequestDto = authServiceImpl.createUser(userCredentialsDto);
        return ResponseEntity.ofNullable(userRequestDto);
    }
}