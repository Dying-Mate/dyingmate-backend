package com.example.dyingmatebackend.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class MessageResponseDto {

    String message;

    public static MessageResponseDto toDto(Message message) {
        return MessageResponseDto.builder()
                .message(message.getMessage())
                .build();
    }
}
