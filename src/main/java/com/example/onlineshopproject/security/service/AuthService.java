package com.example.onlineshopproject.security.service;

import com.example.onlineshopproject.dto.UserDto;
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
public class AuthService {

    private final UserService userService;

    private final Map<String, String> refreshStorage = new HashMap<>();

    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;

    public JwtResponce login(JwtRequest authRequest) throws AuthException {
        final UserDto userDto = userService.getByEmail(authRequest.getLogin());
        if (userDto == null) new AuthException("Пользователь не найден");

        if (passwordEncoder.matches(authRequest.getPassword(), userDto.getPasswordHash())) {
            final String accessToken = jwtProvider.generateAccessToken(userDto);
            final String refreshToken = jwtProvider.generateRefreshToken(userDto);
            refreshStorage.put(userDto.getEmail(), refreshToken);
            return new JwtResponce(accessToken, refreshToken);
        } else {
            throw new AuthException("Неправильный пароль.");
        }
    }

    public JwtResponce getAccessToken(@NotNull String refreshToken) throws AuthException {
        if (jwtProvider.validateRefreshToken(refreshToken)) {
            final Claims claims = jwtProvider.getRefreshClaims(refreshToken);
            final String login = claims.getSubject();
            final String savedRefreshToken = refreshStorage.get(login);
            if (savedRefreshToken != null && savedRefreshToken.equals(refreshToken)) {
                final UserDto userDto = userService.getByEmail(login);
                if (userDto == null) new AuthException("Пользователь не найден.");
                final String accessToken = jwtProvider.generateAccessToken(userDto);
                return new JwtResponce(accessToken, null);
            }
        }
        return new JwtResponce(null, null);
    }

    public JwtResponce refresh(@NotNull String refreshToken) throws AuthException {
        if (jwtProvider.validateRefreshToken(refreshToken)) {
            final Claims claims = jwtProvider.getRefreshClaims(refreshToken);
            final String login = claims.getSubject();
            final String savedRefreshToken = refreshStorage.get(login);
            if (savedRefreshToken != null && savedRefreshToken.equals(refreshToken)) {
                final UserDto userDto = userService.getByEmail(login);
                if (userDto == null) new AuthException("Пользователь не найден.");
                final String accessToken = jwtProvider.generateAccessToken(userDto);
                final String newRefreshToken = jwtProvider.generateRefreshToken(userDto);
                refreshStorage.put(userDto.getEmail(), newRefreshToken);
                return new JwtResponce(accessToken, newRefreshToken);
            }
        }
        throw new AuthException("Неверный токен JWT.");
    }

    public JwtAuth getAuthInfo() {
        return (JwtAuth) SecurityContextHolder.getContext().getAuthentication();
    }

    public UserDto createUser(UserDto userCredentialDto) throws ResponseException {
        return userService.createUser(userCredentialDto);
    }
}
