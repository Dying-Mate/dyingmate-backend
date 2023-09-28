package com.example.dyingmatebackend.user;

import com.example.dyingmatebackend.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    // 회원가입
    @PostMapping("/join")
    public ApiResponse<?> join(@RequestBody UserRequestDto userRequestDto) {
        return ApiResponse.ok(userService.join(userRequestDto));
    }

    // 로그인 (토큰 발급)
    @PostMapping("/login")
    public ApiResponse<?> login(@RequestBody UserRequestDto userRequestDto) {
        return ApiResponse.ok(userService.login(userRequestDto));
    }
}
