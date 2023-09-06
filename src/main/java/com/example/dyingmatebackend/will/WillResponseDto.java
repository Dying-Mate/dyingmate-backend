package com.example.dyingmatebackend.will;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class WillResponseDto {

    String content;

    public static WillResponseDto toDto(Will will) {
        return WillResponseDto.builder()
                .content(will.getContent())
                .build();
    }
}
