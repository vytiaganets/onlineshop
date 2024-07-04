package com.example.onlineshopproject.service;

import com.example.onlineshopproject.dto.CartDto;
import com.example.onlineshopproject.entity.CartEntity;
import com.example.onlineshopproject.entity.UserEntity;
import com.example.onlineshopproject.mapper.Mappers;
import com.example.onlineshopproject.repository.CartRepository;
import com.example.onlineshopproject.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final Mappers mappers;

    @ResponseStatus(HttpStatus.CREATED)
    public CartDto insertCart(@Valid @RequestBody CartDto cartDto) {
        if (cartDto.getUserDto() == null || cartDto.getUserDto().getUserId() == null) {
            throw new IllegalArgumentException("Идентификатор пользователя должен быть указан в CartDto");
        }
        Long userId = cartDto.getUserDto().getUserId();
        UserEntity userEntity = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException(
                "Пользователь не найден с идентификатором: " + userId));

        CartEntity cartEntity = mappers.convertToCartEntity(cartDto);
        cartEntity.setUserEntity(userEntity);
        try {
            CartEntity savedCart = cartRepository.save(cartEntity);
            return mappers.convertToCartDto(savedCart);
        } catch (DataAccessException dataAccessException) {
            throw new RuntimeException("Не удалось сохранить корзину: " + dataAccessException.getMessage(),
                    dataAccessException);
        }
    }
}
