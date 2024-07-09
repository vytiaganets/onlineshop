package com.example.onlineshopproject.controller;

import com.example.onlineshopproject.dto.OrderRequestDto;
import com.example.onlineshopproject.dto.OrderResponseDto;
import com.example.onlineshopproject.service.OrderService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Set;


@Slf4j
@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
@Validated
public class OrderController implements OrderControllerInterface {
    private final OrderService orderService;

    @GetMapping(value = "/{orderId}")
    @ResponseStatus(HttpStatus.OK)
    public OrderResponseDto getById(@PathVariable @Positive(message = "User id must be a positive" +
            " number") Long orderId) {
        return orderService.getById(orderId);
    }

    @GetMapping(value = "/history/{orderId}")
    @ResponseStatus(HttpStatus.OK)
    public Set<OrderResponseDto> getHistoryByUserId(@PathVariable @Positive(message = "User id must be a " +
            "positive" +
            " number") Long orderId) {
        return orderService.getHistoryByUserId(orderId);
    }

    @PostMapping(value = "/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public void insert(@RequestBody @Valid OrderRequestDto orderRequestDto, @PathVariable @Positive(message =
            "User id must be a positive" +
                    " number") Long userId) {
        orderService.insert(orderRequestDto, userId);
    }
}
