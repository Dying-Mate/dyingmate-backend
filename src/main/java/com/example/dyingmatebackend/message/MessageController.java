package com.example.dyingmatebackend.message;

import com.example.dyingmatebackend.ApiResponse;
import com.example.dyingmatebackend.jwt.JwtAuthenticationProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/message")
public class MessageController {

    public final MessageService messageService;
    private final JwtAuthenticationProvider jwtAuthenticationProvider;

    // 부고문자 저장
    @PostMapping("/send")
    public ApiResponse<?> sendMessage(@RequestBody MessageRequestDto messageRequestDto, Authentication authentication) {
        return ApiResponse.ok(messageService.saveMessage(authentication.getName(), messageRequestDto));
    }

    // 부고문자 조회
    @GetMapping("/load")
    public ApiResponse<?> getMessage() {
        Long userId = jwtAuthenticationProvider.getUserId();
        return ApiResponse.ok(messageService.getMessage(userId));
    }

    // 부고문자 수정
    @PatchMapping("/modify/{messageId}")
    public ApiResponse<?> modifyMessage(@PathVariable Long messageId, @RequestBody MessageRequestDto messageRequestDto) {
        MessageResponseDto messageResponseDto = messageService.modifyMessage(messageId, messageRequestDto);

        return ApiResponse.createSuccess("부고문자 수정", messageResponseDto);
    }
}
