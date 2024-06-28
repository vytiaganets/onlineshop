package com.example.onlineshopproject.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartItemDto {
    private Long cartItemId;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("cart")
    private UserDto cart;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("product")
    private UserDto product;
    private int quantity;
}
