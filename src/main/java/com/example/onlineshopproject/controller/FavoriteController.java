package com.example.onlineshopproject.controller;

import com.example.onlineshopproject.dto.FavoriteRequestDto;
import com.example.onlineshopproject.dto.FavoriteResponseDto;
import com.example.onlineshopproject.service.FavoriteServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
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

import java.util.Set;

@Tag(name = "Favorite Controller",
        description = "Controller for favorite operations")
@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/favorities")
public class FavoriteController {
    private final FavoriteServiceImpl favoriteServiceImpl;

    @Operation(summary = "Get favorite by user id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Favorite found successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = FavoriteResponseDto.class))}),
            @ApiResponse(responseCode = "401",
                    description = "Unauthorized",
                    content = @Content)})
    @GetMapping(value = "/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public Set<FavoriteResponseDto> getByUserId(@PathVariable @Positive(message = "User id must be a positive" +
            " number") Long userId) {
        return favoriteServiceImpl.getByUserId(userId);
    }

    @Operation(summary = "Add a product to favorite", description = "Adds a specified product to the current user's favorites")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Product added successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = FavoriteResponseDto.class))})})
    @PostMapping(value = "/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public void insert(@RequestBody @Valid FavoriteRequestDto favoriteRequestDto,
                       @PathVariable @Positive(message = "User id must be a positive" +
                                       " number") Long userId) {
        favoriteServiceImpl.insert(favoriteRequestDto, userId);
    }

    @Operation(summary = "Delete a product from favorites", description = "Delete a specified product from the " +
            "current user's favorites")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",
                    description = "Cart created successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = FavoriteResponseDto.class))})})
    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    public void deleteByProductId(@RequestParam("userId") @Positive(message = "User id must be a positive" +
            " number") Long userId,
                                  @RequestParam("productId") @Positive(message = "User id must be a positive " +
                                                  "number") Long productId) {
        favoriteServiceImpl.deleteByProductId(userId, productId);
    }
}
