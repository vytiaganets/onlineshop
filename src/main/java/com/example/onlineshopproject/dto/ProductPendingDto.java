package com.example.onlineshopproject.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@Builder
public class ProductPendingDto {
    private Long productId;
    private String name;
    private Integer count;
    private Timestamp createAt;

    public ProductPendingDto(Long productId, String name, Integer count, Timestamp createAt) {
        this.productId = productId;
        this.name = name;
        this.count = count;
        this.createAt = createAt;
    }
}
