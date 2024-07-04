package com.example.onlineshopproject.service;

import com.example.onlineshopproject.configuration.MapperConfiguration;
import com.example.onlineshopproject.dto.ProductDto;
import com.example.onlineshopproject.dto.ProductRequestDto;
import com.example.onlineshopproject.dto.ProductResponceDto;
import com.example.onlineshopproject.entity.CategoryEntity;
import com.example.onlineshopproject.entity.ProductEntity;
import com.example.onlineshopproject.mapper.Mappers;
import com.example.onlineshopproject.query.ProductCount;
import com.example.onlineshopproject.repository.CategoryRepository;
import com.example.onlineshopproject.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ProductService {
    private static final Logger log = LoggerFactory.getLogger(ProductService.class);
    private final ProductRepository productRepository;
    private final Mappers mappers;
    private final CategoryRepository categoryRepository;
    public List<ProductDto> getProducts(Long categoryId, Double minPrice, Double maxPrice, Boolean isDiscount,
                                        String sort){
        log.info("categoryId = " + categoryId);
        log.info("minPrice = " + minPrice);
        log.info("maxPrice = " + maxPrice);
        log.info("isDiscount = " + isDiscount);
        log.info("sort = " + sort);//name(asc|desc); price(asc|desc)
        CategoryEntity categoryEntity = categoryRepository.findById(categoryId).orElse(null);
        List<ProductEntity> productEntityList = productRepository.findProductByFilter(categoryEntity, minPrice,
                maxPrice, isDiscount, sort);
        List<ProductDto> productDtoList = MapperConfiguration.convertList(productEntityList,
                mappers::convertToProductDto);
        return productDtoList;
    }
    public List<ProductCount> getTop10Products(String status){
        List<ProductCount> productCountList = (List<ProductCount>)(List<?>)productRepository.findTop10Products(status);
        return productCountList;
    }
    public List<ProductResponceDto> getProduct() {
        List<ProductEntity> productEntityList = productRepository.findAll();
        List<ProductResponceDto> productResponceDtoList = MapperConfiguration.convertList(productEntityList,
                mappers::convertToProductResponceDto);
        return productResponceDtoList;
    }

    public ProductResponceDto getProductById(Long id) {
        Optional<ProductEntity> productOptional = productRepository.findById(id);
        ProductResponceDto productResponceDto = null;
        if (productOptional.isPresent()) {
            productResponceDto = productOptional.map(mappers::convertToProductResponceDto).orElse(null);
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