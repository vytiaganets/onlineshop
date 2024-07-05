package com.example.onlineshopproject.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HistoryDto {
    private Long id;
    private Long userId;
    private Long orderId;
}
