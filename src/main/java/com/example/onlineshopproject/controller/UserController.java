package com.example.onlineshopproject.controller;

import com.example.onlineshopproject.dto.UserDto;
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
    public ResponseEntity<List<UserDto>> getUsers(){
        log.debug("Getting all users.");
        List<UserDto> userDtoList = userService.getUser();
        return new ResponseEntity<>(userDtoList, HttpStatus.OK);
    }
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
}