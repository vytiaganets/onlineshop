package com.example.onlineshopproject.controller;

import com.example.onlineshopproject.dto.ProductCountDto;
import com.example.onlineshopproject.dto.ProductResponseDto;
import com.example.onlineshopproject.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@Tag(name = "Product count Controller",
        description = "Controller for product count operations")
@Slf4j
@RestController
@RequiredArgsConstructor
public class ProductCountController {
    private final ProductService productService;
    @Operation(summary = "Get products")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Products count found successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductCountDto.class))})})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<ProductResponseDto> getProducts(
            @RequestParam(value = "category", required = false) Long categoryId,
            @RequestParam(value = "minPrice", required = false) Double minPrice,
            @RequestParam(value = "maxPrice", required = false) Double maxPrice,
            @RequestParam(value = "isDiscount", required = false, defaultValue = "false") Boolean isDiscount,
            @RequestParam(value = "sort", required = false) String sort
    ){
        List<ProductResponseDto> productDtoList = productService.findProductByFilter(
                categoryId,
                minPrice,
                maxPrice,
                isDiscount,
                sort
        );
        return productDtoList;
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
    public List<ProductCountDto> getTop10Products(@RequestParam(value = "status", required = false) String status){
        return productService.getTop10Products(status);
    }
}
