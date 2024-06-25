package com.example.onlineshop.service;

import com.example.onlineshop.dto.UserCreateDto;
import com.example.onlineshop.entity.UserEntity;
import com.example.onlineshop.mapper.UserMapper;
import com.example.onlineshop.repository.UserJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @Mock
    private UserJpaRepository userJpaRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserServiceImpl userService;

    private UserEntity userEntity;
    private UserCreateDto userCreateDto;
    @BeforeEach
    void setUp() {
        userEntity = new UserEntity();
        userEntity.setId(1L);
        userEntity.setName("Test User");
        userEntity.setEmail("test@example.com");
        userEntity.setPassword("hashedPassword");

        userCreateDto = new UserCreateDto();
        userCreateDto.setName("Test User");
        userCreateDto.setEmail("test@example.com");
        userCreateDto.setPassword("password");
    }

//    @Disabled
//    @Test
//    void create_ShouldCreateUser() {
//        when(userMapper.userCreateDtoToEntity(any(UserCreateDto.class))).thenReturn(userEntity);
//        when(userJpaRepository.save(any(UserEntity.class))).thenReturn(userEntity);
//
//        UserEntity createdUser = userService.create(userEntity);
//
//        assertThat(createdUser).isNotNull();
//        assertThat(createdUser.getName()).isEqualTo(userCreateDto.getName());
//        verify(userJpaRepository).save(any(UserEntity.class));
//    }
}
