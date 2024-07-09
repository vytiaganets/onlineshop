package com.example.onlineshopproject.service;

import com.example.onlineshopproject.dto.UserRequestDto;

import java.io.FileNotFoundException;
import java.util.List;

public interface UserService {
    void register(UserRequestDto userRequestDto);

    List<UserRequestDto> getAll();

    UserRequestDto getById(Long userId);

    UserRequestDto update(UserRequestDto userRequestDto) throws FileNotFoundException;

    UserRequestDto getByEmail(String email);

    UserRequestDto create(UserRequestDto userCredentialsDto);

    void delete(Long userId);

}
