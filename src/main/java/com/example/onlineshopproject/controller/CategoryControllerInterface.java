package com.example.onlineshopproject.controller;

import com.example.onlineshopproject.dto.CategoryRequestDto;
import com.example.onlineshopproject.dto.CategoryResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Tag(name = "Category Controller",
        description = "Controller for category operations")
public interface CategoryControllerInterface {
    @Operation(summary = "List all categories")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Found the categories",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CategoryResponseDto.class))})})
    public List<CategoryResponseDto> getAll();

    @Operation(summary = "Get a category by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Found the category",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CategoryResponseDto.class))}),
            @ApiResponse(responseCode = "404",
                    description = "Category not found",
                    content = @Content)})
    public CategoryResponseDto getById(@PathVariable Long categoryId);

    @Operation(summary = "Create a new category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Category created successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CategoryResponseDto.class))}),
            @ApiResponse(responseCode = "400",
                    description = "Invalid request body",
                    content = @Content)})
    public void insert(@RequestBody @Valid CategoryRequestDto categoryRequestDto);

    @Operation(summary = "Update an existing category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Category updated successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CategoryResponseDto.class))}),
            @ApiResponse(responseCode = "400",
                    description = "Invalid request body",
                    content = @Content),
            @ApiResponse(responseCode = "404",
                    description = "Category not found",
                    content = @Content)})
    public void update(@RequestBody @Valid CategoryRequestDto categoryRequestDto,
                       @PathVariable
                       @Positive(message = "Category id must be a positive number") Long categoryId);

    @Operation(summary = "Delete a category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Category deleted successfully"),
            @ApiResponse(responseCode = "404",
                    description = "Category not found")})
    public void deleteById(@PathVariable @Positive(message = "Category id must be a positive number") Long categoryId);
}
