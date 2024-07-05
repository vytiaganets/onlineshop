package com.example.onlineshopproject.controller;

import com.example.onlineshopproject.dto.CartItemResponseDto;
import com.example.onlineshopproject.service.CartItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/cartitems")
public class CartItemController {
    private final CartItemService categoryService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CartItemResponseDto> getCartItem() {
        return categoryService.getCartItem();
    }

    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CartItemResponseDto getCartItemById(@PathVariable Long id) {
        return categoryService.getCartItemById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CartItemResponseDto createCartItem(@RequestBody CartItemResponseDto categoryDto) {
        return categoryService.createCartItem(categoryDto);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public CartItemResponseDto updateCartItem(@RequestBody CartItemResponseDto categoryDto) {
        return categoryService.updateCartItem(categoryDto);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCartItemById(@PathVariable Long id) {
        categoryService.deleteCartItemEntityById(id);
    }
}
