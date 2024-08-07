package com.example.onlineshopproject.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartItemResponseDto {
    private Long cartItemId;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("cart")
    private CartResponseDto cartResponseDto;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("product")
    private ProductResponseDto productResponseDto;
    private Integer quantity;
}
