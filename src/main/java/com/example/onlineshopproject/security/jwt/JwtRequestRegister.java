package com.example.onlineshopproject.security.jwt;

import com.example.onlineshopproject.enums.UserRole;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtRequestRegister {
    private String name;
    private String email;
    private String phoneNumber;
    private String passwordHash;
    private UserRole role;
}
