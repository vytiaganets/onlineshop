package com.example.onlineshopproject.controller;

import com.example.onlineshopproject.dto.CartResponseDto;
import com.example.onlineshopproject.service.CartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Cart Controller",
        description = "API for shopping carts")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/v1/cart")
public class CartController {
    private final CartService cartService;
    @Operation(summary = "Create a cart")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Cart created successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CartResponseDto.class))}),
            @ApiResponse(responseCode = "400",
                    description = "Invalid request body",
                    content = @Content),
            @ApiResponse(responseCode = "500",
                    description = "Internal server error")})
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CartResponseDto insertCart(@RequestBody CartResponseDto cartResponseDto) {
        log.debug("Request to create cart: {}", cartResponseDto);
        return cartService.insertCart(cartResponseDto);
    }

    @Operation(summary = "Get cart by user id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Found the cart",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CartResponseDto.class))}),
            @ApiResponse(responseCode = "404",
                    description = "Cart not found",
                    content = @Content),
            @ApiResponse(responseCode = "500",
                    description = "Internal server error")})
    @GetMapping(value = "/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<CartResponseDto> getByUserId(@PathVariable Long userId){
        log.debug("Request to get cart for usser id: {}", userId);
        CartResponseDto cartResponseDto = cartService.getCartById(userId);
        return ResponseEntity.ok(cartResponseDto);
    }
}
