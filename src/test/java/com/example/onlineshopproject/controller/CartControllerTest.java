package com.example.onlineshopproject.controller;
import com.example.onlineshopproject.dto.*;
import com.example.onlineshopproject.enums.UserRole;
//import com.example.onlineshopproject.service.CartItemServiceImpl;
import com.example.onlineshopproject.service.CartServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CartController.class)
class CartControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private CartServiceImpl cartServiceImplMock;

    private UserResponseDto userResponseDto;
    private CartResponseDto cartResponseDto;
    private CartItemResponseDto cartItemResponseDto;
    private CartItemRequestDto cartItemRequestDto;
    private ProductResponseDto productResponseDto;
    private Set<CartItemResponseDto> cartItemResponseDtoSet = new HashSet<>();
    @BeforeEach
    void setUp(){
        userResponseDto = UserResponseDto
                .builder()
                .userId(1L)
                .name("Andreas Schwarzberg")
                .email("schwarzberd@web.de")
                .phone("123456789012")
                .password("1234")
                .userRole(UserRole.USER)
                .build();
        cartResponseDto = CartResponseDto
                .builder()
                .cartId(1L)
                .userResponseDto(userResponseDto)
                .build();
        productResponseDto = ProductResponseDto
                .builder()
                .productId(1L)
                .name("Name")
                .description("Description")
                .price(new BigDecimal("10.00"))
                .imageURL("http://localhost/img/1.jpg")
                .createAt(Timestamp.valueOf(LocalDateTime.now()))
                .categoryResponseDto(new CategoryResponseDto(1L, "Category"))
                .build();
        cartItemResponseDto = CartItemResponseDto
                .builder()
                .cartItemId(1L)
                .cartResponseDto(cartResponseDto)
                .productResponseDto(productResponseDto)
                .quantity(3)
                .build();
        cartItemResponseDtoSet.add(cartItemResponseDto);
        cartItemRequestDto= CartItemRequestDto
                .builder()
                .productId(1L)
                .quantity(3)
                .build();
    }
    @Test
    void getByUserId() throws Exception{
        Long userId = 1L;
        when(cartServiceImplMock.getByUserId(anyLong())).thenReturn(cartItemResponseDtoSet);
        this.mockMvc.perform(get("/cartitems/{userId}", userId)).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$..cartItemId").value(1))
                .andExpect(jsonPath("$..product.productId").value(1));
    }
    @Test
    void insert() throws Exception{
        Long userId = 1L;
        mockMvc.perform(post("cartitems/{userId}", userId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(cartItemRequestDto)))
                .andDo(print())
                .andExpect(status().isOk());
    }
    @Test
    void deleteByPoductId() throws  Exception{
        mockMvc.perform(MockMvcRequestBuilders.delete("/cartitems?userId=1&productId=1"))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
