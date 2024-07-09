package com.example.onlineshopproject.service;

import com.example.onlineshopproject.dto.OrderRequestDto;
import com.example.onlineshopproject.dto.OrderResponseDto;

import java.util.Set;

public interface OrderService {
    public void insert(OrderRequestDto orderRequestDto, Long userId);
    public OrderResponseDto getById(Long orderId);
    public Set<OrderResponseDto> getHistoryByUserId(Long userId);
}
