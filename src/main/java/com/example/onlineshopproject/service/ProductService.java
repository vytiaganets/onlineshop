package com.example.onlineshopproject.service;

import com.example.onlineshopproject.dto.*;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.List;

public interface ProductService {
    List<ProductResponseDto> getAll(Long categoryId, BigDecimal minPrice, BigDecimal maxPrice, Boolean isDiscount,
                                    String sort);

    List<ProductCountDto> getTop10(String status);

    List<ProductResponseDto> getAll();

    ProductResponseDto getById(Long productId);

    void deleteById(Long productId);

    void insert(ProductRequestDto productRequestDto);

    void update(ProductRequestDto productRequestDto, Long productId);

//    List<ProductResponseDto> findByFilter(Long category, BigDecimal minPrice, BigDecimal maxPrice,
//                                          Boolean isDiscount, String sort);

    List<ProductPendingDto> findProductsPending(Integer days) throws ParseException;

    List<ProductProfitDto> findProductsProfitByPeriod(String period, Integer value);
}
