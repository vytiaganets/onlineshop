package com.example.onlineshopproject.dto;

import com.example.onlineshopproject.enums.DeliveryMethod;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderRequestDto {
    @JsonProperty("items")
    @NotEmpty(message = "Item list cannot be empty")
    private Set<OrderItemRequestDto> orderItemSet;

    @NotBlank(message = "Delivery address cannot be blank")
    @Size(max = 255, message = "Delivery address must be less than or equal to 255 characters")
    private String deliveryAddress;

    @NotBlank(message = "Delivery method cannot be blank")
    @Size(min = 2, max = 50, message = "Invalid Delivery method")
    private String deliveryMethod;

}
