package com.example.onlineshopproject.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderItemResponseDto {
    private Long orderItemId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("order")
    private OrderResponseDto orderResponseDto;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("product")
    private ProductResponseDto productResponseDto;

    private BigDecimal priceAtPurchase;

    private Integer quantity;
}
