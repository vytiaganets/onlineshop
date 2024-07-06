package com.example.onlineshopproject.service;

import com.example.onlineshopproject.configuration.MapperConfiguration;
import com.example.onlineshopproject.dto.CategoryRequestDto;
import com.example.onlineshopproject.dto.CategoryResponseDto;
import com.example.onlineshopproject.entity.CategoryEntity;
import com.example.onlineshopproject.exceptions.NotFoundInDbException;
import com.example.onlineshopproject.mapper.Mappers;
import com.example.onlineshopproject.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final Mappers mappers;

    @Transactional
    public List<CategoryResponseDto> getCategory() {
        List<CategoryEntity> categoryEntityList = categoryRepository.findAll();
        List<CategoryResponseDto> categoryResponseDtoList = MapperConfiguration.convertList(categoryEntityList,
                mappers::convertToCategoryResponseDto);
        return categoryResponseDtoList;
    }

    @Transactional
    public CategoryResponseDto getCategoryById(Long id) {
        Optional<CategoryEntity> categoryEntityOptional = categoryRepository.findById(id);
        CategoryResponseDto categoryResponseDto = null;
        if (!categoryEntityOptional.isPresent()) {
            categoryResponseDto = categoryEntityOptional.map(mappers::convertToCategoryResponseDto).orElse(null);
        }
        return categoryResponseDto;
    }

    @Transactional
    public void insertCategory(CategoryRequestDto categoryRequestDto) {
        CategoryEntity categoryEntity = categoryRepository.findCategoryEntityByName(categoryRequestDto.getName());
        if (categoryEntity == null) {
            CategoryEntity categoryEntity1 = mappers.convertToCategoryEntity(categoryRequestDto);
            categoryEntity.setCategoryId(0L);
            categoryRepository.save(categoryEntity);
        } else {
            throw new NotFoundInDbException("The category withname already exists.");
        }
    }

    @Transactional
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
        } else {
            throw new NotFoundInDbException("Data not found  in database.");
        }
    }

    @Transactional
    public void updateCategory(CategoryRequestDto categoryRequestDto, Long id) {
        if (id > 0) {
            CategoryEntity categoryEntity = categoryRepository.findById(id).orElse(null);
            if (categoryEntity != null) {
                categoryEntity.setName(categoryRequestDto.getName());
                categoryRepository.save(categoryEntity);
            } else {
                throw new NotFoundInDbException("The value is not valid.");
            }
        }
    }
}