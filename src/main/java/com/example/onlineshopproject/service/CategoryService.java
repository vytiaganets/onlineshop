package com.example.onlineshopproject.service;

import com.example.onlineshopproject.dto.CategoryRequestDto;
import com.example.onlineshopproject.dto.CategoryResponseDto;

import java.util.List;

public interface CategoryService {
    List<CategoryResponseDto> getAll();
    CategoryResponseDto getById(Long id);
    void insert(CategoryRequestDto categoryRequestDto);
    CategoryResponseDto create(CategoryRequestDto categoryRequestDto);
    void deleteById(Long id);
    void update(CategoryRequestDto categoryRequestDto, Long id);
}
