package com.example.onlineshop.controller;

import com.example.onlineshop.dto.UserDto;
import com.example.onlineshop.entity.UserEntity;
import com.example.onlineshop.enums.UserRole;
import com.example.onlineshop.mapper.UserMapper;
import com.example.onlineshop.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {
    @Mock
    private UserService userService;
    @Mock
    private UserMapper userMapper;
    @InjectMocks
    private UserController userController;
    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();
    @BeforeEach
    public void setUp(){mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    void listAllUserDto() throws Exception{
        UserDto userDto = new UserDto(1L, "Test", "andrii@ukr.net","1234567890","hash", UserRole.USER);
        given(userService.getAll()).willReturn(List.of(new UserEntity()));
        given(userMapper.toDto(any(UserEntity.class))).willReturn(userDto);
        mockMvc.perform(get("/v1/users"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(List.of(userDto))));

    }
}
