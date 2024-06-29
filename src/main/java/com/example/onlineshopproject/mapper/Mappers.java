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

    public CartItemEntity convertToCartItemEntity(CartItemDto cartItemDto) {
        CartItemEntity cartItemEntity = modelMapper.map(cartItemDto, CartItemEntity.class);
        return cartItemEntity;
    }

    public CartItemDto convertToCartItemDto(CartItemEntity cartItemEntity) {
        CartItemDto cartItemDto = modelMapper.map(cartItemEntity, CartItemDto.class);
        return cartItemDto;
    }

    public OrderItemEntity convertToOrderItemEntity(OrderItemDto orderItemDto) {
        OrderItemEntity orderItemEntity = modelMapper.map(orderItemDto, OrderItemEntity.class);
        return orderItemEntity;
    }

    public OrderItemDto convertToOrderItemDto(OrderItemEntity orderItemEntity) {
        OrderItemDto orderItemDto = modelMapper.map(orderItemEntity, OrderItemDto.class);
        return orderItemDto;
    }

    public OrderEntity convertToOrderEntity(OrderDto orderDto) {
        return OrderEntity.builder()
                .deliveryAddress(orderDto.getDeliveryAddress())
                .deliveryMethod(orderDto.getDeliveryMethod())
                .items(MapperConfiguration.convertList(orderDto.getItems(), this::convertToOrderItemEntity))
                .build();
    }

    public OrderDto convertToOrderDto(OrderEntity orderEntity) {
        return OrderDto.builder()
                .orderId(orderEntity.getOrderId())
                .deliveryAddress(orderEntity.getDeliveryAddress())
                .deliveryMethod(orderEntity.getDeliveryMethod())
                .items(MapperConfiguration.convertList(orderEntity.getItems(), this::convertToOrderItemDto))
                .build();
    }

    public UserDto convertToUserDto(UserEntity userEntity) {
        if (userEntity == null) {
            return null;
        }
        UserDto userDto = modelMapper.map(userEntity, UserDto.class);

        CartEntity cartEntity = userEntity.getCartEntity();
        if (cartEntity != null) {
            cartEntity.setUserEntity(null);
            userDto.setCartDto(convertToCartDto(cartEntity));
        }
        return userDto;
    }

    public CategoryDto convertToCategoryDto(CategoryEntity categoryEntity) {
        CategoryDto categoryDto = modelMapper.map(categoryEntity, CategoryDto.class);
        return categoryDto;
    }

    public CategoryEntity convertToCategoryEntity(CategoryDto categoryDto) {
        CategoryEntity categoryEntity = modelMapper.map(categoryDto, CategoryEntity.class);
        return categoryEntity;
    }

    public ProductResponceDto convertToProductDto(ProductEntity productEntity) {
        ProductResponceDto productResponceDto = modelMapper.map(productEntity, ProductResponceDto.class);
        return productResponceDto;
    }

    public UserEntity convertToUserEntity(UserDto userDto) {
        UserEntity userEntity = modelMapper.map(userDto, UserEntity.class);
        return userEntity;
    }

    public CartDto convertToCartDto(CartEntity cartEntity) {
        CartDto cartDto = modelMapper.map(cartEntity, CartDto.class);
        return cartDto;
    }

    public CartEntity convertToCartEntity(CartDto cartDto) {
        CartEntity cartEntity = modelMapper.map(cartDto, CartEntity.class);
        return cartEntity;
    }

    public ProductRequestDto convertToProductRequestDto(ProductEntity productEntity) {
        ProductRequestDto productRequestDto = modelMapper.map(productEntity, ProductRequestDto.class);
        return productRequestDto;
    }

    public ProductEntity convertToProductEntity(ProductRequestDto productRequestDto) {
        ProductEntity productEntity = modelMapper.map(productRequestDto, ProductEntity.class);
        return productEntity;
    }

    public FavoriteDto convertToFavoriteDto(FavoriteEntity favoriteEntity) {
        FavoriteDto favoriteDto = modelMapper.map(favoriteEntity, FavoriteDto.class);
        return favoriteDto;
    }

    public FavoriteEntity convertToFavoriteEntity(FavoriteDto favoriteDto) {
        FavoriteEntity favoriteEntity = modelMapper.map(favoriteDto, FavoriteEntity.class);
        return favoriteEntity;
    }
}





























