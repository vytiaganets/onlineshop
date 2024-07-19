package com.example.onlineshopproject.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@Builder
public class ProductProfitDto {
    private String period;
    private BigDecimal sum;

    public ProductProfitDto(String period, BigDecimal sum) {
        this.period = period;
        this.sum = sum;
    }
}
