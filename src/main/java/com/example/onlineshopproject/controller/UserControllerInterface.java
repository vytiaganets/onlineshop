package com.example.onlineshopproject.controller;

import com.example.onlineshopproject.dto.UserDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

@Tag(name = "User Controller", description = "Controller for working with users")
public interface UserControllerInterface {
    @Operation(
            summary = "Get customer information",
            description = "Allows you to obtain information about the client by Id"
    )
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id);
}
