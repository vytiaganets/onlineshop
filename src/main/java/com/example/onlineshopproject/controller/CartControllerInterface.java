package com.example.onlineshopproject.controller;

import com.example.onlineshopproject.dto.CartItemRequestDto;
import com.example.onlineshopproject.dto.CartItemResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Set;

@Tag(name = "Cart Controller",
        description = "Controller for cart operations")
public interface CartControllerInterface {
    @Operation(summary = "Get cart item by user id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Found the cart item",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CartItemResponseDto.class))}),
            @ApiResponse(responseCode = "404",
                    description = "Cart item not found",
                    content = @Content),
            @ApiResponse(responseCode = "500",
                    description = "Internal server error")})
    public Set<CartItemResponseDto> getByUserId(@PathVariable @Positive(message = "User id must be a positive" +
            " number") Long userId);

    @Operation(summary = "Create a cart item")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Cart item created successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CartItemResponseDto.class))}),
            @ApiResponse(responseCode = "400",
                    description = "Invalid request body",
                    content = @Content),
            @ApiResponse(responseCode = "500",
                    description = "Internal server error")})
    public void insert(@RequestBody @Valid CartItemRequestDto cartItemRequestDto, @PathVariable @Positive(message = "User id must be a positive" +
            " number") Long userId);

    @Operation(summary = "Delete a cart item")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",
                    description = "Cart item deleted successfully"),
            @ApiResponse(responseCode = "404",
                    description = "Cart item not found",
                    content = @Content),
            @ApiResponse(responseCode = "500",
                    description = "Internal server error")})
    public void deleteByProductId(@RequestParam("userId") @Positive(message = "User id must be a positive" +
            " number") Long userId, @RequestParam("productId") @Positive(message = "Product id must be a positive" +
            " number") Long productId);
}
