package com.example.onlineshopproject.security.jwt;


import io.jsonwebtoken.Claims;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtUtils {
    public static JwtAuth generate(Claims claims) {
        String username = claims.getSubject();
        List<?> roleObjectList = claims.get("roles", List.class);
        List<String> roles = roleObjectList.stream()
                .map(Object::toString)
                .collect(Collectors.toList());
        return new JwtAuth(username, roles);
    }
}