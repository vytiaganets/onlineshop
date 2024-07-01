package com.example.onlineshopproject.controller;

import com.example.onlineshopproject.dto.UserDto;
import com.example.onlineshopproject.enums.UserRole;
import com.example.onlineshopproject.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserService userServiceMock;

    @Test
    void getUserTest() throws Exception {
        when(userServiceMock.getUser()).thenReturn(List.of(UserDto.builder().userId(1L).build()));
        this.mockMvc.perform(get("/uses")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$..userId").exists());
    }

    @Test
    void getUserByIdTest() throws Exception {
        when(userServiceMock.getUserById(anyLong())).thenReturn(UserDto.builder().userId(1L).build());
        this.mockMvc.perform(get("/users/{id}", 1)).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(1));
    }

    @Test
    void updateClientTest() throws Exception {
        UserDto expectedUser = UserDto.builder()
                .userId(1L)
                .email("andrii@ukr.net")
                .role(UserRole.ADMIN)
                .name("Test")
                .phoneNumber("0123456789")
                .passwordHash("*****")
                .build();
        when(userServiceMock.updateUser(any(UserDto.class))).thenReturn(expectedUser);
        this.mockMvc.perform(put("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                "userId":1,
                                "phoneNumber":"0123456789",
                                "name":"testName"
                                }
                                """))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(1));
    }
}