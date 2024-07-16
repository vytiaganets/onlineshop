//package com.example.onlineshopproject.controller;
//
//import com.example.onlineshopproject.dto.CategoryRequestDto;
//import com.example.onlineshopproject.dto.CategoryResponseDto;
//import com.example.onlineshopproject.service.CategoryService;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//
//import java.util.List;
//
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@WebMvcTest(CategoryControllerTest.class)
//class CategoryControllerTest {
//    @Autowired
//    private MockMvc mockMvc;
//    @Autowired
//    private ObjectMapper objectMapper;
//    @MockBean
//    private CategoryService categoryServiceMock;
//    private CategoryRequestDto categoryRequestDto;
//    private CategoryResponseDto categoryResponseDto;
//    @BeforeEach
//    void setUp(){
//        categoryResponseDto = CategoryResponseDto
//                .builder()
//                .categoryId(1L)
//                .name("Name")
//                .build();
//        categoryRequestDto = CategoryRequestDto
//                .builder()
//                .name("Name")
//                .build();
//    }
//    @Test
//    void getAll() throws Exception{
//        when(categoryServiceMock.getAll()).thenReturn(List.of(categoryResponseDto));
//        this.mockMvc.perform(get("/categories"))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$..categyId")
//                        .value(1))
//                .andExpect(jsonPath("$..name").value("Name"));
//    }
//    @Test
//    void deleteProductsById() throws Exception{
//        Long id = 1L;
//        mockMvc.perform(delete("/categories/{id}",id)).andDo(print())
//                .andExpect(status().isOk());
//    }
//    @Test
//    void insert()throws Exception{
//        mockMvc.perform(post("/categories")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(categoryRequestDto)))
//                .andDo(print())
//                .andExpect(status().isCreated());
//    }
//    @Test
//    void update() throws Exception{
//        Long id= 1L;
//        this.mockMvc.perform(put("/categories/{id}",id)
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(categoryRequestDto)))
//                .andDo(print())
//                .andExpect(status().isOk());
//    }
//}
