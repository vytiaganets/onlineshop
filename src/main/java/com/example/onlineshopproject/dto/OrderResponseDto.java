package com.example.onlineshopproject.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderResponseDto {
    private long orderId;
    private String deliveryAddress;
    private String deliveryMethod;
    private List<OrderItemResponseDto> items = new ArrayList<>();
}
