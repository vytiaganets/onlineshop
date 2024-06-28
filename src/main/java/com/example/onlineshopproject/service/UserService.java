package com.example.onlineshopproject.service;

import com.example.onlineshopproject.dto.UserDto;
import com.example.onlineshopproject.entity.UserEntity;
import com.example.onlineshopproject.mapper.Mappers;
import com.example.onlineshopproject.repository.CartRepository;
import com.example.onlineshopproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final Mappers mappers;

    public UserDto registerUser(UserDto userDto){
        return null;
    }

    public UserEntity getByLogin(String username) {
        return null;
    }

}
