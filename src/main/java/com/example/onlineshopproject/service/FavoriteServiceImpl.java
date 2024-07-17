package com.example.onlineshopproject.service;

import com.example.onlineshopproject.configuration.MapperConfiguration;
import com.example.onlineshopproject.dto.FavoriteRequestDto;
import com.example.onlineshopproject.dto.FavoriteResponseDto;
import com.example.onlineshopproject.entity.FavoriteEntity;
import com.example.onlineshopproject.entity.ProductEntity;
import com.example.onlineshopproject.entity.UserEntity;
import com.example.onlineshopproject.exceptions.FavoriteNotFoundException;
import com.example.onlineshopproject.exceptions.NotFoundInDbException;
import com.example.onlineshopproject.mapper.Mappers;
import com.example.onlineshopproject.repository.FavoriteRepository;
import com.example.onlineshopproject.repository.ProductRepository;
import com.example.onlineshopproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class FavoriteServiceImpl implements FavoriteService {
    private static final Logger log = LoggerFactory.getLogger(FavoriteServiceImpl.class);
    private final FavoriteRepository favoriteRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final Mappers mappers;

    public Set<FavoriteResponseDto> getByUserId(Long userId) {
        log.debug("Attempting favorite by userId: {}", userId);
        UserEntity userEntity = userRepository.findById(userId).orElse(null);
        if (userEntity != null) {
            Set<FavoriteEntity> favoriteEntitySet = userEntity.getFavoriteEntitySet();
            return MapperConfiguration.convertSet(favoriteEntitySet, mappers::convertToFavoriteResponseDto);
        } else {
            log.error("Favorite by userId {} not found.", userId);
            throw new FavoriteNotFoundException("Data not found in database.");
        }
    }

    public void insert(FavoriteRequestDto favoriteRequestDto, Long userId) {
        log.debug("Attempting insert favorite: {}", favoriteRequestDto.getProductId());
        FavoriteEntity favoriteEntity = new FavoriteEntity();
        UserEntity userEntity = userRepository.findById(userId).orElse(null);
        ProductEntity productEntity = productRepository.findById(favoriteRequestDto.getProductId()).orElse(null);
        if (userEntity != null && productEntity != null) {
            favoriteEntity.setProductEntity(productEntity);
            favoriteEntity.setUserEntity(userEntity);
            favoriteRepository.save(favoriteEntity);
        } else {
            log.error("Favorite not found", userId);
            throw new FavoriteNotFoundException("Data not found in database.");
        }
    }

    public void deleteByProductId(Long userId, Long productId) {
        log.debug("Attempting delete by productId", productId);
        UserEntity userEntity = userRepository.findById(userId).orElse(null);
        ProductEntity productEntity = productRepository.findById(productId).orElse(null);
        if (userEntity != null && productEntity != null) {
            Set<FavoriteEntity> favoriteEntitySet = userEntity.getFavoriteEntitySet();
            for (FavoriteEntity item : favoriteEntitySet) {
                if (item.getProductEntity().getProductId() == productId) {
                    favoriteRepository.delete(item);
                }
            }
        } else {
            log.error("Favorite by productId not found", productId);
            throw new FavoriteNotFoundException("Data not found in database.");
        }
    }
}
