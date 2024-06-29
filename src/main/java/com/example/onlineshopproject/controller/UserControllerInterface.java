package com.example.onlineshopproject.controller;

import com.example.onlineshopproject.dto.UserDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

@Tag(name = "Контроллер пользователей", description = "Контроллер для работы с пользователями")
public interface UserControllerInterface {
    @Operation(
            summary = "Получить игформацию о клиенте",
            description = "Позволяет получить информациюо клиенте по его Id"
    )
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id);
}
