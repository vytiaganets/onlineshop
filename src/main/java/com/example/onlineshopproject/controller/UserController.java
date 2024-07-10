package com.example.onlineshopproject.controller;

import com.example.onlineshopproject.dto.UserRequestDto;
import com.example.onlineshopproject.exceptions.UserInvalidArgumentException;
import com.example.onlineshopproject.exceptions.UserNotFoundException;
import com.example.onlineshopproject.service.UserService;
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

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/users")
@Validated
public class UserController implements UserControllerInterface {
    @Autowired
    UserService userService;


    @GetMapping
    public ResponseEntity<List<UserRequestDto>> getAll() {
        log.debug("Getting all users.");
        List<UserRequestDto> userRequestDtoList = userService.getAll();
        return new ResponseEntity<>(userRequestDtoList, HttpStatus.OK);
    }


    @GetMapping(value = "/{userId}")
    public ResponseEntity<UserRequestDto> getById(@PathVariable Long userId) {
        UserRequestDto userRequestDto = userService.getById(userId);
        return new ResponseEntity<>(userRequestDto, HttpStatus.OK);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public UserRequestDto update(@RequestBody UserRequestDto userRequestDto) throws FileNotFoundException {
        return userService.update(userRequestDto);
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable @Positive(message = "Product id must be a positive " +
            "number") Long userId) {
        userService.delete(userId);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleUserNotFoundException(UserNotFoundException userNotFoundException) {
        log.error("User not found:{}", userNotFoundException.getMessage());
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(UserInvalidArgumentException.class)
    public ResponseEntity<String> handleUserInvalidArgumentException(UserInvalidArgumentException userInvalidArgumentException) {
        log.error("Invalid user argument: {}", userInvalidArgumentException.getMessage());
        return ResponseEntity.badRequest().body(userInvalidArgumentException.getMessage());
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
}