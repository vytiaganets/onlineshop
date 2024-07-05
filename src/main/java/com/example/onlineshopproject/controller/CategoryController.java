package com.example.onlineshopproject.controller;

import com.example.onlineshopproject.dto.CategoryRequestDto;
import com.example.onlineshopproject.dto.CategoryResponseDto;
import com.example.onlineshopproject.exceptions.CategoryWrongValueException;
import com.example.onlineshopproject.exceptions.CategoryNotFoundException;
import com.example.onlineshopproject.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.spi.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/categories")
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CategoryResponseDto> getCategory() {
        return categoryService.getCategory();
    }

    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CategoryResponseDto getCategoryById(@PathVariable Long id) {
        return categoryService.getCategoryById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryResponseDto createCategory(@RequestBody CategoryRequestDto categoryRequestDto) {
        return categoryService.createCategory(categoryRequestDto);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public CategoryResponseDto updateCategory(@RequestBody CategoryResponseDto categoryResponseDto) {
        return categoryService.updateCategory(categoryResponseDto);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategoryById(@PathVariable Long id) {
        categoryService.deleteCategoryById(id);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<ErrorMessage> errorMessage(CategoryNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorMessage(exception.getMessage()));
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(CategoryWrongValueException.class)
    public ResponseEntity<ErrorMessage> errorMessage(CategoryWrongValueException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorMessage(exception.getMessage()));
    }
}
