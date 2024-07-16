package com.example.onlineshopproject.service;

import com.example.onlineshopproject.dto.CategoryRequestDto;
import com.example.onlineshopproject.dto.CategoryResponseDto;
import com.example.onlineshopproject.entity.CategoryEntity;
import com.example.onlineshopproject.exceptions.*;
import com.example.onlineshopproject.mapper.Mappers;
import com.example.onlineshopproject.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceImplTest {
    @Mock
    private CategoryRepository categoryRepositoryMock;
    @Mock
    private Mappers mappersMock;
    @InjectMocks
    private CategoryServiceImpl categoryServiceMock;
    private CategoryResponseDto categoryResponseDto;
    private CategoryRequestDto categoryRequestDto, incorrectCategoryRequestDto;
    private CategoryEntity categoryEntity;
    CategoryNotFoundException categoryNotFoundException;
    CategoryInvalidArgumentException categoryInvalidArgumentException;
    CategoryWrongValueException categoryWrongValueException;
    @BeforeEach
    void setUp(){
        categoryResponseDto = CategoryResponseDto
                .builder()
                .categoryId(1L)
                .name("Test category")
                .build();
        categoryEntity = new CategoryEntity(
                1L,
                "Test category",
                null
        );
        categoryRequestDto = CategoryRequestDto
                .builder()
                .name("Test category")
                .build();
        incorrectCategoryRequestDto = CategoryRequestDto
                .builder()
                .name("Incorrect category")
                .build();
    }
    @Test
    void getAll(){
        when(categoryRepositoryMock.findAll()).thenReturn(List.of(categoryEntity));
        when(mappersMock.convertToCategoryResponseDto(any(CategoryEntity.class))).thenReturn(categoryResponseDto);
        List<CategoryResponseDto> actualList = categoryServiceMock.getAll();
        verify(mappersMock, times(1)).convertToCategoryResponseDto(any(CategoryEntity.class));
        assertFalse(actualList.isEmpty());
        assertEquals(categoryEntity.getCategoryId(), actualList.get(0).getCategoryId());
    }
//    @Test
//    void insert(){
//        when(categoryRepositoryMock.findCategoryEntityByName(categoryRequestDto.getName())).thenReturn(null);
//        when(mappersMock.convertToCategoryEntity(any(CategoryRequestDto.class))).thenReturn(categoryEntity);
//        categoryEntity.setCategoryId(1L);
//        categoryServiceMock.insert(categoryRequestDto);
//        verify(categoryRepositoryMock, times(1)).save(any(CategoryEntity.class));
//        when(categoryRepositoryMock.findCategoryEntityByName(incorrectCategoryRequestDto.getName())).thenReturn(categoryEntity);
//        categoryWrongValueException = assertThrows(CategoryWrongValueException.class,
//                () -> categoryServiceMock.insert(incorrectCategoryRequestDto));
//   ///Question
//        //java.lang.NullPointerException: Cannot invoke "com.example.onlineshopproject.entity.CategoryEntity.setCategoryId(java.lang.Long)" because "categoryEntity" is null
//    }

    @Test
    void deleteById(){
        Long categoryId = 1L;
        Long incorrectCategoryId = 9L;
        when(categoryRepositoryMock.findById(categoryId)).thenReturn(Optional.of(categoryEntity));
        categoryServiceMock.deleteById(categoryId);
        verify(categoryRepositoryMock, times(1)).findById(categoryId);
        verify(categoryRepositoryMock, times(1)).deleteById(categoryId);
        when(categoryRepositoryMock.findById(incorrectCategoryId)).thenReturn(Optional.empty());
        categoryNotFoundException = assertThrows(CategoryNotFoundException.class,
                () -> categoryServiceMock.deleteById(incorrectCategoryId));
        assertEquals("Data not found in database.", categoryNotFoundException.getMessage());
    }

}
