package com.example.dyingmatebackend.user;

import com.example.dyingmatebackend.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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

    // 회원가입 중복 여부
    @GetMapping("/email/exists/{email}")
    public boolean checkEmailDuplicate(@PathVariable String email) {
        return userService.checkEmailDuplicate(email); // true - 이미 존재하는 이메일
    }

    // 로그인 (토큰 발급)
    @PostMapping("/login")
    public ApiResponse<?> login(@RequestBody UserRequestDto userRequestDto) {
        return ApiResponse.ok(userService.login(userRequestDto));
    }
}
