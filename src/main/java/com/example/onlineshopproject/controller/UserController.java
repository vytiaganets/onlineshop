package com.example.onlineshopproject.controller;

import com.example.onlineshopproject.dto.UserDto;
import com.example.onlineshopproject.exceptions.ErrorParamException;
import com.example.onlineshopproject.exceptions.NotFoundInDbException;
import com.example.onlineshopproject.service.UserService;
import jakarta.validation.Valid;
import org.springdoc.api.ErrorMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;

@RestController
@RequestMapping("/v1/users")
public class UserController implements UserControllerInterface {
    @Autowired
    UserService userService;

    @GetMapping(value = "/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        UserDto userDto = userService.getUserById(id);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<UserDto> updateClient(@RequestBody @Valid UserDto userDto) throws FileNotFoundException {
        UserDto client = userService.updateUser(userDto);
        return new ResponseEntity<>(client, HttpStatus.OK);
    }

    @ExceptionHandler(ErrorParamException.class)
    public ResponseEntity<ErrorMessage> handleException(ErrorParamException errorParamException) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorMessage(errorParamException.getMessage() + "!"));
    }

    @ExceptionHandler(NotFoundInDbException.class)
    public ResponseEntity<ErrorMessage> handleException(NotFoundInDbException notFoundInDbException) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorMessage(notFoundInDbException.getMessage() + "!"));
    }
}