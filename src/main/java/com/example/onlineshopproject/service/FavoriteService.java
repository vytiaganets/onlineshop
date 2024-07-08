package com.example.onlineshopproject.service;

import com.example.onlineshopproject.dto.FavoriteRequestDto;
import com.example.onlineshopproject.dto.FavoriteResponseDto;

import java.util.Set;

public interface FavoriteService {
    Set<FavoriteResponseDto> getByUserId(Long userId);
    void insert(FavoriteRequestDto favoriteRequestDto, Long userId);
    void deleteByProductId(Long usserId, Long productId);
}
