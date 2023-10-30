package com.example.dyingmatebackend.user;

import com.example.dyingmatebackend.ApiResponse;
import com.example.dyingmatebackend.jwt.JwtAuthenticationProvider;
import com.example.dyingmatebackend.user.dto.LoginResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final OAuthService oAuthService;
    private final JwtAuthenticationProvider jwtAuthenticationProvider;

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

    // 사용자 이름 저장
    @PostMapping("/{name}/save")
    public ApiResponse<?> saveName(@PathVariable String name) {
        Long userId = jwtAuthenticationProvider.getUserId();
        return ApiResponse.ok(userService.saveName(userId, name));
    }

    // 사용자 이름 수정
    @PatchMapping("/{name}/modify")
    public ApiResponse<?> modifyName(@PathVariable String name) {
        Long userId = jwtAuthenticationProvider.getUserId();
        return ApiResponse.ok(userService.modifyName(userId, name));
    }

    // 카카오 로그인
    @PostMapping("/kakao")
    public ApiResponse<LoginResponse> loginKakao(@RequestParam("code") String authorizationCode) {
        return ApiResponse.ok(oAuthService.loginKakao(authorizationCode));
    }
}
