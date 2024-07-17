package com.example.onlineshopproject.mapper;

import com.example.onlineshopproject.configuration.MapperConfiguration;
import com.example.onlineshopproject.dto.*;
import com.example.onlineshopproject.entity.*;
import com.example.onlineshopproject.query.ProductCount;
import com.example.onlineshopproject.query.ProductPending;
import com.example.onlineshopproject.query.ProductProfit;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Mappers {
    private final ModelMapper modelMapper;

    public CartItemEntity convertToCartItemEntity(CartItemRequestDto cartItemRequestDto) {
        CartItemEntity cartItemEntity = modelMapper.map(cartItemRequestDto, CartItemEntity.class);
        return cartItemEntity;
    }

    public CartItemResponseDto convertToCartItemResponseDto(CartItemEntity cartItemEntity) {
        CartItemResponseDto cartItemResponseDto = modelMapper.map(cartItemEntity, CartItemResponseDto.class);
        return cartItemResponseDto;
    }

    public OrderItemEntity convertToOrderItemEntity(OrderItemRequestDto orderItemRequestDto) {
        OrderItemEntity orderItemEntity = modelMapper.map(orderItemRequestDto, OrderItemEntity.class);
        return orderItemEntity;
    }

    public OrderItemResponseDto convertToOrderItemResponseDto(OrderItemEntity orderItemEntity) {
        OrderItemResponseDto orderItemResponseDto = modelMapper.map(orderItemEntity, OrderItemResponseDto.class);
        return orderItemResponseDto;
    }

    public OrderEntity convertToOrderEntity(OrderRequestDto orderRequestDto) {
        OrderEntity orderEntity = modelMapper.map(orderRequestDto, OrderEntity.class);
        return orderEntity;
    }

    public OrderResponseDto convertToOrderResponseDto(OrderEntity orderEntity) {
modelMapper
        .typeMap(OrderEntity.class, OrderResponseDto.class)
        .addMappings(mapper -> mapper.skip(OrderResponseDto::setUserResponseDto));
        OrderResponseDto orderResponseDto = modelMapper.map(orderEntity,OrderResponseDto.class);
        return orderResponseDto;
    }

    public UserRequestDto convertToUserResponseDto(UserEntity userEntity) {
        if (userEntity == null) {
            return null;
        }
        UserRequestDto userRequestDto = modelMapper.map(userEntity, UserRequestDto.class);
        CartEntity cartEntity = userEntity.getCartEntity();
        if (cartEntity != null) {
            cartEntity.setUserEntity(null);
            userRequestDto.setCartResponseDto(convertToCartResponseDto(cartEntity));
        }
        return userRequestDto;
    }

    public UserEntity convertToUserEntity(UserRequestDto userRequestDto) {
        UserEntity userEntity = modelMapper.map(userRequestDto, UserEntity.class);
        return userEntity;
    }

    public CategoryResponseDto convertToCategoryResponseDto(CategoryEntity categoryEntity) {
        CategoryResponseDto categoryResponseDto = modelMapper.map(categoryEntity, CategoryResponseDto.class);
        return categoryResponseDto;
    }

    public CategoryEntity convertToCategoryEntity(CategoryRequestDto categoryRequestDto) {
        CategoryEntity categoryEntity = modelMapper.map(categoryRequestDto, CategoryEntity.class);
        return categoryEntity;
    }

    public CartResponseDto convertToCartResponseDto(CartEntity cartEntity) {
        CartResponseDto cartResponseDto = modelMapper.map(cartEntity, CartResponseDto.class);
        return cartResponseDto;
    }

    public CartEntity convertToCartEntity(CartRequestDto cartRequestDto) {
        CartEntity cartEntity = modelMapper.map(cartRequestDto, CartEntity.class);
        return cartEntity;
    }

    public ProductResponseDto convertToProductResponseDto(ProductEntity productEntity) {
        ProductResponseDto productResponseDto = modelMapper.map(productEntity, ProductResponseDto.class);
        return productResponseDto;
    }

    public ProductEntity convertToProductEntity(ProductRequestDto productRequestDto) {
        ProductEntity productEntity = modelMapper.map(productRequestDto, ProductEntity.class);
        return productEntity;
    }
    public ProductPendingDto convertToProductPendingDto(ProductPending productPending) {
        ProductPendingDto productPendingDto = modelMapper.map(productPending, ProductPendingDto.class);
        return productPendingDto;
    }
    public ProductProfitDto convertToProductProfitDto(ProductProfit productProfit) {
        ProductProfitDto productProfitDto = modelMapper.map(productProfit, ProductProfitDto.class);
        return productProfitDto;
    }
    public ProductCountDto convertToProductCountDto(ProductCount productCount) {
        ProductCountDto productCountDto = modelMapper.map(productCount, ProductCountDto.class);
        return productCountDto;
    }

    public FavoriteResponseDto convertToFavoriteResponseDto(FavoriteEntity favoriteEntity) {
        FavoriteResponseDto favoriteResponseDto = modelMapper.map(favoriteEntity, FavoriteResponseDto.class);
        modelMapper
                .typeMap(FavoriteEntity.class, FavoriteResponseDto.class).addMappings(mapper -> mapper.skip(FavoriteResponseDto::setUserResponseDto));
        favoriteResponseDto
                .setProductResponseDto(convertToProductResponseDto(favoriteEntity.getProductEntity()));
        return favoriteResponseDto;
    }

    public FavoriteEntity convertToFavoriteEntity(FavoriteRequestDto favoriteRequestDto) {
        FavoriteEntity favoriteEntity = modelMapper.map(favoriteRequestDto, FavoriteEntity.class);
        return favoriteEntity;
    }

}