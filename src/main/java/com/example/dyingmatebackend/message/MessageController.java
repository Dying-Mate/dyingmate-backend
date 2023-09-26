package com.example.dyingmatebackend.message;

import com.example.dyingmatebackend.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/message")
public class MessageController {

    public final MessageService messageService;

    // 부고문자 저장
    @PostMapping("/send")
    public ApiResponse<?> sendMessage(@RequestBody MessageRequestDto messageRequestDto, Authentication authentication) {
        messageService.saveMessage(authentication.getName(), messageRequestDto);
        return ApiResponse.createSuccessWithNoData("부고문자 저장 성공");
    }

    // 부고문자 조회
    @GetMapping("/get/{messageId}")
    public ApiResponse<?> getMessage(@PathVariable Long messageId) {
        MessageResponseDto messageResponseDto = messageService.getMessage(messageId);

        return ApiResponse.createSuccess("부고문자 조회", messageResponseDto);
    }

    // 부고문자 수정
    @PatchMapping("/modify/{messageId}")
    public ApiResponse<?> modifyMessage(@PathVariable Long messageId, @RequestBody MessageRequestDto messageRequestDto) {
        MessageResponseDto messageResponseDto = messageService.modifyMessage(messageId, messageRequestDto);

        return ApiResponse.createSuccess("부고문자 수정", messageResponseDto);
    }
}
