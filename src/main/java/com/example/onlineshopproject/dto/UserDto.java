package com.example.onlineshopproject.dto;

import com.example.onlineshopproject.enums.UserRole;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {

    private Long userId;

    @NotBlank(message = "Неверное имя: пустое имя")
    @Size(min = 2, max = 30, message = "Неверное имя: должно состоять из 2 - 30 символов")
    private String name;

    @Email(message = "Неверный адрес электронной почты")
    private String email;

    @NotBlank(message = "Неверный номер телефона:   пустой номер")
    @Pattern(regexp = "^\\d{12}", message = "Неверный номер телефона")
    private String phoneNumber;

    private String passwordHash;

    private UserRole role;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("cart")
    private CartDto cartDto;
}