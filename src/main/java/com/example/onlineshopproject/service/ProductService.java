package com.example.onlineshopproject.service;

import com.example.onlineshopproject.dto.ProductCountDto;
import com.example.onlineshopproject.dto.ProductRequestDto;
import com.example.onlineshopproject.dto.ProductResponseDto;

import java.util.List;

public interface ProductService {
    List<ProductResponseDto> getAll(Long categoryId, Double minPrice, Double maxPrice, Boolean isDiscount,
                                    String sort);

    List<ProductCountDto> getTop10(String status);

    List<ProductResponseDto> getAll();

    ProductResponseDto getById(Long productId);

    void deleteById(Long productId);

    void insert(ProductRequestDto productRequestDto);

    void update(ProductRequestDto productRequestDto, Long productId);

    List<ProductResponseDto> findByFilter(Long category, Double minPrice, Double maxPrice,
                                          Boolean isDiscount, String sort);

}
