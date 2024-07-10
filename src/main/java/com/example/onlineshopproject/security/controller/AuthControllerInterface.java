package com.example.onlineshopproject.security.controller;

import com.example.onlineshopproject.dto.UserRequestDto;
import com.example.onlineshopproject.dto.UserResponseDto;
import com.example.onlineshopproject.exceptions.ResponseException;
import com.example.onlineshopproject.security.jwt.JwtRequest;
import com.example.onlineshopproject.security.jwt.JwtRequestRefresh;
import com.example.onlineshopproject.security.jwt.JwtResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.security.auth.message.AuthException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Auth Controller", description = "Controller for auth operations")
public interface AuthControllerInterface {
    @Operation(summary = "Login")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Login successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserResponseDto.class))}),
            @ApiResponse(responseCode = "401",
                    description = "Unauthorized",
                    content = @Content)})
    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest authRequest) throws AuthException;

    @Operation(summary = "Get a new access token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Access token found successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserResponseDto.class))}),
            @ApiResponse(responseCode = "400",
                    description = "Invalid request body",
                    content = @Content)})
    public ResponseEntity<JwtResponse> getNewAccessToken(@RequestBody JwtRequestRefresh jwtRequestRefresh) throws AuthException;

    @Operation(summary = "Get a new refresh token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "New refresh token found successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserResponseDto.class))}),
            @ApiResponse(responseCode = "400",
                    description = "Invalid request body",
                    content = @Content)})
    public ResponseEntity<JwtResponse> getNewRefreshToken(@RequestBody JwtRequestRefresh jwtRequestRefresh) throws AuthException;

    @Operation(summary = "Register")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Register created successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserResponseDto.class))}),
            @ApiResponse(responseCode = "400",
                    description = "Invalid request body",
                    content = @Content)})
    public ResponseEntity<UserRequestDto> register(@RequestBody UserRequestDto userCredentialsDto) throws ResponseException;
}
