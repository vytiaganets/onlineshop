package com.example.onlineshopproject.service;

import com.example.onlineshopproject.configuration.MapperConfiguration;
import com.example.onlineshopproject.dto.CartItemRequestDto;
import com.example.onlineshopproject.dto.CartItemResponseDto;
import com.example.onlineshopproject.dto.CartResponseDto;
import com.example.onlineshopproject.entity.CartEntity;
import com.example.onlineshopproject.entity.CartItemEntity;
import com.example.onlineshopproject.entity.ProductEntity;
import com.example.onlineshopproject.entity.UserEntity;
import com.example.onlineshopproject.exceptions.CartItemNotFoundException;
import com.example.onlineshopproject.exceptions.CartNotFoundException;
import com.example.onlineshopproject.exceptions.NotFoundInDbException;
import com.example.onlineshopproject.mapper.Mappers;
import com.example.onlineshopproject.repository.CartItemRepository;
import com.example.onlineshopproject.repository.CartRepository;
import com.example.onlineshopproject.repository.ProductRepository;
import com.example.onlineshopproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    private static final Logger log = LoggerFactory.getLogger(CartServiceImpl.class);
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final Mappers mappers;

//    @Transactional
    public Set<CartItemResponseDto> getByUserId(Long userId) {
        log.debug("Attempting get by userId: {}", userId);
        UserEntity userEntity = userRepository.findById(userId).orElse(null);
        if (userEntity != null) {
            Set<CartItemEntity> cartItemEntitySet = userEntity.getCartEntity().getCartItemEntitySet();
            return MapperConfiguration.convertSet(cartItemEntitySet, mappers::convertToCartItemResponseDto);
        } else {
            log.error("Cart not found: {}", userId);
            throw new CartNotFoundException("Cart not found in database.");
        }
    }

//    @Transactional
    public void insert(CartItemRequestDto cartItemRequestDto, Long userId) {
        log.debug("Attempting insert cart: {}", userId);
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
            log.error("User or product not found: {}", userId);
            throw new CartItemNotFoundException("CartItem not found in database");
        }
    }

    @Transactional //2+ записи в БД
    public void deleteByProductId(Long userId, Long productId) {
        log.debug("Attempting delete by productId: {}", productId);
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
            log.error("Product or user not found: {}", productId);
            throw new CartNotFoundException("Data not found in database.");
        }
    }

//    public List<CartItemResponseDto> getAll() {
//        List<CartItemEntity> cartItemEntityList = cartItemRepository.findAll();
//        List<CartItemResponseDto> cartItemResponseDtoList = MapperConfiguration.convertList(cartItemEntityList,
//                mappers::convertToCartItemResponseDto);
//        return cartItemResponseDtoList;
//    }

//    public CartItemResponseDto getById(Long userId) {
//        Optional<CartItemEntity> cartItemEntity = cartItemRepository.findById(userId);
//        CartItemResponseDto cartItemResponseDto = null;
//        if (cartItemEntity.isPresent()) {
//            cartItemResponseDto = mappers.convertToCartItemResponseDto(cartItemEntity.get());
//        }
//        return cartItemResponseDto;
//    }

//    public CartItemResponseDto create(CartItemRequestDto cartItemRequestDto) {
//        CartItemEntity cartItemEntity = mappers.convertToCartItemEntity(cartItemRequestDto);
//        cartItemEntity.setCartItemId(null);
//        CartItemEntity newCartItem = cartItemRepository.save(cartItemEntity);
//        return mappers.convertToCartItemResponseDto(newCartItem);
//    }
//
//    public void deleteById(Long userId) {
//        Optional<CartItemEntity> cartItemEntity = cartItemRepository.findById(userId);
//        cartItemEntity.ifPresent(cartItemRepository::delete);
//    }
//    private final CartRepository cartRepository;
//    private final UserRepository userRepository;
//    private final ProductRepository productRepository;
//    private final CartItemRepository cartItemRepository;
//    private final UserService userService;
//    private final Mappers mappers;

//    public CartResponseDto getById(Long userId) {
//        CartEntity cartEntity = cartRepository.findById(userId)
//                .orElseThrow(() -> new NotFoundInDbException("Cart with this id was not found"));
//        CartResponseDto cartResponseDto = mappers.convertToCartResponseDto(cartEntity);
//        return cartResponseDto;
//    }
//
//    @Transactional
//    public CartResponseDto insert(CartResponseDto cartResponseDto) {
//        log.debug("Inserting cart for current user");
//        return cartResponseDto;
//    }
}
