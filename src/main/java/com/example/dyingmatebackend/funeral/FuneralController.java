package com.example.dyingmatebackend.funeral;

import com.example.dyingmatebackend.ApiResponse;
import com.example.dyingmatebackend.jwt.JwtAuthenticationProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/funeral")
public class FuneralController {

    public final FuneralService funeralService;
    public final JwtAuthenticationProvider jwtAuthenticationProvider;

    // 장례방식 저장
    @PostMapping("/save")
    public ApiResponse<?> saveFuneral(@ModelAttribute FuneralRequestDto funeralRequestDto, Authentication authentication) throws IOException {
        return ApiResponse.ok(funeralService.saveFuneral(authentication.getName(), funeralRequestDto));
    }

    // 장례방식 조회
    @GetMapping("/select")
    public ApiResponse<?> getFuneral() {
        Long userId = jwtAuthenticationProvider.getUserId();
        return ApiResponse.ok(funeralService.getFuneral(userId));
    }

    // 장례방식 수정
    @PatchMapping("/modify")
    public ApiResponse<?> modifyFuneral(@ModelAttribute FuneralRequestDto funeralRequestDto) {
        Long userId = jwtAuthenticationProvider.getUserId();
        return ApiResponse.ok(funeralService.modifyFuneral(userId, funeralRequestDto));
    }
}
