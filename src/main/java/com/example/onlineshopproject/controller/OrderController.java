package com.example.onlineshopproject.controller;

import com.example.onlineshopproject.dto.OrderRequestDto;
import com.example.onlineshopproject.dto.OrderResponseDto;
import com.example.onlineshopproject.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@Tag(name = "Order Controller",
        description = "Order for product operations")
@Slf4j
@RestController
@RequestMapping("/v1/orders")
@RequiredArgsConstructor
@Validated
public class OrderController {
    private final OrderService orderService;

    @Operation(summary = "Get order by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Order found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = OrderResponseDto.class))}),
            @ApiResponse(responseCode = "404",
                    description = "Order not found",
                    content = @Content)})
    @GetMapping(value = "/{orderId}")
    @ResponseStatus(HttpStatus.OK)
    public OrderResponseDto getOrderById(@PathVariable @Positive(message = "User id must be a positive" +
            " number") Long orderId) {
        return orderService.getOrderById(orderId);
    }

    @Operation(summary = "Get order history by user id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Order history found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = OrderResponseDto.class))}),
            @ApiResponse(responseCode = "404",
                    description = "Order history not found",
                    content = @Content)})
    @GetMapping(value = "/history/{orderId}")
    @ResponseStatus(HttpStatus.OK)
    public Set<OrderResponseDto> getOrderHistoryByUserId(@PathVariable @Positive(message = "User id must be a " +
            "positive" +
            " number") Long orderId) {
        return orderService.getOrderHistoryByUserId(orderId);
    }

    @Operation(summary = "Insert order")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Order created successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = OrderResponseDto.class))}),
            @ApiResponse(responseCode = "400",
                    description = "Invalid request body",
                    content = @Content),
            @ApiResponse(responseCode = "500",
                    description = "Internal server error")})
    @PostMapping(value = "/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public void insertOrder(@RequestBody @Valid OrderRequestDto orderRequestDto, @PathVariable @Positive(message =
            "User id must be a positive" +
                    " number") Long userId) {
        orderService.insertOrder(orderRequestDto, userId);
    }
}
