package com.example.onlineshopproject.controller;

import com.example.onlineshopproject.dto.UserRequestDto;
import com.example.onlineshopproject.dto.UserResponseDto;
import com.example.onlineshopproject.service.UserService;
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
@RequestMapping("/v1/users")
@Validated
public class UserController implements UserControllerInterface {
    @Autowired
    UserService userService;
    @Operation(summary = "Get users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Users found successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserResponseDto.class))}),
            @ApiResponse(responseCode = "404", description = "Users not found", content = @Content)})
    @GetMapping
    public ResponseEntity<List<UserRequestDto>> getUsers(){
        log.debug("Getting all users.");
        List<UserRequestDto> userRequestDtoList = userService.getUser();
        return new ResponseEntity<>(userRequestDtoList, HttpStatus.OK);
    }
    @Operation(summary = "Get a user by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "User found successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserResponseDto.class))}),
            @ApiResponse(responseCode = "404",
                    description = "User not found",
                    content = @Content)})
    @GetMapping(value = "/{id}")
    public ResponseEntity<UserRequestDto> getUserById(@PathVariable Long id) {
        UserRequestDto userRequestDto = userService.getUserById(id);
        return new ResponseEntity<>(userRequestDto, HttpStatus.OK);
    }
    @Operation(summary = "Update user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Users updated successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserResponseDto.class))}),
            @ApiResponse(responseCode = "404", description = "Users not updated", content = @Content)})
    @PutMapping
    public ResponseEntity<UserRequestDto> updateClient(@RequestBody @Valid UserRequestDto userRequestDto) throws FileNotFoundException {
        UserRequestDto client = userService.updateUser(userRequestDto);
        return new ResponseEntity<>(client, HttpStatus.OK);
    }
    @Operation(summary = "Register user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Users registered successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserResponseDto.class))}),
            @ApiResponse(responseCode = "404", description = "Users not registered", content = @Content)})
    @Validated
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void registerUser(@RequestBody UserRequestDto userRequestDto){
        userService.registerUser((userRequestDto));
    }
    @Operation(summary = "Update user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Users updated successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserResponseDto.class))}),
            @ApiResponse(responseCode = "404", description = "Users not updated", content = @Content)})
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserRequestDto updateUser(@RequestBody UserRequestDto userRequestDto) throws FileNotFoundException {
       return userService.updateUser(userRequestDto);
    }
    @Operation(summary = "Delete user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Users deleted successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserResponseDto.class))}),
            @ApiResponse(responseCode = "404", description = "Users not deleted", content = @Content)})
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteUser(@PathVariable @Positive(message = "Product id must be a positive " +
            "number") Long id){
        userService.deleteUser(id);
    }
}