package com.example.onlineshopproject.service;

import com.example.onlineshopproject.configuration.MapperConfiguration;
import com.example.onlineshopproject.dto.ProductCountDto;
import com.example.onlineshopproject.dto.ProductResponseDto;
import com.example.onlineshopproject.dto.ProductRequestDto;
import com.example.onlineshopproject.entity.CategoryEntity;
import com.example.onlineshopproject.entity.ProductEntity;
import com.example.onlineshopproject.exceptions.NotFoundInDbException;
import com.example.onlineshopproject.mapper.Mappers;
import com.example.onlineshopproject.repository.CategoryRepository;
import com.example.onlineshopproject.repository.ProductRepository;
import com.fasterxml.jackson.core.io.BigDecimalParser;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.module.ResolutionException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private static final Logger log = LoggerFactory.getLogger(ProductServiceImpl.class);
    private final ProductRepository productRepository;
    private final Mappers mappers;
    private final CategoryRepository categoryRepository;
    private final MapperConfiguration mapperConfiguration;

    @Transactional
    public List<ProductResponseDto> getAll(Long categoryId, Double minPrice, Double maxPrice, Boolean isDiscount,
                                           String sort) {
        log.info("categoryId = " + categoryId);
        log.info("minPrice = " + minPrice);
        log.info("maxPrice = " + maxPrice);
        log.info("isDiscount = " + isDiscount);
        log.info("sort = " + sort);//name(asc|desc); price(asc|desc)
        CategoryEntity categoryEntity = categoryRepository.findById(categoryId).orElse(null);
        List<ProductEntity> productEntityList = productRepository.findProductByFilter(categoryEntity, minPrice,
                maxPrice, isDiscount, sort);
        List<ProductResponseDto> productDtoList = MapperConfiguration.convertList(productEntityList,
                mappers::convertToProductResponseDto);
        return productDtoList;
    }

    @Transactional
    public List<ProductCountDto> getTop10(String status) {
        List<String> stringList = productRepository.findTop10Products(status);
        List<ProductCountDto> productCountDtoList = new ArrayList<>();
        for (String entry : stringList) {
            String[] string = entry.split(",");
            ProductCountDto productCountDto = new ProductCountDto(Long.parseUnsignedLong(string[0]),
                    string[1],
                    Integer.valueOf(string[2]),
                    BigDecimalParser.parseWithFastParser(string[3]));
            productCountDtoList.add(productCountDto);
        }
        return productCountDtoList;
    }

    public List<ProductResponseDto> getAll() {
        List<ProductEntity> productEntityList = productRepository.findAll();
        List<ProductResponseDto> productResponseDtoList = MapperConfiguration.convertList(productEntityList,
                mappers::convertToProductResponseDto);
        return productResponseDtoList;
    }

    @Transactional
    public ProductResponseDto getById(Long productId) {
        ProductEntity productEntity = productRepository.findById(productId).orElse(null);
        if (productEntity != null) {
            return mappers.convertToProductResponseDto(productEntity);
        } else {
            throw new NotFoundInDbException("Data not found in database.");
        }
    }

    public void deleteById(Long userId) {
        if (productRepository.findById(userId).isPresent()) {
            productRepository.findById(userId).ifPresent(productRepository::delete);
        } else {
            throw new NotFoundInDbException("Data not found in database.");
        }
    }

    @Transactional
    public void insert(ProductRequestDto productRequestDto) {
        CategoryEntity categoryEntity =
                categoryRepository.findCategoryEntityByName(productRequestDto.getCategoryEntity());
        if (categoryEntity != null) {
            ProductEntity productEntity = mappers.convertToProductEntity(productRequestDto);
            productEntity.setProductId(0L);
            productEntity.setCategoryEntity(categoryEntity);
            productEntity.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
            productRepository.save(productEntity);
        } else {
            throw new NotFoundInDbException("Data not find in database.");
        }
    }

    @Transactional
    public void update(ProductRequestDto productRequestDto, Long productId) {
        if (productId > 0) {
            ProductEntity productEntity = productRepository.findById(productId).orElse(null);
            CategoryEntity categoryEntity =
                    categoryRepository.findCategoryEntityByName(productRequestDto.getCategoryEntity());
            if (productEntity != null && categoryEntity != null) {
                productEntity.setName(productRequestDto.getName());
                productEntity.setDescription(productRequestDto.getDescription());
                productEntity.setPrice(productRequestDto.getPrice());
                productEntity.setDiscountPrice(productRequestDto.getDiscountPrice());
                productEntity.setImageUrl(productRequestDto.getImageUrl());
                productEntity.setCategoryEntity(categoryEntity);
                productEntity.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
                productRepository.save(productEntity);
            } else {
                throw new NotFoundInDbException("Data not found in database.");
            }
        } else {
            throw new ResolutionException("The value is not valid.");
        }
    }

    @Transactional
    public List<ProductResponseDto> findByFilter(Long category, Double minPrice, Double maxPrice,
                                                 Boolean isDiscount, String sort) {
        boolean isCategory = false;
        if (category == null) {
            isCategory = true;
        }
        if (minPrice == null) {
            minPrice = 0.00;
        }
        if (maxPrice == null) {
            maxPrice = Double.MAX_VALUE;
        }
        if (sort == null) {
            sort = "Name";
        }
        List<ProductEntity> productEntityList = productRepository.findProductByFilter(isCategory, category, minPrice,
                maxPrice, isDiscount, sort);
        return mapperConfiguration.convertList(productEntityList, mappers::convertToProductResponseDto);
    }
}