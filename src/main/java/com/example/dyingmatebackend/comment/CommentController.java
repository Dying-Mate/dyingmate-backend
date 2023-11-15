package com.example.dyingmatebackend.comment;

import com.example.dyingmatebackend.ApiResponse;
import com.example.dyingmatebackend.jwt.JwtAuthenticationProvider;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/community")
@Tag(name = "Community")
public class CommentController {

    private final CommentService commentService;
    private final JwtAuthenticationProvider jwtAuthenticationProvider;

    @Operation(summary = "댓글 등록")
    @PostMapping("/register")
    public ApiResponse<?> registerComment(@RequestBody CommentRequestDto commentRequestDto) {
        Long userId = jwtAuthenticationProvider.getUserId();
        return ApiResponse.ok(commentService.registerComment(userId, commentRequestDto));
    }

    @Operation(summary = "댓글 조회")
    @GetMapping("")
    public ApiResponse<?> getComments() {
        return ApiResponse.ok(commentService.getComments());
    }

    @Operation(summary = "좋아요 수 추가")
    @PatchMapping("/like")
    public ApiResponse<?> addLikeNum(@RequestParam Long commentId) {
        Long userId = jwtAuthenticationProvider.getUserId();
        return ApiResponse.ok(commentService.addLikeNum(userId, commentId));
    }
}
