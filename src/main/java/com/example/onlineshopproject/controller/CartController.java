package com.example.onlineshopproject.controller;

import com.example.onlineshopproject.dto.CartDto;
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
                            schema = @Schema(implementation = CartDto.class))}),
            @ApiResponse(responseCode = "400",
                    description = "Invalid request body",
                    content = @Content),
            @ApiResponse(responseCode = "500",
                    description = "Internal server error")})
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CartDto insertCart(@RequestBody CartDto cartDto) {
        log.debug("Request to create cart: {}", cartDto);
        return cartService.insertCart(cartDto);
    }
}
