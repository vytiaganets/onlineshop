package com.example.onlineshopproject.controller;

import com.example.onlineshopproject.dto.*;
import com.example.onlineshopproject.enums.DeliveryMethod;
import com.example.onlineshopproject.enums.Status;
import com.example.onlineshopproject.enums.UserRole;
import com.example.onlineshopproject.service.OrderServiceImpl;
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

@WebMvcTest(OrderController.class)
public class OrderControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private OrderServiceImpl orderServiceImplMock;

    private UserResponseDto userResponseDto;
    private OrderResponseDto orderResponseDto;
    private OrderRequestDto orderRequestDto;
    private OrderItemResponseDto orderItemResponseDto;
    private OrderItemRequestDto orderItemRequestDto;
    private ProductResponseDto productResponseDto;
    private Set<OrderItemResponseDto> orderItemResponseDtoSet = new HashSet<>();
    private Set<OrderItemRequestDto> orderItemRequestDtoSet = new HashSet<>();

    @BeforeEach
    void setUp() {
        userResponseDto = UserResponseDto
                .builder()
                .userId(1L)
                .name("Andreas Schwarzberg")
                .email("schwarzberd@web.de")
                .phone("123456789012")
                .password("1234")
                .userRole(UserRole.USER)
                .build();
        orderResponseDto = OrderResponseDto
                .builder()
                .orderId(1L)
                .createdAt(Timestamp.valueOf(LocalDateTime.now()))
                .deliveryAddress("Berlin")
                .contactPhone("123456789012")
                .deliveryMethod(DeliveryMethod.COURIER_DELIVERY)
                .status(Status.PAID)
                .updatedAt(Timestamp.valueOf(LocalDateTime.now()))
                .orderItemResponseDtoSet(null)
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
        orderItemResponseDto = OrderItemResponseDto
                .builder()
                .orderItemId(1L)
                .orderResponseDto(null)
                .productResponseDto(productResponseDto)
                .quantity(3)
                .build();
        orderItemResponseDtoSet.add(orderItemResponseDto);
        orderResponseDto.setOrderItemResponseDtoSet(orderItemResponseDtoSet);

        orderItemRequestDto = OrderItemRequestDto
                .builder()
                .productId(1L)
                .quantity(3)
                .build();
        orderItemRequestDtoSet.add(orderItemRequestDto);
        orderRequestDto = OrderRequestDto
                .builder()
                .orderItemSet(orderItemRequestDtoSet)
                .deliveryAddress("Berlin")
                .deliveryMethod("COURIER_DELIVERY")
                .build();
    }

    @Test
    void getOrderById() throws Exception {
        Long orderId = 1L;
        when(orderServiceImplMock.getById(anyLong())).thenReturn(orderResponseDto);
        this.mockMvc.perform(get("/orders/{orderId}", orderId)).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orderId").value(orderId));
    }

    @Test
    void getHistoryByUserId() throws Exception {
        Long userId = 1L;
        Set<OrderResponseDto> orderResponseDtoSet = new HashSet<>();
        orderResponseDtoSet.add(orderResponseDto);
        when(orderServiceImplMock.getHistoryByUserId(anyLong())).thenReturn(orderResponseDtoSet);
        this.mockMvc.perform(get("/orders/history/{orderId}", userId)).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.contactPhone").value(orderResponseDto.getContactPhone()));
    }

    @Test
    void delete() throws Exception {
        Long userId = 1L;
        mockMvc.perform(post("orders/{userId}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderItemRequestDto)))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
