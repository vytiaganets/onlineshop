package com.example.onlineshopproject.dto;

import com.example.onlineshopproject.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponseDto {
    private Long userId;
    public String name;
    private String email;
    private String phone;
//    private String password;//!!!!????
    private UserRole userRole;
}
