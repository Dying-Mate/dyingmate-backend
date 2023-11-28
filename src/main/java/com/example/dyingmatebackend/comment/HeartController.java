package com.example.dyingmatebackend.comment;

import com.example.dyingmatebackend.ApiResponse;
import com.example.dyingmatebackend.jwt.JwtAuthenticationProvider;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/heart")
public class HeartController {

    private final HeartService heartService;
    private final JwtAuthenticationProvider jwtAuthenticationProvider;

    @Operation(summary = "댓글 좋아요 수 추가")
    @PostMapping("/{commentId}")
    public ApiResponse<?> addHeartCount(@PathVariable Long commentId) {
        Long userId = jwtAuthenticationProvider.getUserId();
        return ApiResponse.ok(heartService.addHeartCount(userId, commentId));
    }

    @Operation(summary = "댓글 좋아요 취소")
    @DeleteMapping("/{commentId}")
    public ApiResponse<?> cancelHeart(@PathVariable Long commentId) {
        Long userId = jwtAuthenticationProvider.getUserId();
        return ApiResponse.ok(heartService.cancelHeart(userId, commentId));
    }
}
