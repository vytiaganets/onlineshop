package com.example.onlineshopproject.service;

import com.example.onlineshopproject.dto.*;
import com.example.onlineshopproject.entity.CategoryEntity;
import com.example.onlineshopproject.entity.ProductEntity;
import com.example.onlineshopproject.exceptions.ProductInvalidArgumentException;
import com.example.onlineshopproject.exceptions.ProductNotFoundException;
import com.example.onlineshopproject.mapper.Mappers;
import com.example.onlineshopproject.repository.CategoryRepository;
import com.example.onlineshopproject.repository.ProductRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductServiceImplTest {
    @Mock
    private ProductRepository productRepositoryMock;
    @Mock
    private CategoryRepository categoryRepositoryMock;
    @Mock
    private Mappers mappersMock;
    @InjectMocks
    private ProductServiceImpl productServiceMock;
    ProductNotFoundException productNotFoundException;
    ProductInvalidArgumentException productInvalidArgumentException;
    private ProductResponseDto productResponseDto;
    private ProductRequestDto productRequestDto, incorrectProductRequestDto;
    private ProductEntity productEntity, productEntityToInsert;
    private CategoryEntity categoryEntity;
    private ProductCountDto productCountDto;
    @Autowired
    private ObjectMapper objectMapper;
    @BeforeEach
    void setUp(){
        productResponseDto = ProductResponseDto
                .builder()
                .productId(1L)
                .name("Name")
                .description("Description")
                .price(new BigDecimal("10.00"))
                .imageURL("http://localhost/cart.jpg")
                .createdAt(Timestamp.valueOf(LocalDateTime.now()))
                .categoryResponseDto(new CategoryResponseDto(1L, "Category"))
                .build();
        productEntity = new ProductEntity(1L,
                "Product name",
                "Product description",
                new BigDecimal("10.00"),
                "http://localhost/product.jpg",
                new BigDecimal("1.00"),
                Timestamp.valueOf(LocalDateTime.now()),
                Timestamp.valueOf(LocalDateTime.now()),
                new CategoryEntity(1L,
                        "Category name",
                        null),
                null,
                null,
                null);
        productEntityToInsert = new ProductEntity(null,
                "Product name",
                "Product description",
                new BigDecimal("10.00"),
                "http://localhost/product.jpg",
                new BigDecimal("1.00"),
                Timestamp.valueOf(LocalDateTime.now()),
                Timestamp.valueOf(LocalDateTime.now()),
                new CategoryEntity(1L,
                        "Category name",
                        null),
                null,
                null,
                null);
        productCountDto = new ProductCountDto(
                1L,
                "Spade",
                3,
                new BigDecimal(7.29));
        categoryEntity = new CategoryEntity(1L,
                "Category namee",
                null);
        productRequestDto = ProductRequestDto
                .builder()
                .name("Product name")
                .description("Product description")
                .price(new BigDecimal("10.00"))
                .discountPrice(new BigDecimal("1.00"))
                .imageUrl("http://localhost/cart.jpg")
                .categoryEntity( "Category")
                .build();
        incorrectProductRequestDto = ProductRequestDto
                .builder()
                .name("Incorrect product name")
                .description("Incorrect product description")
                .price(new BigDecimal("10.00"))
                .discountPrice(new BigDecimal("1.00"))
                .imageUrl("http://localhost/cart.jpg")
                .categoryEntity( "Incorrect category")
                .build();
    }
    @Test
    void getProductById(){
        Long productId = 1L;
        Long incorrectProductId = 9L;
        when(productRepositoryMock.findById(anyLong())).thenReturn(Optional.of(productEntity));
        when(mappersMock.convertToProductResponseDto(any(ProductEntity.class))).thenReturn(productResponseDto);
        ProductResponseDto actualProductResponseDto = productServiceMock.getById(productId);
        verify(productRepositoryMock, times(1)).findById(productId);
        verify(mappersMock, times(1)).convertToProductResponseDto(any(ProductEntity.class));
        assertEquals(productResponseDto.getProductId(), actualProductResponseDto.getProductId());
        when(productRepositoryMock.findById(incorrectProductId)).thenReturn(Optional.empty());
        productNotFoundException = assertThrows(ProductNotFoundException.class,
                () -> productServiceMock.getById(incorrectProductId));
        assertEquals("Data not found in database.", productNotFoundException.getMessage());
    }
    @Test
    void deleteById(){
        Long productId = 1L;
        Long incorrectProductId = 9L;
        when(productRepositoryMock.findById(productId)).thenReturn(Optional.of(productEntity));
        productServiceMock.deleteById(productId);
        verify(productRepositoryMock, times(1)).delete(productEntity);
        when(productRepositoryMock.findById(incorrectProductId)).thenReturn(Optional.empty());
        productNotFoundException = assertThrows(ProductNotFoundException.class,
                () -> productServiceMock.deleteById(incorrectProductId));
        assertEquals("Data not found in database.", productNotFoundException.getMessage());
    }
    @Test
    void insert(){
        when(categoryRepositoryMock.findCategoryEntityByName(productRequestDto.getCategoryEntity())).thenReturn(categoryEntity);
        when(mappersMock.convertToProductEntity(any(ProductRequestDto.class))).thenReturn(productEntityToInsert);
        productEntityToInsert.setProductId(0L);
        productEntityToInsert.setCategoryEntity(categoryEntity);
        productEntityToInsert.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        productServiceMock.insert(productRequestDto);
        verify(mappersMock, times(1)).convertToProductEntity(any(ProductRequestDto.class));
        verify(productRepositoryMock, times(1)).save(productEntityToInsert);
        when(categoryRepositoryMock.findCategoryEntityByName(incorrectProductRequestDto.getCategoryEntity())).thenReturn(null);
        productNotFoundException = assertThrows(ProductNotFoundException.class,
                () -> productServiceMock.insert(incorrectProductRequestDto));
        assertEquals("Data not found in database.", productNotFoundException.getMessage());
    }
    @Test
    void update(){
        Long productId = 1L;
        Long incorrectId = 9L;
        Long negativeProductId = -1L;
        when(productRepositoryMock.findById(productId)).thenReturn(Optional.of(productEntity));
        when(categoryRepositoryMock.findCategoryEntityByName(anyString())).thenReturn(categoryEntity);
        productEntity.setName(productRequestDto.getName());
        productEntity.setPrice(productRequestDto.getPrice());
        productEntity.setDescription(productRequestDto.getDescription());
        productEntity.setDiscountPrice(productRequestDto.getDiscountPrice());
        productEntity.setImageUrl(productRequestDto.getImageUrl());
        productEntity.setCategoryEntity(categoryEntity);
        productEntity.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
        productServiceMock.update(productRequestDto, productId);
        verify(productRepositoryMock, times(1)).save(any(ProductEntity.class));
        productNotFoundException = assertThrows(ProductNotFoundException.class,
                () -> productServiceMock.update(incorrectProductRequestDto, incorrectId));
        assertEquals("Data not found in database.", productNotFoundException.getMessage());
        productInvalidArgumentException = assertThrows(ProductInvalidArgumentException.class,
                () -> productServiceMock.update(incorrectProductRequestDto, negativeProductId));
        assertEquals("The value is not valid.", productInvalidArgumentException.getMessage());
    }
    @Test
    void getTop10Products() throws JsonProcessingException {
        String sort = "Price";
        String expected = "1,Spade,125,303.75";
        when(productRepositoryMock.findTop10Products(sort)).thenReturn(List.of(expected));
        List<ProductCountDto> actualProductResponseDto = productServiceMock.getTop10(sort);
        verify(productRepositoryMock, times(1)).findTop10Products(sort);
        assertEquals(productResponseDto.getProductId(), actualProductResponseDto.get(0).getProductId());
    }
//    @Test
//    void findByFilter(){
//        Long category = 1L;
//        BigDecimal minPrice = BigDecimal.valueOf(0.00);
//        BigDecimal maxPrice = BigDecimal.valueOf(100.00);
//        Boolean isDiscount = true;
//        String sort = "Price";
//        when(productRepositoryMock.findByFilter(false, category, minPrice, maxPrice, isDiscount, sort)).thenReturn((List<String>) productEntity);
//        when(mappersMock.convertToProductResponseDto(any(ProductEntity.class))).thenReturn(productResponseDto);
//        List<ProductResponseDto> actualProductResponseDto = productServiceMock.findByFilter(category, minPrice, maxPrice, isDiscount, sort);
//        verify(mappersMock, times(1)).convertToProductResponseDto(any(ProductEntity.class));
//        assertEquals(productResponseDto.getProductId(), actualProductResponseDto.get(0).getProductId());
//    }
}