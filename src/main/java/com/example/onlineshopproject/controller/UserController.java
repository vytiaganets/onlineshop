package com.example.onlineshopproject.controller;

import com.example.onlineshopproject.dto.UserRequestDto;
import com.example.onlineshopproject.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.util.List;
@Tag(name = "User Controller", description = "Controller for user operations")
@RestController
@Slf4j
@RequestMapping("/v1/users")
public class UserController implements UserControllerInterface {
    @Autowired
    UserService userService;
    @GetMapping
    public ResponseEntity<List<UserRequestDto>> getUsers(){
        log.debug("Getting all users.");
        List<UserRequestDto> userRequestDtoList = userService.getUser();
        return new ResponseEntity<>(userRequestDtoList, HttpStatus.OK);
    }
    @GetMapping(value = "/{id}")
    public ResponseEntity<UserRequestDto> getUserById(@PathVariable Long id) {
        UserRequestDto userRequestDto = userService.getUserById(id);
        return new ResponseEntity<>(userRequestDto, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<UserRequestDto> updateClient(@RequestBody @Valid UserRequestDto userRequestDto) throws FileNotFoundException {
        UserRequestDto client = userService.updateUser(userRequestDto);
        return new ResponseEntity<>(client, HttpStatus.OK);
    }
}