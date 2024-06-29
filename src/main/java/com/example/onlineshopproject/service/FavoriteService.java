package com.example.onlineshopproject.service;

import com.example.onlineshopproject.configuration.MapperConfiguration;
import com.example.onlineshopproject.dto.FavoriteDto;
import com.example.onlineshopproject.mapper.Mappers;
import com.example.onlineshopproject.repository.FavoriteRepository;
import com.example.onlineshopproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FavoriteService {
    private final FavoriteRepository favoriteRepository;
    private final UserRepository userRepository;
    private final Mappers mappers;

    public List<FavoriteDto> getFavorite() {
        return MapperConfiguration.convertList(favoriteRepository.findAll(), mappers::convertToFavoriteDto);
    }
}
