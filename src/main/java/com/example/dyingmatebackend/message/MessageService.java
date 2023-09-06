package com.example.dyingmatebackend.message;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MessageService {

    final public MessageRepository messageRepository;

    // 부고문자 저장
    public Message saveMessage(MessageRequestDto messageRequestDto) {
        Message message = Message.builder()
                .message(messageRequestDto.getMessage())
                .build();

        return messageRepository.save(message);
    }

    // 부고문자 조회
    public MessageResponseDto getMessage(Long messageId) {
        Message message = messageRepository.findById(messageId).get();

        MessageResponseDto messageResponseDto = MessageResponseDto.toDto(message);

        return messageResponseDto;
    }
}
