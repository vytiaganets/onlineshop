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
public class UserServiceImplTest {
    @Mock
    private UserRepository userRepositoryMock;
    @Mock
    private Mappers mappersMock;
    @Mock
    private ModelMapper modelMapperMock;
    @InjectMocks
    private UserServiceImpl userServiceTest;

    private UserRequestDto expectedUserRequestDto;
    private UserEntity expectedUser;
    @BeforeEach
    void setUp(){
        expectedUserRequestDto = UserRequestDto.builder()
                .userId(1L)
                .email("andrii@ukr.net")
                .role(UserRole.ADMIN)
                .name("Test")
                .phoneNumber("123456789012")
                .passwordHash("1234")
                .build();
        expectedUser = UserEntity.builder()
                .userId(1L)
                .email("andrii@ukr.net")
                .role(UserRole.ADMIN)
                .name("Test")
                .phoneNumber("123456789012")
                .passwordHash("1234")
                .build();
    }
    @Test
    void getAll(){
        when(userRepositoryMock.findAll()).thenReturn(Arrays.asList(expectedUser));
        when(mappersMock.convertToUserResponseDto(any(UserEntity.class))).thenReturn(expectedUserRequestDto);
        List<UserRequestDto> actualUserRequestDtoList = userServiceTest.getAll();
        assertEquals(Arrays.asList(expectedUserRequestDto), actualUserRequestDtoList);
        ///Question
        //You haven't provided the instance at field declaration so I tried to construct the instance.
        //Examples of correct usage of @InjectMocks:
        //   @InjectMocks Service service = new Service();
        //   @InjectMocks Service service;
        //   //and... don't forget about some @Mocks for injection :)
    }
//    @Test
//    void getById(){
//        when(userRepositoryMock.findById(anyLong())).thenReturn(Optional.of(expectedUser));
//        when(mappersMock.convertToUserResponseDto(any(UserEntity.class))).thenReturn(expectedUserRequestDto);
//        UserRequestDto actualUserRequestDto = userServiceTest.getById(1L);
//        assertEquals(expectedUserRequestDto, actualUserRequestDto);
//    }
//    @Test
//    void updateUserTest() {
//
//    }
}
