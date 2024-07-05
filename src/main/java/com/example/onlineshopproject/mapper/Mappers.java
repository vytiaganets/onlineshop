package com.example.onlineshopproject.mapper;

import com.example.onlineshopproject.configuration.MapperConfiguration;
import com.example.onlineshopproject.dto.*;
import com.example.onlineshopproject.entity.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Mappers {
    private final ModelMapper modelMapper;

    public CartItemEntity convertToCartItemEntity(CartItemResponseDto cartItemResponseDto) {
        CartItemEntity cartItemEntity = modelMapper.map(cartItemResponseDto, CartItemEntity.class);
        return cartItemEntity;
    }

    public CartItemResponseDto convertToCartItemResponseDto(CartItemEntity cartItemEntity) {
        CartItemResponseDto cartItemResponseDto = modelMapper.map(cartItemEntity, CartItemResponseDto.class);
        return cartItemResponseDto;
    }

    public OrderItemEntity convertToOrderItemEntity(OrderItemResponseDto orderItemResponseDto) {
        OrderItemEntity orderItemEntity = modelMapper.map(orderItemResponseDto, OrderItemEntity.class);
        return orderItemEntity;
    }

    public OrderItemResponseDto convertToOrderItemResponseDto(OrderItemEntity orderItemEntity) {
        OrderItemResponseDto orderItemResponseDto = modelMapper.map(orderItemEntity, OrderItemResponseDto.class);
        return orderItemResponseDto;
    }

    public OrderEntity convertToOrderEntity(OrderResponseDto orderResponseDto) {
        return OrderEntity.builder()
                .deliveryAddress(orderResponseDto.getDeliveryAddress())
                .deliveryMethod(orderResponseDto.getDeliveryMethod())
                .items(MapperConfiguration.convertList(orderResponseDto.getItems(), this::convertToOrderItemEntity))
                .build();
    }

    public OrderResponseDto convertToOrderResponseDto(OrderEntity orderEntity) {
        return OrderResponseDto.builder()
                .orderId(orderEntity.getOrderId())
                .deliveryAddress(orderEntity.getDeliveryAddress())
                .deliveryMethod(orderEntity.getDeliveryMethod())
                .items(MapperConfiguration.convertList(orderEntity.getItems(), this::convertToOrderItemResponseDto))
                .build();
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