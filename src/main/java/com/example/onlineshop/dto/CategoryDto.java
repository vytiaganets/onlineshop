package com.example.onlineshop.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryDto {
    @NotBlank(message = "Id не должно быть пустым")
    private  Long  id;
    @NotBlank(message = "Имя не должно быть пустым")
    private String name;
}