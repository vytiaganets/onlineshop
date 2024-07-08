package com.example.onlineshopproject.service;

import com.example.onlineshopproject.dto.CartResponseDto;

public interface CartService {
    CartResponseDto getById(Long id);
    CartResponseDto insert(CartResponseDto cartResponseDto);
}
