package com.example.onlineshopproject.service;

import com.example.onlineshopproject.configuration.MapperConfiguration;
import com.example.onlineshopproject.dto.CategoryRequestDto;
import com.example.onlineshopproject.dto.CategoryResponseDto;
import com.example.onlineshopproject.entity.CategoryEntity;
import com.example.onlineshopproject.exceptions.NotFoundInDbException;
import com.example.onlineshopproject.mapper.Mappers;
import com.example.onlineshopproject.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private static final Logger log = LoggerFactory.getLogger(CategoryServiceImpl.class);
    private final CategoryRepository categoryRepository;
    private final Mappers mappers;

    @Transactional
    public List<CategoryResponseDto> getAll() {
        log.debug("Attempting receive all categories.");
        List<CategoryEntity> categoryEntityList = categoryRepository.findAll();
        List<CategoryResponseDto> categoryResponseDtoList = MapperConfiguration.convertList(categoryEntityList,
                mappers::convertToCategoryResponseDto);
        log.debug("Returning all categories: {}", categoryResponseDtoList.size());
        return categoryResponseDtoList;
    }

    @Transactional
    public CategoryResponseDto getById(Long categoryId) {
        log.debug("Attempting category with id: {}", categoryId);
        Optional<CategoryEntity> categoryEntityOptional = categoryRepository.findById(categoryId);
        CategoryResponseDto categoryResponseDto = null;
        if (!categoryEntityOptional.isPresent()) {
            categoryResponseDto = categoryEntityOptional.map(mappers::convertToCategoryResponseDto).orElse(null);
        }
        log.debug("Returning category by id: {}", categoryResponseDto.getCategoryId());
        return categoryResponseDto;
    }

    @Transactional
    public void insert(CategoryRequestDto categoryRequestDto) {
        log.debug("Attempting insert category: {}", categoryRequestDto.getName());
        CategoryEntity categoryEntity = categoryRepository.findCategoryEntityByName(categoryRequestDto.getName());
        if (categoryEntity == null) {
            CategoryEntity categoryEntity1 = mappers.convertToCategoryEntity(categoryRequestDto);
            categoryEntity.setCategoryId(0L);
            categoryRepository.save(categoryEntity);
        } else {
            log.error("Category not found: {}", categoryRequestDto.getName());
            throw new NotFoundInDbException("The category with name already exists.");
        }
    }

    @Transactional
    public CategoryResponseDto create(CategoryRequestDto categoryRequestDto) {
        log.debug("Attempting create category: {}", categoryRequestDto.getName());
        CategoryEntity categoryEntity = mappers.convertToCategoryEntity(categoryRequestDto);
        categoryEntity.setCategoryId(0L);
        CategoryEntity savedCategory = categoryRepository.save(categoryEntity);
        return mappers.convertToCategoryResponseDto(savedCategory);
    }
    @Transactional
    public void deleteById(Long categoryId) {
        log.debug("Attempting delete category: {}", categoryId);
        Optional<CategoryEntity> categoryEntity = categoryRepository.findById(categoryId);
        if (categoryEntity.isPresent()) {
            categoryRepository.deleteById(categoryId);
        } else {
            log.error("Category not found: {}", categoryId);
            throw new NotFoundInDbException("Data not found in database.");
        }
    }

    @Transactional
    public void update(CategoryRequestDto categoryRequestDto, Long categoryId) {
        log.debug("Attempting update category: {}", categoryId);
        if (categoryId > 0) {
            CategoryEntity categoryEntity = categoryRepository.findById(categoryId).orElse(null);
            if (categoryEntity != null) {
                categoryEntity.setName(categoryRequestDto.getName());
                categoryRepository.save(categoryEntity);
            } else {
                log.error("Category not found: {}", categoryId);
                throw new NotFoundInDbException("The value is not valid.");
            }
        }
    }
}