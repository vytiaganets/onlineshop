package com.example.onlineshopproject.controller;
import com.example.onlineshopproject.dto.CategoryResponseDto;
import com.example.onlineshopproject.dto.ProductRequestDto;
import com.example.onlineshopproject.dto.ProductResponseDto;
import com.example.onlineshopproject.service.ProductServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductControllerTest.class)
public class ProductControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private ProductServiceImpl productServiceImplMock;
    private ProductRequestDto productRequestDto;
    private ProductResponseDto productResponseDto;
    @BeforeEach
    void setUp(){
        productResponseDto = ProductResponseDto
                .builder()
                .productId(1L)
                .name("Name")
                .description("Description")
                .price(new BigDecimal("10.00"))
                .discountPrice(new BigDecimal("10.00"))
                .createAt(Timestamp.valueOf(LocalDateTime.now()))
                .updateAt(Timestamp.valueOf(LocalDateTime.now()))
                .imageURL("http://localhost:8080/images/1.jpg")
                .categoryResponseDto(CategoryResponseDto
                        .builder()
                        .categoryId(1L)
                        .name("Category test")
                        .build())
                .build();
        productRequestDto = ProductRequestDto
                .builder()
                .name("Name")
                .description("Description")
                .price((new BigDecimal("11.00")))
                .discountPrice(new BigDecimal("11.00"))
                .imageUrl("http://localhost:8080/images/1.jpg")
                .categoryEntity("Category test")
                .build();
    }
    @Test
    void getById() throws Exception{
        Long productId = 1L;
        when(productServiceImplMock.getById(anyLong())).thenReturn(productResponseDto);
        this.mockMvc.perform(get("/products/{productId}"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productId")
                        .value(1))
                .andExpect(jsonPath("$.name").value("Name"));
    }
    @Test
    void deleteById() throws Exception{
        Long productId = 1L;
        mockMvc.perform(delete("/products/{productId}",productId)).andDo(print())
                .andExpect(status().isOk());
    }
    @Test
    void insert()throws Exception{
        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productRequestDto)))
                .andDo(print())
                .andExpect(status().isCreated());
    }
    @Test
    void update() throws Exception{
        Long id= 1L;
        this.mockMvc.perform(put("/products/{productId}",id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productRequestDto)))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
