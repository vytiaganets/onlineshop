package com.example.onlineshopproject.controller;

import com.example.onlineshopproject.dto.CartItemRequestDto;
import com.example.onlineshopproject.dto.CartItemResponseDto;
import com.example.onlineshopproject.service.CartItemService;
import com.example.onlineshopproject.service.CartService;
import com.example.onlineshopproject.service.CategoryService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/cartitems")
@Validated
public class CartItemController {
    private final CartItemService cartItemService;

    @GetMapping(value = "/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public Set<CartItemResponseDto> getCartItemByUserId(@PathVariable @Positive(message = "User id must be a positive" +
            " number") Long userId){
        return cartItemService.getCartItemByUserId(userId);
    }
    @PostMapping(value = "/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public void insertCartItem(@RequestBody @Valid CartItemRequestDto cartItemRequestDto, @PathVariable @Positive (message = "User id must be a positive" +
            " number") Long userId){
        cartItemService.insertCartItem(cartItemRequestDto, userId);
    }
    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    public void deleteCarItemByProductId(@RequestParam("userId")  @Positive (message = "User id must be a positive" +
            " number") Long userId, @RequestParam("productId") @Positive(message = "Product id must be a positive" +
            " number") Long productId){
        cartItemService.deleteCarItemByProductId(userId, productId);
    }
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CartItemResponseDto> getCartItem() {
        return cartItemService.getCartItem();
    }

    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CartItemResponseDto getCartItemById(@PathVariable Long id) {
        return cartItemService.getCartItemById(id);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCartItemById(@PathVariable Long id) {
        cartItemService.deleteCartItemEntityById(id);
    }
}
