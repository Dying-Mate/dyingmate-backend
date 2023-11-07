package com.example.dyingmatebackend.comment;

import com.example.dyingmatebackend.ApiResponse;
import com.example.dyingmatebackend.jwt.JwtAuthenticationProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/community")
public class CommentController {

    private final CommentService commentService;
    private final JwtAuthenticationProvider jwtAuthenticationProvider;

    // 댓글 등록
    @PostMapping("/register")
    public ApiResponse<?> registerComment(@RequestBody CommentRequestDto commentRequestDto) {
        Long userId = jwtAuthenticationProvider.getUserId();
        return ApiResponse.ok(commentService.registerComment(userId, commentRequestDto));
    }

    // 댓글 조회
    @GetMapping("")
    public ApiResponse<?> getComments() {
        return ApiResponse.ok(commentService.getComments());
    }
}