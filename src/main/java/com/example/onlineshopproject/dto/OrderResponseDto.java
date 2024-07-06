package com.example.onlineshopproject.dto;

import com.example.onlineshopproject.enums.Status;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.security.Timestamp;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderResponseDto {
    private Long orderId;
    private Timestamp createdAt;
    private String deliveryAddress;
    private String contactPhone;
    private String deliveryMethod;
    private Status status;
    private Timestamp updatedAt;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("userEntity")
    private UserResponseDto userResponseDto;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("itemEntity")
    private Set<OrderItemResponseDto> orderItemResponseDtoSet;
}
