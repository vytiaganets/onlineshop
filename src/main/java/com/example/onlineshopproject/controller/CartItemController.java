package com.example.onlineshopproject.controller;

import com.example.onlineshopproject.dto.CartItemRequestDto;
import com.example.onlineshopproject.dto.CartItemResponseDto;
import com.example.onlineshopproject.service.CartItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@Tag(name = "Cart item Controller",
        description = "Controller for cart item operations")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/cartitems")
@Validated
public class CartItemController {
    private final CartItemService cartItemService;

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
    @GetMapping(value = "/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public Set<CartItemResponseDto> getCartItemByUserId(@PathVariable @Positive(message = "User id must be a positive" +
            " number") Long userId) {
        return cartItemService.getCartItemByUserId(userId);
    }

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
    @PostMapping(value = "/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public void insertCartItem(@RequestBody @Valid CartItemRequestDto cartItemRequestDto, @PathVariable @Positive(message = "User id must be a positive" +
            " number") Long userId) {
        cartItemService.insertCartItem(cartItemRequestDto, userId);
    }

    @Operation(summary = "Delete a cart item")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",
                    description = "Cart item deleted successfully"),
            @ApiResponse(responseCode = "404",
                    description = "Cart item not found",
                    content = @Content),
            @ApiResponse(responseCode = "500",
                    description = "Internal server error")})
    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    public void deleteCarItemByProductId(@RequestParam("userId") @Positive(message = "User id must be a positive" +
            " number") Long userId, @RequestParam("productId") @Positive(message = "Product id must be a positive" +
            " number") Long productId) {
        cartItemService.deleteCarItemByProductId(userId, productId);
    }

    @Operation(summary = "Get all cart items")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Found all cart items",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = CartItemResponseDto.class)))}),
            @ApiResponse(responseCode = "500",
                    description = "Internal server error")})
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CartItemResponseDto> getCartItem() {
        return cartItemService.getCartItem();
    }

    @Operation(summary = "Get a cart item by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Found the cart item",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CartItemResponseDto.class))}),
            @ApiResponse(responseCode = "404",
                    description = "Cart item not found",
                    content = @Content)})
    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CartItemResponseDto getCartItemById(@PathVariable Long id) {
        return cartItemService.getCartItemById(id);
    }

    @Operation(summary = "Delete a cart item by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",
                    description = "Cart deleted successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CartItemResponseDto.class))}),
            @ApiResponse(responseCode = "404",
                    description = "Cart item not found",
                    content = @Content),
            @ApiResponse(responseCode = "500",
                    description = "Internal server error")})
    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCartItemById(@PathVariable Long id) {
        cartItemService.deleteCartItemEntityById(id);
    }
}
