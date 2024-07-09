package com.example.onlineshopproject.controller;

import com.example.onlineshopproject.dto.OrderRequestDto;
import com.example.onlineshopproject.dto.OrderResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Set;

@Tag(name = "Order Controller",
        description = "Order for product operations")
public interface OrderControllerInterface {
    @Operation(summary = "Get order by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Order found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = OrderResponseDto.class))}),
            @ApiResponse(responseCode = "404",
                    description = "Order not found",
                    content = @Content)})
    public OrderResponseDto getById(@PathVariable @Positive(message = "User id must be a positive" +
            " number") Long orderId);

    @Operation(summary = "Get order history by user id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Order history found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = OrderResponseDto.class))}),
            @ApiResponse(responseCode = "401",
                    description = "Unauthorized",
                    content = @Content)})
    public Set<OrderResponseDto> getHistoryByUserId(@PathVariable @Positive(message = "User id must be a " +
            "positive" +
            " number") Long orderId);

    @Operation(summary = "Insert order")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Order created successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = OrderResponseDto.class))}),
            @ApiResponse(responseCode = "400",
                    description = "Invalid request body",
                    content = @Content)})
    public void insert(@RequestBody @Valid OrderRequestDto orderRequestDto, @PathVariable @Positive(message =
            "User id must be a positive" +
                    " number") Long userId);
}
