package com.example.onlineshopproject.controller;

import com.example.onlineshopproject.dto.FavoriteRequestDto;
import com.example.onlineshopproject.dto.FavoriteResponseDto;
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

@Tag(name = "Favorite Controller",
        description = "Controller for favorite operations")
public interface FavoriteControllerInterface {
    @Operation(summary = "Get favorite by user id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Favorite found successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = FavoriteResponseDto.class))}),
            @ApiResponse(responseCode = "401",
                    description = "Unauthorized",
                    content = @Content)})
    public Set<FavoriteResponseDto> getByUserId(@PathVariable @Positive(message = "User id must be a positive" +
            " number") Long userId);

    @Operation(summary = "Add a product to favorite", description = "Adds a specified product to the current user's favorites")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Product added successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = FavoriteResponseDto.class))})})
    public void insert(@RequestBody @Valid FavoriteRequestDto favoriteRequestDto,
                       @PathVariable @Positive(message = "User id must be a positive" +
                               " number") Long userId);

    @Operation(summary = "Delete a product from favorites", description = "Delete a specified product from the " +
            "current user's favorites")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",
                    description = "Cart created successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = FavoriteResponseDto.class))})})
    public void deleteByProductId(@RequestParam("userId") @Positive(message = "User id must be a positive" +
            " number") Long userId,
                                  @RequestParam("productId") @Positive(message = "User id must be a positive " +
                                          "number") Long productId);
}
