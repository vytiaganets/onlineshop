package com.example.onlineshopproject.service;

import com.example.onlineshopproject.configuration.MapperConfiguration;
import com.example.onlineshopproject.dto.ProductRequestDto;
import com.example.onlineshopproject.dto.ProductResponceDto;
import com.example.onlineshopproject.entity.CategoryEntity;
import com.example.onlineshopproject.entity.ProductEntity;
import com.example.onlineshopproject.mapper.Mappers;
import com.example.onlineshopproject.repository.CategoryRepository;
import com.example.onlineshopproject.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final Mappers mappers;
    private final CategoryRepository categoryRepository;

    public List<ProductResponceDto> getProduct() {
        List<ProductEntity> productEntityList = productRepository.findAll();
        List<ProductResponceDto> productResponceDtoList = MapperConfiguration.convertList(productEntityList,
                mappers::convertToProductDto);
        return productResponceDtoList;
    }

    public ProductResponceDto getProductById(Long id) {
        Optional<ProductEntity> productOptional = productRepository.findById(id);
        ProductResponceDto productResponceDto = null;
        if (productOptional.isPresent()) {
            productResponceDto = productOptional.map(mappers::convertToProductDto).orElse(null);
        }
        return productResponceDto;
    }

    public void deleteProductById(Long id) {
        Optional<ProductEntity> product = productRepository.findById(id);
        if (product.isPresent()) {
            productRepository.deleteById(id);
        }
    }

    public void insertProduct(ProductRequestDto productRequestDto) {
        if (productRequestDto.getCategoryId() != null) {
            ProductEntity newProduct = mappers.convertToProductEntity(productRequestDto);
            newProduct.setProductId(0L);
            ProductEntity savedProducts = productRepository.save(newProduct);
        }
    }

    public void updateProduct(ProductRequestDto productRequestDto, Long id) {
        if (id > 0) {
            Optional<ProductEntity> productEntityOptional = productRepository.findById(id);
            if (!productEntityOptional.isPresent()) {
                throw new RuntimeException("Product not founf with id: " + id);
            } else {
                ProductEntity productEntity = productEntityOptional.get();
                productEntity.setName(productRequestDto.getName());
                productEntity.setDescription(productRequestDto.getDescription());
                productEntity.setImageUrl(productRequestDto.getImageUrl());
                productEntity.setPrice(productRequestDto.getPrice());
                CategoryEntity categoryEntity =
                        categoryRepository.findById(productRequestDto.getCategoryId()).orElseThrow(NoSuchFieldError::new);
                productEntity.setCategoryEntity(categoryEntity);
                productEntity.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
                productRepository.save(productEntity);
                productEntity.setDiscountPrice(productRequestDto.getDiscountPrice());
            }
        }
    }
}