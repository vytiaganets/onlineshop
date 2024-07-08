package com.example.onlineshopproject.service;

import com.example.onlineshopproject.dto.OrderRequestDto;
import com.example.onlineshopproject.dto.OrderResponseDto;

import java.util.Set;

public interface OrderService {
    void insert(OrderRequestDto orderRequestDto, Long userId);
    OrderResponseDto getById(Long orderId);
    Set<OrderResponseDto> getHistoryByUserId(Long userId);
}
