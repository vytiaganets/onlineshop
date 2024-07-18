package com.example.onlineshopproject.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FavoriteRequestDto {
    //@NotBlank(message = "Invalid favorite Id: Empty favorite Id")
    //@Pattern(regexp = "^[^0]\\d{1,18}$", message = "Invalid favorite Id: not a number")
    private Long productId;
}
