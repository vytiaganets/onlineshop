package com.example.onlineshopproject.service;

import com.example.onlineshopproject.configuration.MapperConfiguration;
import com.example.onlineshopproject.dto.CartItemResponseDto;
import com.example.onlineshopproject.entity.CartItemEntity;
import com.example.onlineshopproject.mapper.Mappers;
import com.example.onlineshopproject.repository.CartItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartItemService {
    private final CartItemRepository cartItemRepository;
    private final Mappers mappers;

    public List<CartItemResponseDto> getCartItem() {
        List<CartItemEntity> cartItemEntityList = cartItemRepository.findAll();
        List<CartItemResponseDto> cartItemResponseDtoList = MapperConfiguration.convertList(cartItemEntityList,
                mappers::convertToCartItemResponseDto);
        return cartItemResponseDtoList;
    }

    public CartItemResponseDto getCartItemById(Long id) {
        Optional<CartItemEntity> cartItemEntity = cartItemRepository.findById(id);
        CartItemResponseDto cartItemResponseDto = null;
        if (cartItemEntity.isPresent()) {
            cartItemResponseDto = mappers.convertToCartItemResponseDto(cartItemEntity.get());
        }
        return cartItemResponseDto;
    }

    public CartItemResponseDto createCartItem(CartItemResponseDto cartItemResponseDto) {
        CartItemEntity cartItemEntity = mappers.convertToCartItemEntity(cartItemResponseDto);
        cartItemEntity.setCartItemId(null);
        CartItemEntity newCartItem = cartItemRepository.save(cartItemEntity);
        return mappers.convertToCartItemResponseDto(newCartItem);
    }

    public CartItemResponseDto updateCartItem(CartItemResponseDto cartItemResponseDto) {
        if (cartItemResponseDto.getCartItemId() <= 0) {
            return null;
        }
        Optional<CartItemEntity> cartItemEntityOptional = cartItemRepository.findById(cartItemResponseDto.getCartItemId());
        if (cartItemEntityOptional.isEmpty()) {
            return null;
        }
        CartItemEntity cartItemEntity = mappers.convertToCartItemEntity(cartItemResponseDto);
        CartItemEntity newCartItemEntity = cartItemRepository.save(cartItemEntity);
        return mappers.convertToCartItemResponseDto(newCartItemEntity);
    }

    public void deleteCartItemEntityById(Long id) {
        Optional<CartItemEntity> cartItemEntity = cartItemRepository.findById(id);
        cartItemEntity.ifPresent(cartItemRepository::delete);
    }
}