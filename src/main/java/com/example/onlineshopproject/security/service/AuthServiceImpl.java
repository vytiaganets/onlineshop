package com.example.onlineshopproject.security.service;

import com.example.onlineshopproject.dto.UserRequestDto;
import com.example.onlineshopproject.exceptions.ResponseException;
import com.example.onlineshopproject.security.jwt.*;
import com.example.onlineshopproject.service.UserService;
import io.jsonwebtoken.Claims;
import jakarta.security.auth.message.AuthException;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserService userService;

    private final Map<String, String> refreshStorage = new HashMap<>();

    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;

    public JwtResponse login(JwtRequest authRequest) throws AuthException {
        final UserRequestDto userRequestDto = userService.getByEmail(authRequest.getLogin());
        if (userRequestDto == null) new AuthException("Пользователь не найден");

        if (passwordEncoder.matches(authRequest.getPassword(), userRequestDto.getPasswordHash())) {
            final String accessToken = jwtProvider.generateAccessToken(userRequestDto);
            final String refreshToken = jwtProvider.generateRefreshToken(userRequestDto);
            refreshStorage.put(userRequestDto.getEmail(), refreshToken);
            return new JwtResponse(accessToken, refreshToken);
        } else {
            throw new AuthException("Неправильный пароль.");
        }
    }

    public JwtResponse getAccessToken(@NotNull String refreshToken) throws AuthException {
        if (jwtProvider.validateRefreshToken(refreshToken)) {
            final Claims claims = jwtProvider.getRefreshClaims(refreshToken);
            final String login = claims.getSubject();
            final String savedRefreshToken = refreshStorage.get(login);
            if (savedRefreshToken != null && savedRefreshToken.equals(refreshToken)) {
                final UserRequestDto userRequestDto = userService.getByEmail(login);
                if (userRequestDto == null) new AuthException("Пользователь не найден.");
                final String accessToken = jwtProvider.generateAccessToken(userRequestDto);
                return new JwtResponse(accessToken, null);
            }
        }
        return new JwtResponse(null, null);
    }

    public JwtResponse refresh(@NotNull String refreshToken) throws AuthException {
        if (jwtProvider.validateRefreshToken(refreshToken)) {
            final Claims claims = jwtProvider.getRefreshClaims(refreshToken);
            final String login = claims.getSubject();
            final String savedRefreshToken = refreshStorage.get(login);
            if (savedRefreshToken != null && savedRefreshToken.equals(refreshToken)) {
                final UserRequestDto userRequestDto = userService.getByEmail(login);
                if (userRequestDto == null) new AuthException("Пользователь не найден.");
                final String accessToken = jwtProvider.generateAccessToken(userRequestDto);
                final String newRefreshToken = jwtProvider.generateRefreshToken(userRequestDto);
                refreshStorage.put(userRequestDto.getEmail(), newRefreshToken);
                return new JwtResponse(accessToken, newRefreshToken);
            }
        }
        throw new AuthException("Неверный токен JWT.");
    }

    public JwtAuth getAuthInfo() {
        return (JwtAuth) SecurityContextHolder.getContext().getAuthentication();
    }

    public UserRequestDto createUser(UserRequestDto userCredentialDto) throws ResponseException {
        return userService.create(userCredentialDto);
    }
}
