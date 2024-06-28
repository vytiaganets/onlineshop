package com.example.onlineshopproject.service;

import com.example.onlineshopproject.configuration.MapperConfiguration;
import com.example.onlineshopproject.dto.CategoryDto;
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

    public List<CategoryDto> getCategory() {
        List<CategoryEntity> categoryEntityList = categoryRepository.findAll();
        List<CategoryDto> categoryDtoList = MapperConfiguration.convertList(categoryEntityList, mappers::convertToCategoryDto);
        return categoryDtoList;
    }

    public CategoryDto getCategoryById(Long id) {
        Optional<CategoryEntity> categoryEntityOptional = categoryRepository.findById(id);
        CategoryDto categoryDto = null;
        if (!categoryEntityOptional.isPresent()) {
            categoryDto = categoryEntityOptional.map(mappers::convertToCategoryDto).orElse(null);
        }
        return categoryDto;
    }

    public CategoryDto createCategory(CategoryDto categoryDto) {
        CategoryEntity categoryEntity = mappers.convertToCategoryEntity(categoryDto);
        categoryEntity.setCategoryId(0L);
        CategoryEntity savedCategory = categoryRepository.save(categoryEntity);
        return mappers.convertToCategoryDto(savedCategory);
    }

    public void deleteCategoryById(Long id){
        Optional<CategoryEntity> categoryEntity = categoryRepository.findById(id);
        if(categoryEntity.isPresent()){
            categoryRepository.deleteById(id);
        }
    }
    public CategoryDto updateCategory(CategoryDto categoryDto){
        if(categoryDto.getCategoryId() <= 0){
            return null;
        }
        Optional<CategoryEntity> categoryEntityOptional = categoryRepository.findById(categoryDto.getCategoryId());
        if(!categoryEntityOptional.isPresent()){
            return null;
            //...
        }
        CategoryEntity categoryEntity = categoryEntityOptional.get();
        categoryEntity.setName(categoryDto.getName());
        CategoryEntity savedCategory = categoryRepository.save(categoryEntity);
        return mappers.convertToCategoryDto(savedCategory);
    }
}