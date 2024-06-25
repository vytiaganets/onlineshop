package com.example.onlineshop.controller;

import com.example.onlineshop.dto.UserCreateDto;
import com.example.onlineshop.exceptions.UserInvalidArgumentException;
import com.example.onlineshop.exceptions.UserNotFoundException;
import jakarta.validation.Valid;
import com.example.onlineshop.dto.UserDto;
import com.example.onlineshop.entity.UserEntity;
import com.example.onlineshop.mapper.UserMapper;
import com.example.onlineshop.security.AuthService;
import com.example.onlineshop.security.model.JwtAuthResponce;
import com.example.onlineshop.security.model.SignInRequest;
import com.example.onlineshop.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/users")
@Slf4j
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;
    private final AuthService authService;

    @GetMapping
    public ResponseEntity<List<UserDto>> listAll() {
        log.debug("Получение всех пользователей.");
        List<UserDto> userDto = userService.getAll()
                .stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(userDto);
    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@Valid @RequestBody UserCreateDto userCreateDto){
        UserEntity userEntity = userMapper.userCreateDtoToEntity(userCreateDto);
        UserEntity createdUserEntity = userService.create(userEntity);
        UserDto createdUserDto = userMapper.toDto(createdUserEntity);
        log.debug("Пользователь, зарегистрированный с помощью Id: {}", createdUserDto.getId());
        return new ResponseEntity<>(createdUserDto, HttpStatus.CREATED);
    }
    @PutMapping("/{id}")
    public ResponseEntity<UserDto> edit(@PathVariable Long id, @Valid @RequestBody UserCreateDto userCreateDto){
        log.debug("Попытка отредактировать пользователя с идентификатором: {}", id);
        UserEntity updatedUser = userService.edit(id, userCreateDto);
        return ResponseEntity.ok(userMapper.toDto(updatedUser));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id){
        log.debug("Попытка удалить пользователя с идентификатором: {}", id);
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
    @PostMapping("/login")
    public JwtAuthResponce login(@RequestBody SignInRequest request){
        return authService.authenticate(request);
    }
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleUserNotExceeption(UserNotFoundException ex){
        log.error("Пользователь не найден: {}", ex.getMessage());
        return ResponseEntity.notFound().build();
    }
    @ExceptionHandler(UserInvalidArgumentException.class)
    public ResponseEntity<String> handleUserInvalidArgumentException(UserInvalidArgumentException ex){
        log.error("Недопустимый аргумент пользователя: {}", ex.getMessage());
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}