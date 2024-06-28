package com.example.onlineshopproject.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryDto {
    @NotBlank(message = "Id не должно быть пустым")
    private  Long  categoryId;
    @NotBlank(message = "Имя не должно быть пустым")
    private String name;
}