package com.example.onlineshopproject.controller;

import com.example.onlineshopproject.dto.OrderDto;
import com.example.onlineshopproject.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import com.example.onlineshopproject.enums.Status;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    @PostMapping
    public ResponseEntity<OrderDto> insertOrder(@RequestBody @Valid OrderDto orderDto){
        OrderDto orderDtoResponce = orderService.insertOrder(orderDto);
        return new ResponseEntity<>(orderDtoResponce, HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Status> getOrderStatusById(@PathVariable long id) {
        Status orderStatusById = orderService.getOrderStatusById(id);
        return new ResponseEntity<>(orderStatusById, HttpStatus.OK);

    }
}
