package com.example.dyingmatebackend.will;

import com.example.dyingmatebackend.ApiResponse;
import com.example.dyingmatebackend.jwt.JwtAuthenticationProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/will")
public class WillController {

    private final WillService willService;
    private final JwtAuthenticationProvider jwtAuthenticationProvider;

    // 유언장 저장
    @PostMapping("/write")
    public ApiResponse<?> writeWill(@RequestBody WillRequestDto willRequestDto, Authentication authentication) {
        return ApiResponse.ok(willService.saveWill(authentication.getName(), willRequestDto));
    }

    // 유언장 조회
    @GetMapping("/load")
    public ApiResponse<?> getWill() {
        Long userId = jwtAuthenticationProvider.getUserId();
        return ApiResponse.ok(willService.getWill(userId));
    }

    // 유언장 수정
    @PatchMapping("/modify")
    public ApiResponse<?> modifyWill(@RequestBody WillRequestDto willRequestDto) {
        Long userId = jwtAuthenticationProvider.getUserId();
        return ApiResponse.ok(willService.modifyWill(userId, willRequestDto));
    }
}
