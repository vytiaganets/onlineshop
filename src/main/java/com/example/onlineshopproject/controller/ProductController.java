package com.example.onlineshopproject.controller;

import com.example.onlineshopproject.dto.ProductRequestDto;
import com.example.onlineshopproject.dto.ProductResponseDto;
import com.example.onlineshopproject.service.ProductService;
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Tag(name = "Product Controller",
        description = "Controller for product operations")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
@Validated
public class ProductController {
    private final ProductService productService;

    @Operation(summary = "Get a product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Product found successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductResponseDto.class))}),
            @ApiResponse(responseCode = "404",
                    description = "Product not found",
                    content = @Content)})
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProductResponseDto> getProduct() {
        return productService.getProduct();
    }
    @Operation(summary = "Get product by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Product found successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductResponseDto.class))}),
            @ApiResponse(responseCode = "404",
                    description = "Product not found",
                    content = @Content)})
    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductResponseDto getProductById(@PathVariable @Positive(message = "Product id must be a positive " +
            "number") Long id) {
        return productService.getProductById(id);
    }
    @Operation(summary = "Delete a product", description = "Deletes a product by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",
                    description = "Product deleted successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductResponseDto.class))})})
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProductById(@PathVariable @Positive(message = "Product id must be a positive " +
            "number") Long id) {
        productService.deleteProductById(id);
    }
    @Operation(summary = "Insert a product", description = "Inserts a new product with the provided details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Product created successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductResponseDto.class))})})
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void insertProduct(@RequestBody @Valid ProductRequestDto productRequestDto) {
        productService.insertProduct(productRequestDto);
    }
    @Operation(summary = "Update a product", description = "Updates an existing product by id with the provided new " +
            "details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Product update successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductResponseDto.class))})})
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Validated
    public void updateProduct(@RequestBody @Valid ProductRequestDto productRequestDto, @PathVariable @Positive(message = "Product id must be a positive " +
            "number")Long id) {
        productService.updateProduct(productRequestDto, id);
    }
}