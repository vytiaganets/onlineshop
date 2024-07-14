package com.example.onlineshopproject.controller;

import com.example.onlineshopproject.dto.ProductCountDto;
import com.example.onlineshopproject.dto.ProductRequestDto;
import com.example.onlineshopproject.dto.ProductResponseDto;
import com.example.onlineshopproject.exceptions.ProductInvalidArgumentException;
import com.example.onlineshopproject.exceptions.ProductNotFoundException;
import com.example.onlineshopproject.service.ProductService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
@Validated
public class ProductController implements ProductControllerInterface {
    private final ProductService productService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<ProductResponseDto> getAll(
            @RequestParam(value = "category", required = false) Long categoryId,
            @RequestParam(value = "minPrice", required = false) Double minPrice,
            @RequestParam(value = "maxPrice", required = false) Double maxPrice,
            @RequestParam(value = "isDiscount", required = false, defaultValue = "false") Boolean isDiscount,
            @RequestParam(value = "sort", required = false) String sort
    ) {
        List<ProductResponseDto> productDtoList = productService.findByFilter(
                categoryId,
                minPrice,
                maxPrice,
                isDiscount,
                sort
        );
        return productDtoList;
    }

    @GetMapping(value = "/{productId}")
    @ResponseStatus(HttpStatus.OK)
    public ProductResponseDto getById(@PathVariable @Positive(message = "Product id must be a positive " +
            "number") Long productId) {
        return productService.getById(productId);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping(value = "/{productId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable @Positive(message = "Product id must be a positive " +
            "number") Long productId) {
        productService.deleteById(productId);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void insert(@RequestBody @Valid ProductRequestDto productRequestDto) {
        productService.insert(productRequestDto);
    }

//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping(value = "/{productId}")
    @ResponseStatus(HttpStatus.OK)
    @Validated
    public void update(@RequestBody @Valid ProductRequestDto productRequestDto, @PathVariable @Positive(message = "Product id must be a positive " +
            "number") Long productId) {
        productService.update(productRequestDto, productId);
    }

    ///    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/top10")
    public List<ProductCountDto> getTop10(@RequestParam(value = "status", required = true) String status) {
        return productService.getTop10(status);
    }
    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<String> handleProductNotFoundException(ProductNotFoundException productNotFoundException) {
        log.error("Product not found:{}", productNotFoundException.getMessage());
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception exception) {
        log.error("Internal server error: {}", exception.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error.");
    }
}