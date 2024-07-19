package com.example.onlineshopproject.service;

import com.example.onlineshopproject.dto.*;
import com.example.onlineshopproject.entity.CategoryEntity;
import com.example.onlineshopproject.entity.FavoriteEntity;
import com.example.onlineshopproject.entity.ProductEntity;
import com.example.onlineshopproject.entity.UserEntity;
import com.example.onlineshopproject.enums.UserRole;
import com.example.onlineshopproject.exceptions.FavoriteNotFoundException;
import com.example.onlineshopproject.mapper.Mappers;
import com.example.onlineshopproject.repository.FavoriteRepository;
import com.example.onlineshopproject.repository.ProductRepository;
import com.example.onlineshopproject.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FavoritesServiceImplTest {
    @Mock
    private ProductRepository productRepositoryMock;
    @Mock
    private UserRepository userRepositoryMock;
    @Mock
    private FavoriteRepository favoriteRepositoryMock;
    @Mock
    private Mappers mappersMock;
    @InjectMocks
    private FavoriteServiceImpl favoriteServiceMock;
    FavoriteNotFoundException favoriteNotFoundException;
    private UserEntity userEntity;
    private ProductEntity productEntity;
    private FavoriteEntity favoriteEntity;
    private UserResponseDto userResponseDto;
    private UserRequestDto userRequestDto;
    private ProductResponseDto productResponseDto;
    private ProductRequestDto productRequestDto;
    private FavoriteResponseDto favoriteResponseDto;
    private FavoriteRequestDto favoriteRequestDto, incorrectFavoriteRequestDto;
    private Set<FavoriteEntity> favoriteEntitySet = new HashSet<>();
    private Set<FavoriteResponseDto> favoriteResponseDtoSet = new HashSet<>();
    @BeforeEach
    void setUp(){
        userEntity = new UserEntity(1L,
                "Andrii Kpi",
                "andrii@ukr.net",
                "123456789012",
                "1234",
                UserRole.USER,
                null,
                null,
                null);
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
        favoriteEntity = new FavoriteEntity(1L,
                userEntity,
                productEntity);
        favoriteEntitySet.add(favoriteEntity);
        userEntity.setFavoriteEntitySet(favoriteEntitySet);
        userResponseDto = UserResponseDto
                .builder()
                .userId(1L)
                .name("Andrey Kpi")
                .email("andey.kpi@ukr.net")
                .phone("123456789012")
                .password("1234")
                .userRole(UserRole.USER)
                .build();
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
        favoriteResponseDto = FavoriteResponseDto
                .builder()
                .favoriteId(1L)
                .productResponseDto(productResponseDto)
                .userResponseDto(userResponseDto)
                .build();
        favoriteResponseDtoSet.add(favoriteResponseDto);
        favoriteRequestDto = FavoriteRequestDto
                .builder()
                .productId(1L)
                .build();
        incorrectFavoriteRequestDto = FavoriteRequestDto
                .builder()
                .productId(9L)
                .build();
    }
    @Test
    void getByUserId(){
        Long userId = 1L;
        Long incorrectUserId = 9L;
        when(userRepositoryMock.findById(userId)).thenReturn(Optional.of(userEntity));
        when(mappersMock.convertToFavoriteResponseDto(any(FavoriteEntity.class))).thenReturn(favoriteResponseDto);
        Set<FavoriteResponseDto> actualFavoriteResponseDtoSet = favoriteServiceMock.getByUserId(userId);
        verify(userRepositoryMock, times(1)).findById(userId);
        verify(mappersMock, times(1)).convertToFavoriteResponseDto(any(FavoriteEntity.class));
        assertFalse(actualFavoriteResponseDtoSet.isEmpty());
        assertEquals(favoriteResponseDtoSet.size(), actualFavoriteResponseDtoSet.size());
        assertEquals(favoriteResponseDtoSet.hashCode(), actualFavoriteResponseDtoSet.hashCode());
        when(userRepositoryMock.findById(incorrectUserId)).thenReturn(Optional.empty());
        favoriteNotFoundException = assertThrows(FavoriteNotFoundException.class,
                () -> favoriteServiceMock.getByUserId(incorrectUserId));
        assertEquals("Data not found in database.", favoriteNotFoundException.getMessage());
    }
    @Test
    void insert(){
        Long userId = 1L;
        Long incorrectUserId = 9L;
        FavoriteEntity realFavoriteEntity = new FavoriteEntity();
        when(userRepositoryMock.findById(userId)).thenReturn(Optional.of(userEntity));
        when(productRepositoryMock.findById(favoriteRequestDto.getProductId())).thenReturn(Optional.of(productEntity));
        realFavoriteEntity.setProductEntity(productEntity);
        realFavoriteEntity.setUserEntity(userEntity);
        when(favoriteRepositoryMock.save(any(FavoriteEntity.class))).thenReturn(realFavoriteEntity);
        favoriteServiceMock.insert(favoriteRequestDto, userId);
        verify(favoriteRepositoryMock, times(1)).save(any(FavoriteEntity.class));
        when(userRepositoryMock.findById(incorrectUserId)).thenReturn(Optional.empty());
        favoriteNotFoundException = assertThrows(FavoriteNotFoundException.class,
                () -> favoriteServiceMock.insert(favoriteRequestDto, incorrectUserId));
        assertEquals("Data not found in database.", favoriteNotFoundException.getMessage());
        when(productRepositoryMock.findById(incorrectUserId)).thenReturn(Optional.empty());
        favoriteNotFoundException = assertThrows(FavoriteNotFoundException.class,
                () -> favoriteServiceMock.insert(incorrectFavoriteRequestDto, userId));
        assertEquals("Data not found in database.", favoriteNotFoundException.getMessage());
    }
    @Test
    void deleteByProductId(){
        Long userId = 1L;
        Long incorrectUserId = 9L;
        Long productId = 1L;
        Long incorrectProductId = 9L;
        when(userRepositoryMock.findById(userId)).thenReturn(Optional.of(userEntity));
        when(productRepositoryMock.findById(productId)).thenReturn(Optional.of(productEntity));
        favoriteServiceMock.deleteByProductId(userId, productId);
        for(FavoriteEntity favorite : favoriteEntitySet){
            verify(favoriteRepositoryMock, times(1)).delete(favorite);
        }
        when(userRepositoryMock.findById(incorrectUserId)).thenReturn(Optional.empty());
        when(productRepositoryMock.findById(incorrectProductId)).thenReturn(Optional.empty());
        favoriteNotFoundException = assertThrows(FavoriteNotFoundException.class,
                () -> favoriteServiceMock.deleteByProductId(productId,incorrectUserId));
        assertEquals("Data not found in database.", favoriteNotFoundException.getMessage());
        favoriteNotFoundException = assertThrows(FavoriteNotFoundException.class,
                () -> favoriteServiceMock.deleteByProductId(incorrectProductId, userId));
        assertEquals("Data not found in database.", favoriteNotFoundException.getMessage());
    }
}