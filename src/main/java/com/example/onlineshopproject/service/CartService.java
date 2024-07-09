package com.example.onlineshopproject.service;

import com.example.onlineshopproject.dto.CartItemRequestDto;
import com.example.onlineshopproject.dto.CartItemResponseDto;
import com.example.onlineshopproject.dto.CartResponseDto;

import java.util.Set;

public interface CartService {
    public Set<CartItemResponseDto> getByUserId(Long userId);
    public void insert(CartItemRequestDto cartItemRequestDto, Long userId);
    public void deleteByProductId(Long userId, Long productId);
//    CartResponseDto getById(Long id);
//    CartResponseDto insert(CartResponseDto cartResponseDto);
}
