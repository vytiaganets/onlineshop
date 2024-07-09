package com.example.onlineshopproject.controller;

import com.example.onlineshopproject.dto.ProductCountDto;
import com.example.onlineshopproject.dto.ProductRequestDto;
import com.example.onlineshopproject.dto.ProductResponseDto;
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

import java.util.List;

@Tag(name = "Product Controller",
        description = "Controller for product operations")
public interface ProductControllerInterface {
    @Operation(summary = "Get products")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Products count found successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductCountDto.class))})})
    public List<ProductResponseDto> getAll(
            @RequestParam(value = "category", required = false) Long categoryId,
            @RequestParam(value = "minPrice", required = false) Double minPrice,
            @RequestParam(value = "maxPrice", required = false) Double maxPrice,
            @RequestParam(value = "isDiscount", required = false, defaultValue = "false") Boolean isDiscount,
            @RequestParam(value = "sort", required = false) String sort
    );
    @Operation(summary = "Get product by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Product found successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductResponseDto.class))}),
            @ApiResponse(responseCode = "404",
                    description = "Product not found",
                    content = @Content)})
    public ProductResponseDto getById(@PathVariable @Positive(message = "Product id must be a positive " +
            "number") Long productId);
    @Operation(summary = "Delete a product", description = "Deletes a product by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Product update successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductResponseDto.class))}),
            @ApiResponse(responseCode = "404",
                    description = "Product not found",
                    content = @Content)})
    public void deleteById(@PathVariable @Positive(message = "Product id must be a positive " +
            "number") Long productId);
    @Operation(summary = "Insert a product", description = "Inserts a new product with the provided details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Product created successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductResponseDto.class))}),
            @ApiResponse(responseCode = "400",
                    description = "Invalid request body",
                    content = @Content)})
    public void insert(@RequestBody @Valid ProductRequestDto productRequestDto);
    @Operation(summary = "Update a product", description = "Updates an existing product by id with the provided new " +
            "details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Product update successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductResponseDto.class))}),
            @ApiResponse(responseCode = "400",
                    description = "Invalid request body",
                    content = @Content),
            @ApiResponse(responseCode = "404",
                    description = "Product not found",
                    content = @Content)})
    public void update(@RequestBody @Valid ProductRequestDto productRequestDto, @PathVariable @Positive(message = "Product id must be a positive " +
            "number") Long productId);
    @Operation(summary = "Get top 10 products")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Top 10 products found successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductCountDto.class))})})
    public List<ProductCountDto> getTop10(@RequestParam(value = "status", required = true) String status);
}
