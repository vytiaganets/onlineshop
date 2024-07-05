package com.example.onlineshopproject.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryResponseDto {
    @NotBlank(message = "Id must not be empty")
    private Long categoryId;
    @NotBlank(message = "The name must not be empty")
    private String name;
}