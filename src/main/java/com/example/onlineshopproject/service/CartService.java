package com.example.onlineshopproject.service;

import com.example.onlineshopproject.dto.CartResponseDto;
import com.example.onlineshopproject.entity.CartEntity;
import com.example.onlineshopproject.exceptions.NotFoundInDbException;
import com.example.onlineshopproject.mapper.Mappers;
import com.example.onlineshopproject.repository.CartItemRepository;
import com.example.onlineshopproject.repository.CartRepository;
import com.example.onlineshopproject.repository.ProductRepository;
import com.example.onlineshopproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final CartItemRepository cartItemRepository;
    private final UserService userService;
    private final Mappers mappers;

    public CartResponseDto getCartById(Long id) {
        CartEntity cartEntity = cartRepository.findById(id)
                .orElseThrow(() -> new NotFoundInDbException("Cart with this id was not found"));
        CartResponseDto cartResponseDto = mappers.convertToCartResponseDto(cartEntity);
        return cartResponseDto;
    }

    @Transactional
    public CartResponseDto insertCart(CartResponseDto cartResponseDto) {
        log.debug("Inserting cart for current user");
        return cartResponseDto;
    }
}
