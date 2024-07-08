package com.example.onlineshopproject.controller;

import com.example.onlineshopproject.dto.ProductCountDto;
import com.example.onlineshopproject.dto.ProductRequestDto;
import com.example.onlineshopproject.dto.ProductResponseDto;
import com.example.onlineshopproject.service.ProductServiceImpl;
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
    private final ProductServiceImpl productServiceImpl;
    @Operation(summary = "Get products")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Products count found successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductCountDto.class))})})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<ProductResponseDto> getAll(
            @RequestParam(value = "category", required = false) Long categoryId,
            @RequestParam(value = "minPrice", required = false) Double minPrice,
            @RequestParam(value = "maxPrice", required = false) Double maxPrice,
            @RequestParam(value = "isDiscount", required = false, defaultValue = "false") Boolean isDiscount,
            @RequestParam(value = "sort", required = false) String sort
    ){
        List<ProductResponseDto> productDtoList = productServiceImpl.findByFilter(
                categoryId,
                minPrice,
                maxPrice,
                isDiscount,
                sort
        );
        return productDtoList;
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
    @GetMapping(value = "/{productId}")
    @ResponseStatus(HttpStatus.OK)
    public ProductResponseDto getById(@PathVariable @Positive(message = "Product id must be a positive " +
            "number") Long id) {
        return productServiceImpl.getById(id);
    }

    @Operation(summary = "Delete a product", description = "Deletes a product by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Product update successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductResponseDto.class))}),
            @ApiResponse(responseCode = "404",
                    description = "Product not found",
                    content = @Content)})
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping(value = "/{productId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable @Positive(message = "Product id must be a positive " +
            "number") Long id) {
        productServiceImpl.deleteById(id);
    }

    @Operation(summary = "Insert a product", description = "Inserts a new product with the provided details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Product created successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductResponseDto.class))}),
            @ApiResponse(responseCode = "400",
                    description = "Invalid request body",
                    content = @Content)})
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void insert(@RequestBody @Valid ProductRequestDto productRequestDto) {
        productServiceImpl.insert(productRequestDto);
    }

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
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping(value = "/{productId}")
    @ResponseStatus(HttpStatus.OK)
    @Validated
    public void update(@RequestBody @Valid ProductRequestDto productRequestDto, @PathVariable @Positive(message = "Product id must be a positive " +
            "number") Long id) {
        productServiceImpl.update(productRequestDto, id);
    }

    @Operation(summary = "Get top 10 products")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Top 10 products found successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductCountDto.class))})})
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/top10")
    public List<ProductCountDto> getTop10(@RequestParam(value = "status", required = false) String status){
        return productServiceImpl.getTop10(status);
    }
}