package com.example.onlineshopproject.service;

import com.example.onlineshopproject.configuration.MapperConfiguration;
import com.example.onlineshopproject.dto.FavoriteRequestDto;
import com.example.onlineshopproject.dto.FavoriteResponseDto;
import com.example.onlineshopproject.entity.FavoriteEntity;
import com.example.onlineshopproject.entity.ProductEntity;
import com.example.onlineshopproject.entity.UserEntity;
import com.example.onlineshopproject.exceptions.NotFoundInDbException;
import com.example.onlineshopproject.mapper.Mappers;
import com.example.onlineshopproject.repository.FavoriteRepository;
import com.example.onlineshopproject.repository.ProductRepository;
import com.example.onlineshopproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class FavoriteService {
    private final FavoriteRepository favoriteRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final Mappers mappers;

    @Transactional
    public Set<FavoriteResponseDto> getFavoriteByUserId(Long userId) {
        UserEntity userEntity = userRepository.findById(userId).orElse(null);
        if (userEntity != null) {
            Set<FavoriteEntity> favoriteEntitySet = userEntity.getFavoriteEntitySet();
            return MapperConfiguration.convertSet(favoriteEntitySet, mappers::convertToFavoriteResponseDto);
        } else {
            throw new NotFoundInDbException("Data not found in database.");
        }
    }

    @Transactional
    public void insertFavorite(FavoriteRequestDto favoriteRequestDto, Long userId) {
        FavoriteEntity favoriteEntity = new FavoriteEntity();
        UserEntity userEntity = userRepository.findById(userId).orElse(null);
        ProductEntity productEntity = productRepository.findById(favoriteRequestDto.getProductId()).orElse(null);
        if (userEntity != null && productEntity != null) {
            favoriteEntity.setProductEntity(productEntity);
            favoriteEntity.setUserEntity(userEntity);
            favoriteRepository.save(favoriteEntity);
        } else {
            throw new NotFoundInDbException("Data not found in database.");
        }
    }
    @Transactional
    public void deleteFavoiteByProductId(Long usserId, Long productId){
        UserEntity userEntity = userRepository.findById(usserId).orElse(null);
        ProductEntity productEntity = productRepository.findById(productId).orElse(null);
        if(userEntity != null && productEntity != null){
            Set<FavoriteEntity> favoriteEntitySet = userEntity.getFavoriteEntitySet();
            for(FavoriteEntity item : favoriteEntitySet){
                if(item.getProductEntity().getProductId() == productId){
                    favoriteRepository.delete(item);
                }
            }
        } else {
            throw new NotFoundInDbException("Data not found in database.");
        }
    }
}
