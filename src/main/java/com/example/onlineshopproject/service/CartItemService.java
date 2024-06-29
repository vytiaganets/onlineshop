package com.example.onlineshopproject.service;

import com.example.onlineshopproject.configuration.MapperConfiguration;
import com.example.onlineshopproject.dto.CartItemDto;
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

    public List<CartItemDto> getCartItem() {
        List<CartItemEntity> cartItemEntityList = cartItemRepository.findAll();
        List<CartItemDto> cartItemDtoList = MapperConfiguration.convertList(cartItemEntityList,
                mappers::convertToCartItemDto);
        return cartItemDtoList;
    }

    public CartItemDto getCartItemById(Long id) {
        Optional<CartItemEntity> cartItemEntity = cartItemRepository.findById(id);
        CartItemDto cartItemDto = null;
        if (cartItemEntity.isPresent()) {
            cartItemDto = mappers.convertToCartItemDto(cartItemEntity.get());
        }
        return cartItemDto;
    }

    public CartItemDto createCartItem(CartItemDto cartItemDto) {
        CartItemEntity cartItemEntity = mappers.convertToCartItemEntity(cartItemDto);
        cartItemEntity.setCartItemId(null);
        CartItemEntity newCartItem = cartItemRepository.save(cartItemEntity);
        return mappers.convertToCartItemDto(newCartItem);
    }

    public CartItemDto updateCartItem(CartItemDto cartItemDto) {
        if (cartItemDto.getCartItemId() <= 0) {
            return null;
        }
        Optional<CartItemEntity> cartItemEntityOptional = cartItemRepository.findById(cartItemDto.getCartItemId());
        if (cartItemEntityOptional.isEmpty()) {
            return null;
        }
        CartItemEntity cartItemEntity = mappers.convertToCartItemEntity(cartItemDto);
        CartItemEntity newCartItemEntity = cartItemRepository.save(cartItemEntity);
        return mappers.convertToCartItemDto(newCartItemEntity);
    }

    public void deleteCartItemEntityById(Long id) {
        Optional<CartItemEntity> cartItemEntity = cartItemRepository.findById(id);
        cartItemEntity.ifPresent(cartItemRepository::delete);
    }
}