package com.example.dyingmatebackend.message;

import com.example.dyingmatebackend.user.User;
import com.example.dyingmatebackend.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class MessageService {

    public final MessageRepository messageRepository;
    public final UserRepository userRepository;

    // 부고문자 저장
    public String saveMessage(String email, MessageRequestDto messageRequestDto) {
        User user = userRepository.findByEmail(email).get();

        Message message = Message.builder()
                .message(messageRequestDto.getMessage())
                .user(user)
                .build();

        messageRepository.save(message);
        return "부고문자 저장";
    }

    // 부고문자 조회
    public MessageResponseDto getMessage(Long userId) {
        Message message = messageRepository.findByUserUserId(userId);
        return MessageResponseDto.toDto(message);
    }

    // 부고문자 수정
    @Transactional
    public MessageResponseDto modifyMessage(Long messageId, MessageRequestDto messageRequestDto) {
        Message message = messageRepository.findById(messageId).get();
        message.updateMessage(messageRequestDto.getMessage());
        return MessageResponseDto.toDto(message);
    }
}
