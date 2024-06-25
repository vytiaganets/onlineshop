package com.example.onlineshop.service;

import com.example.onlineshop.configuration.MapperConfiguration;
import com.example.onlineshop.dto.CategoryDto;
import com.example.onlineshop.entity.CategoryEntity;
import com.example.onlineshop.mapper.Mappers;
import com.example.onlineshop.repository.CategoryJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl{
    private final CategoryJpaRepository categoryJpaRepository;

    private final Mappers mappers;

    public List<CategoryDto> getCategory() {
        List<CategoryEntity> categoryEntityList = categoryJpaRepository.findAll();
        List<CategoryDto> categoryDtoList = MapperConfiguration.convertList(categoryEntityList, mappers::convertToCategoryDto);
        return categoryDtoList;
    }

    public CategoryDto getCategoryById(Long id) {
        Optional<CategoryEntity> categoryEntityOptional = categoryJpaRepository.findById(id);
        CategoryDto categoryDto = null;
        if (!categoryEntityOptional.isPresent()) {
            categoryDto = categoryEntityOptional.map(mappers::convertToCategoryDto).orElse(null);
        }
        return categoryDto;
    }

    public CategoryDto createCategory(CategoryDto categoryDto) {
        CategoryEntity categoryEntity = mappers.convertToCategory(categoryDto);
        categoryEntity.setId(0L);
        CategoryEntity savedCategory = categoryJpaRepository.save(categoryEntity);
        return mappers.convertToCategoryDto(savedCategory);
    }
}