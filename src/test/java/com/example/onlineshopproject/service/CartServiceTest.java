package com.example.onlineshopproject.service;

import com.example.onlineshopproject.dto.*;
import com.example.onlineshopproject.entity.*;
import com.example.onlineshopproject.enums.UserRole;
import com.example.onlineshopproject.exceptions.CartItemNotFoundException;
import com.example.onlineshopproject.exceptions.CartNotFoundException;
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
    private CartServiceImpl cartServiceMock;
    CartNotFoundException cartNotFoundException;
    CartItemNotFoundException cartItemNotFoundException;
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
                .createdAt(Timestamp.valueOf(LocalDateTime.now()))
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
        cartNotFoundException = assertThrows(CartNotFoundException.class, () -> cartServiceMock.getByUserId(incorrectUserId));
        assertEquals("Cart not found in database.", cartNotFoundException.getMessage());
    }
    @Test
    void insert(){
        Long cartId = 1L;
        Long incorrectCartId = 9L;
        when(userRepositoryMock.findById(cartId)).thenReturn(Optional.of(userEntity));
        when(productRepositoryMock.findById(cartItemRequestDto.getProductId())).thenReturn(Optional.of(productEntity));
        when(cartRepositoryMock.findById(userEntity.getCartEntity().getCartId())).thenReturn(Optional.of(cartEntity));
        CartItemEntity cartItemEntityToInsert = new CartItemEntity();
        cartItemEntityToInsert.setCartEntity(cartEntity);
        cartItemEntityToInsert.setCartItemId(1L);
        cartItemEntityToInsert.setProductEntity(productEntity);
        cartItemEntityToInsert.setQuantity(cartItemRequestDto.getQuantity());
        when(cartItemRepositoryMock.save(any(CartItemEntity.class))).thenReturn(cartItemEntityToInsert);
        cartServiceMock.insert(cartItemRequestDto, cartId);
        verify(cartItemRepositoryMock, times(1)).save(any(CartItemEntity.class));
        when(userRepositoryMock.findById(incorrectCartId)).thenReturn(Optional.empty());
        when(productRepositoryMock.findById(incorrectCartItemRequestDto.getProductId())).thenReturn(Optional.empty());
        cartItemNotFoundException = assertThrows(CartItemNotFoundException.class,
                () -> cartServiceMock.insert(cartItemRequestDto, incorrectCartId));
        assertEquals("CartItem not found in database.", cartItemNotFoundException.getMessage());
        cartItemNotFoundException = assertThrows(CartItemNotFoundException.class,
                () -> cartServiceMock.insert(incorrectCartItemRequestDto, cartId));
        assertEquals("CartItem not found in database.", cartItemNotFoundException.getMessage());
    }
    @Test
    void deleteByProductId(){
        Long userId = 1L;
        Long incorrectUserId = 9L;
        Long productId = 1L;
        Long incorrectProductId = 9L;
        when(userRepositoryMock.findById(userId)).thenReturn(Optional.of(userEntity));
        when(productRepositoryMock.findById(productId)).thenReturn(Optional.of(productEntity));
        Set<CartItemEntity> cartItemEntitySet = userEntity.getCartEntity().getCartItemEntitySet();
        cartServiceMock.deleteByProductId(userId, productId);
        verify(userRepositoryMock, times(1)).findById(userId);
        verify(productRepositoryMock, times(1)).findById(productId);
        for(CartItemEntity item : cartItemEntitySet){
            verify(cartItemRepositoryMock, times(1)).delete(item);
            when(userRepositoryMock.findById(incorrectUserId)).thenReturn(Optional.empty());
            when(productRepositoryMock.findById(incorrectProductId)).thenReturn(Optional.empty());
            cartNotFoundException = assertThrows(CartNotFoundException.class,
                    () -> cartServiceMock.deleteByProductId(userId,incorrectUserId));
            assertEquals("Data not found in database.", cartNotFoundException.getMessage());
            cartNotFoundException = assertThrows(CartNotFoundException.class,
                    () -> cartServiceMock.deleteByProductId(incorrectUserId, productId));
            assertEquals("Data not found in database.", cartNotFoundException.getMessage());
        }
    }
}