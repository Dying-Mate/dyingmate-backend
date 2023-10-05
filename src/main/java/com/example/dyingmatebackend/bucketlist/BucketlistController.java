package com.example.dyingmatebackend.bucketlist;

import com.example.dyingmatebackend.ApiResponse;
import com.example.dyingmatebackend.jwt.JwtAuthenticationProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/bucketlist")
public class BucketlistController {

    private final BucketlistService bucketlistService;
    private final JwtAuthenticationProvider jwtAuthenticationProvider;

    // 버킷리스트 추가
    @PostMapping("/add")
    public ApiResponse<?> addBucketlist(@ModelAttribute BucketlistRequestDto bucketlistRequestDto, Authentication authentication) {
        return ApiResponse.ok(bucketlistService.addMemo(authentication.getName(), bucketlistRequestDto));
    }

    // 버킷리스트 조회
    @GetMapping("/load")
    public ApiResponse<?> getBucketlist() {
        Long userId = jwtAuthenticationProvider.getUserId();
        return ApiResponse.ok(bucketlistService.getMemos(userId));
    }

    // 버킷리스트 달성 여부 체크
    @PatchMapping("/complete/{bucketlistId}")
    public ApiResponse<?> bucketlistComplete(@PathVariable Long bucketlistId) {
        return ApiResponse.ok(bucketlistService.checkMemo(bucketlistId));
    }

    // 버킷리스트 삭제
    @DeleteMapping("/{bucketlistId}")
    public ApiResponse<?> deleteBucketlist(@PathVariable Long bucketlistId) {
        return ApiResponse.ok(bucketlistService.deleteMemo(bucketlistId));
    }
}
