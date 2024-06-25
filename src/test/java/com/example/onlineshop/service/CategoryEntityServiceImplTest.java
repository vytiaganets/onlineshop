package com.example.onlineshop.service;

import com.example.onlineshop.exceptions.CategoryNotFoundException;
import com.example.onlineshop.mapper.CategoryMapper;
import com.example.onlineshop.repository.CategoryJpaRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;


import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class CategoryEntityServiceImplTest {
    @Mock
    private CategoryJpaRepository categoryJpaRepository;
    @Mock
    private CategoryMapper categoryMapper;
    @InjectMocks
    private CategoryServiceImpl categoryServiceImpl;
    @Test
    void getCategoryById_WhenCategoryDoesNotExist(){
        Long id = 1L;
        when(categoryJpaRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(CategoryNotFoundException.class, () -> categoryServiceImpl.getCategoryById(id));
        verify(categoryJpaRepository).findById(id);
    }
}
