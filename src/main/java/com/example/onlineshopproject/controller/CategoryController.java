package com.example.onlineshopproject.controller;

import com.example.onlineshopproject.dto.CategoryRequestDto;
import com.example.onlineshopproject.dto.CategoryResponseDto;
import com.example.onlineshopproject.exceptions.CategoryInvalidArgumentException;
import com.example.onlineshopproject.exceptions.CategoryNotFoundException;
import com.example.onlineshopproject.exceptions.UserInvalidArgumentException;
import com.example.onlineshopproject.exceptions.UserNotFoundException;
import com.example.onlineshopproject.service.CategoryService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/categories")
public class CategoryController implements CategoryControllerInterface {
    private final CategoryService categoryService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CategoryResponseDto> getAll() {
        return categoryService.getAll();
    }

    @GetMapping(value = "/{categoryId}")
    @ResponseStatus(HttpStatus.OK)
    public CategoryResponseDto getById(@PathVariable Long categoryId) {
        return categoryService.getById(categoryId);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void insert(@RequestBody @Valid CategoryRequestDto categoryRequestDto) {
        categoryService.create(categoryRequestDto);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping(value = "/{categoryId}")
    @ResponseStatus(HttpStatus.OK)
    public void update(@RequestBody @Valid CategoryRequestDto categoryRequestDto,
                       @PathVariable
                       @Positive(message = "Category id must be a positive number") Long categoryId) {
        categoryService.update(categoryRequestDto, categoryId);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping(value = "/{categoryId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable @Positive(message = "Category id must be a positive number") Long categoryId) {
        categoryService.deleteById(categoryId);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(CategoryNotFoundException.class)
    public Map<String, String> handleCategoryNotFoundException(CategoryNotFoundException categoryNotFoundException) {
        log.error("Category not found:{}", categoryNotFoundException.getMessage());
        Map<String, String> map = new HashMap<>();
        map.put("error", categoryNotFoundException.getMessage());
        return map;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(CategoryInvalidArgumentException.class)
    public Map<String, String> handleCategoryInvalidArgumentException(CategoryInvalidArgumentException categoryInvalidArgumentException) {
        log.error("Invalid category argument: {}", categoryInvalidArgumentException.getMessage());
        Map<String, String> map = new HashMap<>();
        map.put("error", categoryInvalidArgumentException.getMessage());
        return map;
    }
}
