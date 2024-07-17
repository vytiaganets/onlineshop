package com.example.onlineshopproject.service;

import com.example.onlineshopproject.configuration.MapperConfiguration;
import com.example.onlineshopproject.dto.*;
import com.example.onlineshopproject.entity.CategoryEntity;
import com.example.onlineshopproject.entity.ProductEntity;
import com.example.onlineshopproject.exceptions.ProductInvalidArgumentException;
import com.example.onlineshopproject.exceptions.ProductNotFoundException;
import com.example.onlineshopproject.mapper.Mappers;
import com.example.onlineshopproject.query.ProductPending;
import com.example.onlineshopproject.query.ProductProfit;
import com.example.onlineshopproject.repository.CategoryRepository;
import com.example.onlineshopproject.repository.ProductRepository;
import com.fasterxml.jackson.core.io.BigDecimalParser;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

    public List<ProductResponseDto> getAll(Long categoryId, Double minPrice, Double maxPrice, Boolean isDiscount,
                                           String sort) {
        log.debug("Attempting receive all products.");
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
        log.debug("Returning all products: {}", productDtoList.size());
        return productDtoList;
    }

    public List<ProductCountDto> getTop10(String status) {
        log.debug("Receiving top 10 products by status", status);
        List<String> stringList = productRepository.findTop10Products(status);
        List<ProductCountDto> productCountDtoList = new ArrayList<>();
        for (String entry : stringList) {
            String[] string = entry.split(",");
            ProductCountDto productCountDto = new ProductCountDto(
                    Long.parseUnsignedLong(string[0]),
                    string[1],
                    Integer.valueOf(string[2]),
                    BigDecimalParser.parseWithFastParser(string[3]));
            productCountDtoList.add(productCountDto);
        }
        log.info("Returning top 10 products by status", productCountDtoList.size());
        return productCountDtoList;
    }

    public List<ProductResponseDto> getAll() {
        log.debug("Obtaining all products: {}");
        List<ProductEntity> productEntityList = productRepository.findAll();
        List<ProductResponseDto> productResponseDtoList = MapperConfiguration.convertList(productEntityList,
                mappers::convertToProductResponseDto);
        log.debug("Returning all products: {}", productResponseDtoList.size());
        return productResponseDtoList;
    }


    public ProductResponseDto getById(Long productId) {
        log.debug("Attempting product with id: {}", productId);
        ProductEntity productEntity = productRepository.findById(productId).orElse(null);
        if (productEntity != null) {
            return mappers.convertToProductResponseDto(productEntity);
        } else {
            log.error("Product with id {} not found.", productId);
            throw new ProductNotFoundException("Data not found in database.");
        }
    }

    public void deleteById(Long productId) {
        log.debug("Attempting delete product by id: {}", productId);
        if (productRepository.findById(productId).isPresent()) {
            productRepository.findById(productId).ifPresent(productRepository::delete);
        } else {
            log.error("Product with id not found: {}", productId);
            throw new ProductNotFoundException("Data not found in database.");
        }
    }


    public void insert(ProductRequestDto productRequestDto) {
        log.debug("Attempting insert product: {}", productRequestDto.getName());
        CategoryEntity categoryEntity =
                categoryRepository.findCategoryEntityByName(productRequestDto.getCategoryEntity());
        if (categoryEntity != null) {
            ProductEntity productEntity = mappers.convertToProductEntity(productRequestDto);
            productEntity.setProductId(0L);
            productEntity.setCategoryEntity(categoryEntity);
            productEntity.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
            productRepository.save(productEntity);
        } else {
            log.error("Product not found", productRequestDto.getName());
            throw new ProductNotFoundException("Data not found in database.");
        }
    }

    public void update(ProductRequestDto productRequestDto, Long productId) {
        log.debug("Attempting update product", productId);
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
                log.error("Can't find the product with id: {}", productId);
                throw new ProductNotFoundException("Data not found in database.");
            }
        } else {
            log.error("Product id not valid: {}", productId);
            throw new ProductInvalidArgumentException("The value is not valid.");
        }
    }


    public List<ProductResponseDto> findByFilter(Long category, BigDecimal minPrice, BigDecimal maxPrice,
                                                 Boolean isDiscount, String sort) {
       log.debug("Attempting find products:{}");
        boolean isCategory = false;
        if (category == null) {
            isCategory = true;
        }
        if (minPrice == null) {
            minPrice = BigDecimal.valueOf(0.00);
        }
        if (maxPrice == null) {
            maxPrice = BigDecimal.valueOf(Double.MAX_VALUE);
        }
        if (sort == null) {
            sort = "Name";
        }
        List<ProductEntity> productEntityList = productRepository.findByFilter(isCategory, category, minPrice,
                maxPrice, isDiscount, sort);
        return mapperConfiguration.convertList(productEntityList, mappers::convertToProductResponseDto);
    }
   public List<ProductPendingDto> findProductsPending(Integer days) throws ParseException {
       log.debug("Attempting pending products by days", days);
       List<String> stringList = productRepository.findProductsPending(days);
       List<ProductPendingDto> productPendingDtoList = new ArrayList<>();
       for (String entry : stringList) {
           String[] string = entry
                   .split(",");
           ProductPendingDto productPendingDto = new ProductPendingDto(
                   Long.parseUnsignedLong(string[0]),
                   string[1],
                   Integer.valueOf(string[2]),
                   Timestamp.valueOf(string[3]));
           productPendingDtoList.add(productPendingDto);
       }
       log.info("Returning pending products by status", productPendingDtoList.size());
       return productPendingDtoList;
   }

   public List<ProductProfitDto> findProductsProfitByPeriod(String period, Integer value) {
       log.debug("Attempting profits products by period: {}", period);
       List<String> stringList = productRepository.findProductsProfitByPeriod(period, value);
       List<ProductProfitDto> productProfitDtoList = new ArrayList<>();
       for (String entry : stringList) {
           String[] string = entry
                   .split(",");
           ProductProfitDto productProfitDto = new ProductProfitDto(
                   Long.parseUnsignedLong(
                            string[0]),
                            string[1],
                            BigDecimalParser.parseWithFastParser(string[2]));
           productProfitDtoList.add(productProfitDto);
       }
       log.info("Returning profits products by status", productProfitDtoList.size());
       return productProfitDtoList;
   }
}