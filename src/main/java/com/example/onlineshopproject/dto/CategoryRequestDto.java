package com.example.onlineshopproject.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryRequestDto {
    @NotBlank(message = "Invalid category name: Empty category name")
    @Size(min = 2, max = 50, message = "Invalid category name: Must be of 2 to 50 characters")
    private String name;
}
