package com.example.onlineshopproject.controller;

import com.example.onlineshopproject.dto.CartItemRequestDto;
import com.example.onlineshopproject.dto.CartItemResponseDto;
import com.example.onlineshopproject.dto.CartResponseDto;
import com.example.onlineshopproject.service.CartServiceImpl;
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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@Tag(name = "Cart Controller",
        description = "Controller for cart operations")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/cart")
public class CartController {
    private final CartServiceImpl cartServiceImpl;
//    @Operation(summary = "Get all cart items")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200",
//                    description = "Found all cart items",
//                    content = {@Content(mediaType = "application/json",
//                            array = @ArraySchema(schema = @Schema(implementation = CartItemResponseDto.class)))}),
//            @ApiResponse(responseCode = "500",
//                    description = "Internal server error")})
//    @GetMapping
//    @ResponseStatus(HttpStatus.OK)
//    public List<CartItemResponseDto> getAll() {
//        return cartServiceImpl.getAll();
//    }

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
    public Set<CartItemResponseDto> getByUserId(@PathVariable @Positive(message = "User id must be a positive" +
            " number") Long userId) {
        return cartServiceImpl.getByUserId(userId);
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
    public void insert(@RequestBody @Valid CartItemRequestDto cartItemRequestDto, @PathVariable @Positive(message = "User id must be a positive" +
            " number") Long userId) {
        cartServiceImpl.insert(cartItemRequestDto, userId);
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
    public void deleteByProductId(@RequestParam("userId") @Positive(message = "User id must be a positive" +
            " number") Long userId, @RequestParam("productId") @Positive(message = "Product id must be a positive" +
            " number") Long productId) {
        cartServiceImpl.deleteByProductId(userId, productId);
    }

//    @Operation(summary = "Get a cart item by id")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200",
//                    description = "Found the cart item",
//                    content = {@Content(mediaType = "application/json",
//                            schema = @Schema(implementation = CartItemResponseDto.class))}),
//            @ApiResponse(responseCode = "404",
//                    description = "Cart item not found",
//                    content = @Content)})
//    @GetMapping(value = "/{userId}")
//    @ResponseStatus(HttpStatus.OK)
//    public CartItemResponseDto getById(@PathVariable Long id) {
//        return cartItemServiceImpl.getById(id);
//    }

//    @Operation(summary = "Delete a cart item by id")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "204",
//                    description = "Cart deleted successfully",
//                    content = {@Content(mediaType = "application/json",
//                            schema = @Schema(implementation = CartItemResponseDto.class))}),
//            @ApiResponse(responseCode = "404",
//                    description = "Cart item not found",
//                    content = @Content),
//            @ApiResponse(responseCode = "500",
//                    description = "Internal server error")})
//    @DeleteMapping(value = "/{userId}")
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    public void deleteById(@PathVariable Long userId) {
//        cartServiceImpl.deleteById(userId);
//    }
//    @Operation(summary = "Create a cart")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200",
//                    description = "Cart created successfully",
//                    content = {@Content(mediaType = "application/json",
//                            schema = @Schema(implementation = CartResponseDto.class))}),
//            @ApiResponse(responseCode = "400",
//                    description = "Invalid request body",
//                    content = @Content)})
//    @PostMapping
//    @ResponseStatus(HttpStatus.CREATED)
//    public CartResponseDto insert(@RequestBody CartResponseDto cartResponseDto) {
//        log.debug("Request to create cart: {}", cartResponseDto);
//        return cartServiceImpl.insert(cartResponseDto);
//    }
//
//    @Operation(summary = "Get cart by user id")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200",
//                    description = "Found the cart",
//                    content = {@Content(mediaType = "application/json",
//                            schema = @Schema(implementation = CartResponseDto.class))}),
//            @ApiResponse(responseCode = "404",
//                    description = "Cart not found",
//                    content = @Content),
//            @ApiResponse(responseCode = "500",
//                    description = "Internal server error")})
//    @GetMapping(value = "/{userId}")
//    @ResponseStatus(HttpStatus.OK)
//    public ResponseEntity<CartResponseDto> getByUserId(@PathVariable Long userId) {
//        log.debug("Request to get cart for user id: {}", userId);
//        CartResponseDto cartResponseDto = cartServiceImpl.getById(userId);
//        return ResponseEntity.ok(cartResponseDto);
//    }
}
