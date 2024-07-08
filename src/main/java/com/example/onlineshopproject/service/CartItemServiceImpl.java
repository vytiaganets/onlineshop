package com.example.onlineshopproject.service;

import com.example.onlineshopproject.configuration.MapperConfiguration;
import com.example.onlineshopproject.dto.CartItemRequestDto;
import com.example.onlineshopproject.dto.CartItemResponseDto;
import com.example.onlineshopproject.entity.CartEntity;
import com.example.onlineshopproject.entity.CartItemEntity;
import com.example.onlineshopproject.entity.ProductEntity;
import com.example.onlineshopproject.entity.UserEntity;
import com.example.onlineshopproject.exceptions.NotFoundInDbException;
import com.example.onlineshopproject.mapper.Mappers;
import com.example.onlineshopproject.repository.CartItemRepository;
import com.example.onlineshopproject.repository.CartRepository;
import com.example.onlineshopproject.repository.ProductRepository;
import com.example.onlineshopproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CartItemServiceImpl implements CartItemService {
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final Mappers mappers;

    @Transactional
    public Set<CartItemResponseDto> getById(Long userId) {
        UserEntity userEntity = userRepository.findById(userId).orElse(null);
        if (userEntity != null) {
            Set<CartItemEntity> cartItemEntitySet = userEntity.getCartEntity().getCartItemEntitySet();
            return MapperConfiguration.convertSet(cartItemEntitySet, mappers::convertToCartItemResponseDto);
        } else {
            throw new NotFoundInDbException("Data not found in database.");
        }
    }

    @Transactional
    public void insert(CartItemRequestDto cartItemRequestDto, Long userId) {
        CartItemEntity cartItemEntityToInsert = new CartItemEntity();
        UserEntity userEntity = userRepository.findById(userId).orElse(null);
        ProductEntity productEntity = productRepository.findById(cartItemRequestDto.getProductId()).orElse(null);
        if (userEntity != null && productEntity != null) {
            CartEntity cartEntity = cartRepository.findById(userEntity.getCartEntity().getCartId()).orElse(null);
            cartItemEntityToInsert.setCartEntity(cartEntity);
            cartItemEntityToInsert.setCartItemId(0L);
            cartItemEntityToInsert.setProductEntity(productEntity);
            cartItemEntityToInsert.setQuantity(cartItemRequestDto.getQuantity());
            cartItemRepository.save(cartItemEntityToInsert);
        } else {
            throw new NotFoundInDbException("Data not found in database");
        }
    }

    @Transactional
    public void deleteByProductId(Long userId, Long productId) {
        UserEntity userEntity = userRepository.findById(userId).orElse(null);
        ProductEntity productEntity = productRepository.findById(productId).orElse(null);
        if (userEntity != null && productEntity != null) {
            Set<CartItemEntity> cartItemEntitySet = userEntity.getCartEntity().getCartItemEntitySet();
            for (CartItemEntity itemEntity : cartItemEntitySet) {
                if (itemEntity.getProductEntity().getProductId().equals(productId)) {
                    cartItemRepository.delete(itemEntity);
                }
            }
        } else {
            throw new NotFoundInDbException("Data not found in database.");
        }
    }

    public List<CartItemResponseDto> getAll() {
        List<CartItemEntity> cartItemEntityList = cartItemRepository.findAll();
        List<CartItemResponseDto> cartItemResponseDtoList = MapperConfiguration.convertList(cartItemEntityList,
                mappers::convertToCartItemResponseDto);
        return cartItemResponseDtoList;
    }

//    public CartItemResponseDto getById(Long userId) {
//        Optional<CartItemEntity> cartItemEntity = cartItemRepository.findById(userId);
//        CartItemResponseDto cartItemResponseDto = null;
//        if (cartItemEntity.isPresent()) {
//            cartItemResponseDto = mappers.convertToCartItemResponseDto(cartItemEntity.get());
//        }
//        return cartItemResponseDto;
//    }

    public CartItemResponseDto create(CartItemRequestDto cartItemRequestDto) {
        CartItemEntity cartItemEntity = mappers.convertToCartItemEntity(cartItemRequestDto);
        cartItemEntity.setCartItemId(null);
        CartItemEntity newCartItem = cartItemRepository.save(cartItemEntity);
        return mappers.convertToCartItemResponseDto(newCartItem);
    }

    public void deleteById(Long userId) {
        Optional<CartItemEntity> cartItemEntity = cartItemRepository.findById(userId);
        cartItemEntity.ifPresent(cartItemRepository::delete);
    }
}