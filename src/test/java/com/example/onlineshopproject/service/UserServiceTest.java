package com.example.onlineshopproject.service;

import com.example.onlineshopproject.dto.UserRequestDto;
import com.example.onlineshopproject.entity.UserEntity;
import com.example.onlineshopproject.enums.UserRole;
import com.example.onlineshopproject.mapper.Mappers;
import com.example.onlineshopproject.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserRepository userRepositoryMock;
    @Mock
    private Mappers mappersMock;
    @Mock
    private ModelMapper modelMapperMock;
    @InjectMocks
    private UserService userServiceTest;
    private UserRequestDto expectedUserRequestDto;
    private UserEntity expectedUser;
    @BeforeEach
    void setUp(){
        expectedUserRequestDto = UserRequestDto.builder()
                .userId(1L)
                .email("andrii@ukr.net")
                .role(UserRole.ADMIN)
                .name("Test")
                .phoneNumber("012 345 6789")
                .passwordHash("*****")
                .build();
        expectedUser = UserEntity.builder()
                .userId(1L)
                .email("andrii@ukr.net")
                .role(UserRole.ADMIN)
                .name("Test")
                .phoneNumber("012 345 6789")
                .passwordHash("*****")
                .build();
    }
    @Test
    void getUserTest(){
        when(userRepositoryMock.findAll()).thenReturn(Arrays.asList(expectedUser));
        when(mappersMock.convertToUserResponseDto(any(UserEntity.class))).thenReturn(expectedUserRequestDto);
        List<UserRequestDto> actualUserRequestDtoList = userServiceTest.getUser();
        assertEquals(Arrays.asList(expectedUserRequestDto), actualUserRequestDtoList);
    }
    @Test
    void getUserByIdTest(){
        when(userRepositoryMock.findById(anyLong())).thenReturn(Optional.of(expectedUser));
        when(mappersMock.convertToUserResponseDto(any(UserEntity.class))).thenReturn(expectedUserRequestDto);
        UserRequestDto actualUserRequestDto = userServiceTest.getUserById(1L);
        assertEquals(expectedUserRequestDto, actualUserRequestDto);
    }
    @Test
    void updateUserTest() {

    }
}
