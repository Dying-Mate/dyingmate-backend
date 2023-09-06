package com.example.dyingmatebackend.message;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/message")
public class MessageController {

    public final MessageService messageService;

    // 부고문자 저장
    @PostMapping("/post")
    public ResponseEntity<?> sendMessage(@RequestBody MessageRequestDto messageRequestDto) {
        if (messageRequestDto != null) {
            messageService.saveMessage(messageRequestDto);
        }

        return ResponseEntity.ok().build();
    }

    // 부고문자 조회
    @GetMapping("/get")
    public ResponseEntity<MessageResponseDto> getMessage(@RequestParam Long messageId) {
        MessageResponseDto messageResponseDto = messageService.getMessage(messageId);

        return ResponseEntity.ok(messageResponseDto);
    }
}
