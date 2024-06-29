package com.example.onlineshopproject.service;

import com.example.onlineshopproject.dto.OrderDto;
import com.example.onlineshopproject.entity.OrderEntity;
import com.example.onlineshopproject.enums.Status;
import com.example.onlineshopproject.mapper.Mappers;
import com.example.onlineshopproject.repository.OrderItemRepository;
import com.example.onlineshopproject.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final Mappers mappers;

    public OrderDto insertOrder(OrderDto orderDto) {
        if (orderDto.getItems().isEmpty()) {
            //...
        }
        OrderEntity orderEntity = mappers.convertToOrderEntity(orderDto);
        orderEntity.setStatus(Status.ORDERED);
        orderEntity.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        orderEntity.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
        orderEntity.getItems().stream().forEach(e -> orderItemRepository.save(e));
        return mappers.convertToOrderDto(orderRepository.save(orderEntity));
    }

    public Status getOrderStatusById(long id) {
        Optional<OrderEntity> orderEntityOptional = orderRepository.findById(id);
        if (orderEntityOptional.isPresent()) {
            return orderEntityOptional.get().getStatus();
        } else {
            return null;
            // ...
        }
    }
}
