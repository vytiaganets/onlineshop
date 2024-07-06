package com.example.onlineshopproject.controller;

import com.example.onlineshopproject.dto.FavoriteRequestDto;
import com.example.onlineshopproject.dto.FavoriteResponseDto;
import com.example.onlineshopproject.service.FavoriteService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/favorities")
public class FavoriteController {
    private final FavoriteService favoriteService;

    @GetMapping(value = "/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public Set<FavoriteResponseDto> getFavoriteByUserId(@PathVariable @Positive(message = "User id must be a positive" +
            " number") Long userId) {
        return favoriteService.getFavoriteByUserId(userId);
    }

    @PostMapping(value = "/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public void insertFavorite(@RequestBody @Valid FavoriteRequestDto favoriteRequestDto,
                               @PathVariable @Positive(message = "User id must be a positive" +
                                       " number") Long userId) {
        favoriteService.insertFavorite(favoriteRequestDto, userId);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    public void deleteFavoriteByProductId(@RequestParam("userId") @Positive(message = "User id must be a positive" +
            " number") Long userId,
                                          @RequestParam("productId") @Positive(message = "User id must be a positive " +
                                                  "number") Long productId) {
        favoriteService.deleteFavoiteByProductId(userId, productId);
    }
}
