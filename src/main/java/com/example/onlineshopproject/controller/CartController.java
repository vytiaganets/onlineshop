package com.example.onlineshopproject.controller;

import com.example.onlineshopproject.dto.CartDto;
import com.example.onlineshopproject.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/v1/cart")
public class CartController {
    private final CartService cartService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CartDto insertCart(@RequestBody CartDto cartDto) {
        return cartService.insertCart(cartDto);
    }
}
