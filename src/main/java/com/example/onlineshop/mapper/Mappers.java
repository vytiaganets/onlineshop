package com.example.onlineshop.mapper;

import com.example.onlineshop.dto.CategoryDto;
import com.example.onlineshop.entity.CategoryEntity;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Mappers{
    private final ModelMapper modelMapper;
    public CategoryDto convertToCategoryDto(CategoryEntity categoryEntity){
        return modelMapper.map(categoryEntity, CategoryDto.class);
    }
    public CategoryEntity convertToCategory(CategoryDto categoryDto){
        return modelMapper.map(categoryDto, CategoryEntity.class);
    }
}
