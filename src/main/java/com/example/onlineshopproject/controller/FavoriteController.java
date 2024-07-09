package com.example.onlineshopproject.controller;

import com.example.onlineshopproject.dto.FavoriteRequestDto;
import com.example.onlineshopproject.dto.FavoriteResponseDto;
import com.example.onlineshopproject.service.FavoriteService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/favorites")
public class FavoriteController implements FavoriteControllerInterface {
    private final FavoriteService favoriteService;

    @GetMapping(value = "/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public Set<FavoriteResponseDto> getByUserId(@PathVariable @Positive(message = "User id must be a positive" +
            " number") Long userId) {
        return favoriteService.getByUserId(userId);
    }

    @PostMapping(value = "/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public void insert(@RequestBody @Valid FavoriteRequestDto favoriteRequestDto,
                       @PathVariable @Positive(message = "User id must be a positive" +
                               " number") Long userId) {
        favoriteService.insert(favoriteRequestDto, userId);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    public void deleteByProductId(@RequestParam("userId") @Positive(message = "User id must be a positive" +
            " number") Long userId,
                                  @RequestParam("productId") @Positive(message = "User id must be a positive " +
                                          "number") Long productId) {
        favoriteService.deleteByProductId(userId, productId);
    }
}
