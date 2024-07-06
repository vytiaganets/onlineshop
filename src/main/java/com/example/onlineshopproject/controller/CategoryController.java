package com.example.onlineshopproject.controller;

import com.example.onlineshopproject.dto.CategoryRequestDto;
import com.example.onlineshopproject.dto.CategoryResponseDto;
import com.example.onlineshopproject.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Category Controller",
        description = "Controller for category operations")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/categories")
public class CategoryController {
    private final CategoryService categoryService;

    @Operation(summary = "List all categories")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Found the categories",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CategoryResponseDto.class))}),
            @ApiResponse(responseCode = "404",
                    description = "Categories not found",
                    content = @Content)})
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CategoryResponseDto> getCategory() {
        return categoryService.getCategory();
    }

    @Operation(summary = "Get a category by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Found the category",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CategoryResponseDto.class))}),
            @ApiResponse(responseCode = "404",
                    description = "Category not found",
                    content = @Content)})
    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CategoryResponseDto getCategoryById(@PathVariable Long id) {
        return categoryService.getCategoryById(id);
    }

    @Operation(summary = "Create a new category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Category created successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CategoryResponseDto.class))})})
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void insertCategory(@RequestBody @Valid CategoryRequestDto categoryRequestDto) {
        categoryService.createCategory(categoryRequestDto);
    }

    @Operation(summary = "Update an existing category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Category updated successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CategoryResponseDto.class))}),
            @ApiResponse(responseCode = "404",
                    description = "Category not found",
                    content = @Content)})
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updateCategory(@RequestBody @Valid CategoryRequestDto categoryRequestDto,
                               @PathVariable
                               @Positive(message = "Category id must be a positive number") Long id) {
        categoryService.updateCategory(categoryRequestDto, id);
    }

    @Operation(summary = "Delete a category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Category deleted successfully"),
            @ApiResponse(responseCode = "404",
                    description = "Category not found")})
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategoryById(@PathVariable @Positive(message = "Category id must be a positive number") Long id) {
        categoryService.deleteCategoryById(id);
    }
}
