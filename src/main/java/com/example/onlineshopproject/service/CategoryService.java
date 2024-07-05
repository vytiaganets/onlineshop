package com.example.onlineshopproject.service;

import com.example.onlineshopproject.configuration.MapperConfiguration;
import com.example.onlineshopproject.dto.CategoryRequestDto;
import com.example.onlineshopproject.dto.CategoryResponseDto;
import com.example.onlineshopproject.entity.CategoryEntity;
import com.example.onlineshopproject.mapper.Mappers;
import com.example.onlineshopproject.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    private final Mappers mappers;

    public List<CategoryResponseDto> getCategory() {
        List<CategoryEntity> categoryEntityList = categoryRepository.findAll();
        List<CategoryResponseDto> categoryResponseDtoList = MapperConfiguration.convertList(categoryEntityList,
                mappers::convertToCategoryResponseDto);
        return categoryResponseDtoList;
    }

    public CategoryResponseDto getCategoryById(Long id) {
        Optional<CategoryEntity> categoryEntityOptional = categoryRepository.findById(id);
        CategoryResponseDto categoryResponseDto = null;
        if (!categoryEntityOptional.isPresent()) {
            categoryResponseDto = categoryEntityOptional.map(mappers::convertToCategoryResponseDto).orElse(null);
        }
        return categoryResponseDto;
    }

    public CategoryResponseDto createCategory(CategoryRequestDto categoryRequestDto) {
        CategoryEntity categoryEntity = mappers.convertToCategoryEntity(categoryRequestDto);
        categoryEntity.setCategoryId(0L);
        CategoryEntity savedCategory = categoryRepository.save(categoryEntity);
        return mappers.convertToCategoryResponseDto(savedCategory);
    }

    public void deleteCategoryById(Long id) {
        Optional<CategoryEntity> categoryEntity = categoryRepository.findById(id);
        if (categoryEntity.isPresent()) {
            categoryRepository.deleteById(id);
        }
    }

    public CategoryResponseDto updateCategory(CategoryResponseDto categoryResponseDto) {
        if (categoryResponseDto.getCategoryId() <= 0) {
            return null;
        }
        Optional<CategoryEntity> categoryEntityOptional = categoryRepository.findById(categoryResponseDto.getCategoryId());
        if (!categoryEntityOptional.isPresent()) {
            return null;
            //...
        }
        CategoryEntity categoryEntity = categoryEntityOptional.get();
        categoryEntity.setName(categoryResponseDto.getName());
        CategoryEntity savedCategory = categoryRepository.save(categoryEntity);
        return mappers.convertToCategoryResponseDto(savedCategory);
    }
}