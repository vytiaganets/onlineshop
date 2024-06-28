package com.example.onlineshopproject.controller;

import com.example.onlineshopproject.dto.ProductRequestDto;
import com.example.onlineshopproject.dto.ProductResponceDto;
import com.example.onlineshopproject.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProductResponceDto> getProduct(){
        return productService.getProduct();
    }
    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductResponceDto getProductById(@PathVariable Long id){
        return productService.getProductById(id);
    }
    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProductById(@PathVariable Long id){
        productService.deleteProductById(id);
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void insertProducct(@RequestBody ProductRequestDto productRequestDto){
        productService.insertProduct(productRequestDto);
    }
    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updatePrroduct(@RequestBody ProductRequestDto productRequestDto, @PathVariable Long id){
        productService.updateProduct(productRequestDto, id);
    }
}





























