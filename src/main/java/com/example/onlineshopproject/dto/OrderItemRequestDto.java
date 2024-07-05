package com.example.onlineshopproject.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItemRequestDto {
    @NotBlank(message = "Invalid Id: Empty Id")
    @Pattern(regexp = "^[^0]\\d{1,18}$", message = "Invalid Id: not a number")
    private Long productId;

    @NotNull(message = "Quantity cannot be null")
    @Positive(message = "Quantity must be positive")
    @Min(value = 1, message = "Quantity must be at least 1")
    @Max(value = 1000000, message = "Quantity cannot exceed 1000000")
    private Integer quantity;
}
