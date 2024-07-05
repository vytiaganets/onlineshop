package com.example.onlineshopproject.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FavoriteResponseDto {
    private long favoriteId;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("User")
    private UserResponseDto userResponseDto;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("Product")
    private ProductResponseDto productResponseDto;
}
