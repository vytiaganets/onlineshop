package com.example.onlineshopproject.controller;

import com.example.onlineshopproject.dto.FavoriteDto;
import com.example.onlineshopproject.service.FavoriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/favorities")
public class FavoriteController {
    private final FavoriteService favoriteService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<FavoriteDto> getFavorities() {
        return favoriteService.getFavorite();
    }
}
