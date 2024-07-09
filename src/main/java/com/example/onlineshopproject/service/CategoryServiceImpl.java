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
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final Mappers mappers;

    @Transactional
    public List<CategoryResponseDto> getAll() {
        List<CategoryEntity> categoryEntityList = categoryRepository.findAll();
        List<CategoryResponseDto> categoryResponseDtoList = MapperConfiguration.convertList(categoryEntityList,
                mappers::convertToCategoryResponseDto);
        return categoryResponseDtoList;
    }

    @Transactional
    public CategoryResponseDto getById(Long categoryId) {
        Optional<CategoryEntity> categoryEntityOptional = categoryRepository.findById(categoryId);
        CategoryResponseDto categoryResponseDto = null;
        if (!categoryEntityOptional.isPresent()) {
            categoryResponseDto = categoryEntityOptional.map(mappers::convertToCategoryResponseDto).orElse(null);
        }
        return categoryResponseDto;
    }

    @Transactional
    public void insert(CategoryRequestDto categoryRequestDto) {
        CategoryEntity categoryEntity = categoryRepository.findCategoryEntityByName(categoryRequestDto.getName());
        if (categoryEntity == null) {
            CategoryEntity categoryEntity1 = mappers.convertToCategoryEntity(categoryRequestDto);
            categoryEntity.setCategoryId(0L);
            categoryRepository.save(categoryEntity);
        } else {
            throw new NotFoundInDbException("The category with name already exists.");
        }
    }

    @Transactional
    public CategoryResponseDto create(CategoryRequestDto categoryRequestDto) {
        CategoryEntity categoryEntity = mappers.convertToCategoryEntity(categoryRequestDto);
        categoryEntity.setCategoryId(0L);
        CategoryEntity savedCategory = categoryRepository.save(categoryEntity);
        return mappers.convertToCategoryResponseDto(savedCategory);
    }
    @Transactional
    public void deleteById(Long categoryId) {
        Optional<CategoryEntity> categoryEntity = categoryRepository.findById(categoryId);
        if (categoryEntity.isPresent()) {
            categoryRepository.deleteById(categoryId);
        } else {
            throw new NotFoundInDbException("Data not found  in database.");
        }
    }

    @Transactional
    public void update(CategoryRequestDto categoryRequestDto, Long categoryId) {
        if (categoryId > 0) {
            CategoryEntity categoryEntity = categoryRepository.findById(categoryId).orElse(null);
            if (categoryEntity != null) {
                categoryEntity.setName(categoryRequestDto.getName());
                categoryRepository.save(categoryEntity);
            } else {
                throw new NotFoundInDbException("The value is not valid.");
            }
        }
    }
}