package com.example.onlineshopproject.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@Builder
public class ProductProfitDto {
    private Long productId;
    private String period;
    private BigDecimal sum;

    public ProductProfitDto(Long productId, String period, BigDecimal sum) {
        this.productId = productId;
        this.period = period;
        this.sum = sum;
    }
}
