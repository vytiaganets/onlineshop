package com.example.onlineshopproject.controller;

import com.example.onlineshopproject.dto.UserRequestDto;
import com.example.onlineshopproject.dto.UserResponseDto;
import com.example.onlineshopproject.service.UserServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.util.List;

@Tag(name = "User Controller", description = "Controller for user operations")
@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/users")
@Validated
public class UserController {
    @Autowired
    UserServiceImpl userServiceImpl;

    @Operation(summary = "Get users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Users found successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserResponseDto.class))}),
            @ApiResponse(responseCode = "404", description = "Users not found", content = @Content)})
    @GetMapping
    public ResponseEntity<List<UserRequestDto>> getAll() {
        log.debug("Getting all users.");
        List<UserRequestDto> userRequestDtoList = userServiceImpl.getAll();
        return new ResponseEntity<>(userRequestDtoList, HttpStatus.OK);
    }

    @Operation(summary = "Get a user by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "User found successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserResponseDto.class))}),
            @ApiResponse(responseCode = "400",
                    description = "Invalid request body",
                    content = @Content),
            @ApiResponse(responseCode = "404",
                    description = "User not found",
                    content = @Content)})
    @GetMapping(value = "/{userId}")
    public ResponseEntity<UserRequestDto> getById(@PathVariable Long userId) {
        UserRequestDto userRequestDto = userServiceImpl.getById(userId);
        return new ResponseEntity<>(userRequestDto, HttpStatus.OK);
    }

//    @Operation(summary = "Register user")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200",
//                    description = "Users registered successfully",
//                    content = {@Content(mediaType = "application/json",
//                            schema = @Schema(implementation = UserResponseDto.class))}),
//            @ApiResponse(responseCode = "404", description = "Users not registered", content = @Content)})
//    @Validated
//    @PostMapping("/register")
//    @ResponseStatus(HttpStatus.CREATED)
//    public void register(@RequestBody UserRequestDto userRequestDto) {
//        userServiceImpl.register((userRequestDto));
//    }

    @Operation(summary = "Update a user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Users updated successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserResponseDto.class))}),
            @ApiResponse(responseCode = "400",
                    description = "Invalid request body",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Users not updated", content = @Content)})
    @PutMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public UserRequestDto update(@RequestBody UserRequestDto userRequestDto) throws FileNotFoundException {
        return userServiceImpl.update(userRequestDto);
    }

    @Operation(summary = "Delete user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Users deleted successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserResponseDto.class))}),
            @ApiResponse(responseCode = "404", description = "Users not deleted", content = @Content)})
    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable @Positive(message = "Product id must be a positive " +
            "number") Long id) {
        userServiceImpl.delete(id);
    }
}