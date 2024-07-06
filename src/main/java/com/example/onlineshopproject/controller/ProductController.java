package com.example.onlineshopproject.controller;

import com.example.onlineshopproject.dto.ProductRequestDto;
import com.example.onlineshopproject.dto.ProductResponseDto;
import com.example.onlineshopproject.service.ProductService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
@Validated
public class ProductController {
    private final ProductService productService;


    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProductResponseDto> getProduct() {
        return productService.getProduct();
    }

    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductResponseDto getProductById(@PathVariable @Positive(message = "Product id must be a positive " +
            "number") Long id) {
        return productService.getProductById(id);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProductById(@PathVariable @Positive(message = "Product id must be a positive " +
            "number") Long id) {
        productService.deleteProductById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void insertProduct(@RequestBody @Valid ProductRequestDto productRequestDto) {
        productService.insertProduct(productRequestDto);
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Validated
    public void updateProduct(@RequestBody @Valid ProductRequestDto productRequestDto, @PathVariable @Positive(message = "Product id must be a positive " +
            "number")Long id) {
        productService.updateProduct(productRequestDto, id);
    }
}