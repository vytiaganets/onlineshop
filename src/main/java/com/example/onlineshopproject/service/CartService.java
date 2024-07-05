package com.example.onlineshopproject.service;

import com.example.onlineshopproject.dto.CartResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartService {


    public CartResponseDto insertCart(CartResponseDto cartResponseDto) {
        return cartResponseDto;
    }
}
