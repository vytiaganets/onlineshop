//package com.example.onlineshopproject.controller;
//
//import com.example.onlineshopproject.dto.UserRequestDto;
//import com.example.onlineshopproject.service.UserService;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Disabled;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//
//import java.util.List;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.anyLong;
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//@WebMvcTest(UserController.class)
//public class UserControllerTest {
//    @Autowired
//    private MockMvc mockMvc;
//    @Autowired
//    private ObjectMapper objectMapper;
//    @MockBean
//    private UserService userServiceMock;
//    private UserRequestDto userRequestDto;
//@BeforeEach
//void setUp(){
//    userRequestDto = UserRequestDto
//            .builder()
//            .userId(1L)
//            .name("Andreas Schwarzberg")
//            .email("schwarzberd@web.de")
//            .phoneNumber("123456789012")
//            .passwordHash("1234")
//            .build();
//}
//
//    @Test
//    void getAll() throws Exception {
//        when(userServiceMock
//                .getAll())
//                .thenReturn(List
//                        .of(UserRequestDto
//                                .builder()
//                                .userId(1L)
//                                .build()));
//        this.mockMvc
//                .perform(get("/users"))
//                .andDo(print())
//                .andExpect(status()
//                        .isOk())
//                .andExpect(jsonPath("$..userId")
//                        .exists());
//    }
//    @Test
//    void getByIdTest() throws Exception {
//        when(userServiceMock
//                .getById(anyLong()))
//                .thenReturn(UserRequestDto
//                        .builder()
//                        .userId(1L)
//                        .build());
//        this.mockMvc
//                .perform(get("/users/{userId}", 1))
//                .andDo(print())
//                .andExpect(status()
//                        .isOk())
//                .andExpect(jsonPath("$.userId")
//                        .value(1));
//    }
//
//    @Test
//    void update() throws Exception {
//    when(userServiceMock
//            .update(any(UserRequestDto.class)))
//            .thenReturn(userRequestDto);
//        this.mockMvc
//                .perform(put("/users")
//                        .contentType(MediaType
//                                .APPLICATION_JSON)
//                        .content(objectMapper
//                                .writeValueAsString(userRequestDto)))
//                .andDo(print())
//                .andExpect(status()
//                        .isOk())
//                .andExpect(jsonPath("$.userId")
//                        .value(1));
//    }
//}