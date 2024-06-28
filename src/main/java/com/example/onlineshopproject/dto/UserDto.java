package com.example.onlineshopproject.dto;

import com.example.onlineshopproject.enums.UserRole;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("email")
    private String email;
    @JsonProperty("phone")
    private String phoneNumber;
    @JsonIgnore
    private String passwordHash;
    @JsonProperty("role")
    private UserRole role;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("Cart")
    private CartDto cartDto;
}
