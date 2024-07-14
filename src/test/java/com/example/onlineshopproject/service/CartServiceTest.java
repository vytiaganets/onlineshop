package com.example.onlineshopproject.service;

import com.example.onlineshopproject.dto.*;
import com.example.onlineshopproject.entity.*;
import com.example.onlineshopproject.enums.UserRole;
import com.example.onlineshopproject.exceptions.NotFoundInDbException;
import com.example.onlineshopproject.mapper.Mappers;
import com.example.onlineshopproject.repository.CartItemRepository;
import com.example.onlineshopproject.repository.CartRepository;
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
public class CartServiceTest {
    @Mock
    private ProductRepository productRepositoryMock;
    @Mock
    private UserRepository userRepositoryMock;
    @Mock
    private CartRepository cartRepositoryMock;
    @Mock
    private CartItemRepository cartItemRepositoryMock;
    @Mock
    private Mappers mappersMock;
    @InjectMocks
    private CartService cartServiceMock;
    NotFoundInDbException notFoundInDbException;
    private UserEntity userEntity;
    private CartEntity cartEntity;
    private CartItemEntity cartItemEntity;
    private ProductEntity productEntity;
    private ProductResponseDto productResponseDto;
    private UserResponseDto userResponseDto;
    private CartResponseDto cartResponseDto;
    private CartItemResponseDto cartItemResponseDto;
    private CartItemRequestDto cartItemRequestDto, incorrectCartItemRequestDto;

    @BeforeEach
    void setUp() {
        userEntity = new UserEntity(1L,
                "Andrii Kpi",
                "andrii@ukr.net",
                "123456789012",
                "1234",
                UserRole.USER,
                null,
                null,
                null);
        cartEntity = new CartEntity(1L,
                null,
                userEntity);
        productEntity = new ProductEntity(1L,
                "Product name",
                "Product description",
                new BigDecimal("10.00"),
                new BigDecimal("1.00"),
                "http://localhost/product.jpg",
                Timestamp.valueOf(LocalDateTime.now()),
                Timestamp.valueOf(LocalDateTime.now()),
                new CategoryEntity(1L,
                        "Category name",
                        null),
                null,
                null,
                null);
        cartItemEntity = new CartItemEntity(1L,
                cartEntity, productEntity, 5);
        Set<CartItemEntity> cartItemEntitySet = new HashSet<>();
        cartItemEntitySet.add(cartItemEntity);
        cartEntity.setCartItemEntitySet(cartItemEntitySet);
        userEntity.setCartEntity(cartEntity);

        userResponseDto = UserResponseDto
                .builder()
                .userId(1L)
                .name("Andrey Kpi")
                .email("andey.kpi@ukr.net")
                .phone("123456789012")
                .password("1234")
                .userRole(UserRole.USER)
                .build();
        cartResponseDto = CartResponseDto
                .builder()
                .cartId(1L)
                .userResponseDto(userResponseDto)
                .build();
        productResponseDto = ProductResponseDto
                .builder()
                .productId(1L)
                .name("Name")
                .description("Description")
                .price(new BigDecimal("10.00"))
                .imageURL("http://localhost/cart.jpg")
                .createAt(Timestamp.valueOf(LocalDateTime.now()))
                .categoryResponseDto(new CategoryResponseDto(1L, "Category"))
                .build();
        cartItemResponseDto = CartItemResponseDto
                .builder()
                .cartItemId(1L)
                .cartResponseDto(cartResponseDto)
                .productResponseDto(productResponseDto)
                .quantity(7)
                .build();
        cartItemRequestDto = CartItemRequestDto
                .builder()
                .productId(1L)
                .quantity(7)
                .build();
        incorrectCartItemRequestDto = CartItemRequestDto
                .builder()
                .productId(9L)
                .quantity(2)
                .build();
    }

    @Test
    void getByUserId() {
        Long userId = 1L;
        Long incorrectUserId = 9L;
        when(userRepositoryMock.findById(userId)).thenReturn(Optional.of(userEntity));
        when(mappersMock.convertToCartItemResponseDto(any(CartItemEntity.class))).thenReturn(cartItemResponseDto);
        Set<CartItemResponseDto> cartItemResponseDtoSet = new HashSet<>();
        cartItemResponseDtoSet.add(cartItemResponseDto);
        Set<CartItemResponseDto> actualCartItemSet = cartServiceMock.getByUserId(userId);
        verify(userRepositoryMock, times(1)).findById(userId);
        verify(mappersMock, times(1)).convertToCartItemResponseDto(any(CartItemEntity.class));
        assertFalse(actualCartItemSet.isEmpty());
        assertEquals(cartItemResponseDtoSet.size(), actualCartItemSet.size());
        assertEquals(cartItemResponseDtoSet.hashCode(), actualCartItemSet.hashCode());
        when(userRepositoryMock.findById(incorrectUserId)).thenReturn((Optional.empty()));
        notFoundInDbException = assertThrows(NotFoundInDbException.class, () -> cartServiceMock.getByUserId(incorrectUserId));
        assertEquals("Data not found in database", notFoundInDbException.getMessage());

    }
}

































