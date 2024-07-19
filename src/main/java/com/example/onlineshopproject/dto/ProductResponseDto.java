package com.example.onlineshopproject.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductResponseDto {
    private Long productId;
    private String name;
    private String description;
    private BigDecimal price;
    private String imageURL;
    private BigDecimal discountPrice;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private int quantity;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("category")
    private CategoryResponseDto categoryResponseDto;



    public ProductResponseDto(Long productId, String name, String description, BigDecimal price,
                              BigDecimal discountPrice, String imageURL,
                              Timestamp createdAt, Timestamp updatedAt) {
        this.productId = productId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.discountPrice = discountPrice;
        this.imageURL = imageURL;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;

    }
}
