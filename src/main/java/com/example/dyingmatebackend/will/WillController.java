package com.example.dyingmatebackend.will;

import com.example.dyingmatebackend.ApiResponse;
import com.example.dyingmatebackend.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/will")
public class WillController {

    private final WillService willService;

    // 유언장 저장
    @PostMapping("/write")
    public ApiResponse<?> writeWill(@RequestBody WillRequestDto willRequestDto, Authentication authentication) {
        willService.saveWill(authentication.getName(), willRequestDto);
        return ApiResponse.createSuccessWithNoData("유언장 저장 성공");
    }

    // 유언장 조회
    @GetMapping("/get/{willId}")
    public ApiResponse<?> getWill(@PathVariable Long willId) {
        WillResponseDto willResponse = willService.getWill(willId);

        return ApiResponse.createSuccess("유언장 조회", willResponse);
    }

    // 유언장 수정
    @PatchMapping("/modify/{willId}")
    public ApiResponse<?> modifyWill(@PathVariable Long willId, @RequestBody WillRequestDto willRequestDto) {
        WillResponseDto willResponseDto = willService.modifyWill(willId, willRequestDto);

        return ApiResponse.createSuccess("유언장 수정", willResponseDto);
    }
}
