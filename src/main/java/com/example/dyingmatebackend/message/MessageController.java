package com.example.dyingmatebackend.message;

import com.example.dyingmatebackend.ApiResponse;
import com.example.dyingmatebackend.jwt.JwtAuthenticationProvider;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/message")
@Tag(name = "Message")
public class MessageController {

    public final MessageService messageService;
    private final JwtAuthenticationProvider jwtAuthenticationProvider;

    @Operation(summary = "부고문자 저장")
    @PostMapping("/send")
    public ApiResponse<?> sendMessage(@RequestBody MessageRequestDto messageRequestDto, Authentication authentication) {
        return ApiResponse.ok(messageService.saveMessage(authentication.getName(), messageRequestDto));
    }

    @Operation(summary = "부고문자 조회")
    @GetMapping("/load")
    public ApiResponse<?> getMessage() {
        Long userId = jwtAuthenticationProvider.getUserId();
        return ApiResponse.ok(messageService.getMessage(userId));
    }

    @Operation(summary = "부고문자 수정")
    @PatchMapping("/modify")
    public ApiResponse<?> modifyMessage(@RequestBody MessageRequestDto messageRequestDto) {
        Long userId = jwtAuthenticationProvider.getUserId();
        return ApiResponse.ok(messageService.modifyMessage(userId, messageRequestDto));
    }

    @Operation(summary = "부고문자 삭제")
    @DeleteMapping("/delete")
    public ApiResponse<?> deleteMessage() {
        Long userId = jwtAuthenticationProvider.getUserId();
        return ApiResponse.ok(messageService.deleteMessage(userId));
    }
}
