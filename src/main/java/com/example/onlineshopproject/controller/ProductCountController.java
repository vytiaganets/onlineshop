package com.example.onlineshopproject.controller;

import com.example.onlineshopproject.dto.ProductDto;
import com.example.onlineshopproject.query.ProductCount;
import com.example.onlineshopproject.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
@RequiredArgsConstructor
public class ProductCountController {
    private final ProductService productService;
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<ProductDto> getProducts(
            @RequestParam(value = "category", required = false) Long categoryId,
            @RequestParam(value = "min_price", required = false) Double minPrice,
            @RequestParam(value = "max_price", required = false) Double maxPrice,
            @RequestParam(value = "is_discount", required = false, defaultValue = "false") Boolean isDiscount,
            @RequestParam(value = "sort", required = false) String sort
    ){
        List<ProductDto> productDtoList = productService.getProducts(
                categoryId,
                minPrice,
                maxPrice,
                isDiscount,
                sort
        );
        return productDtoList;
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/top10")
    public List<ProductCount> getTop10Products(@RequestParam(value = "status", required = false) String status){
        return productService.getTop10Products(status);
    }
}
