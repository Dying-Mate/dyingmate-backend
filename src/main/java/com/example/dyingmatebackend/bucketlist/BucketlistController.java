package com.example.dyingmatebackend.bucketlist;

import com.example.dyingmatebackend.ApiResponse;
import com.example.dyingmatebackend.bucketlist.dto.req.ContentRequest;
import com.example.dyingmatebackend.bucketlist.dto.req.FileRequest;
import com.example.dyingmatebackend.bucketlist.dto.req.TitleRequest;
import com.example.dyingmatebackend.jwt.JwtAuthenticationProvider;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RequiredArgsConstructor
@RestController
@RequestMapping("/bucketlist")
@Tag(name = "Bucketlist")
public class BucketlistController {

    private final BucketlistService bucketlistService;
    private final JwtAuthenticationProvider jwtAuthenticationProvider;

    @Operation(summary = "버킷리스트 추가 (form-data)")
    @PostMapping("/add/file")
    public ApiResponse<?> addFileBucketlist(@ModelAttribute FileRequest fileRequest, Authentication authentication) throws IOException {
        return ApiResponse.ok(bucketlistService.addFileMemo(authentication.getName(), fileRequest));
    }

    @Operation(summary = "버킷리스트 추가 (content)")
    @PostMapping("/add/content")
    public ApiResponse<?> addContentBucketlist(@RequestBody ContentRequest contentRequest, Authentication authentication) {
        return ApiResponse.ok(bucketlistService.addContentMemo(authentication.getName(), contentRequest));
    }

    @Operation(summary = "버킷리스트 추가 (title)")
    @PostMapping("/add/title")
    public ApiResponse<?> addTitleBucketlist(@RequestBody TitleRequest titleRequest, Authentication authentication) {
        return ApiResponse.ok(bucketlistService.addTitleMemo(authentication.getName(), titleRequest));
    }

    @Operation(summary = "버킷리스트 조회")
    @GetMapping("/load")
    public ApiResponse<?> getBucketlist() {
        Long userId = jwtAuthenticationProvider.getUserId();
        return ApiResponse.ok(bucketlistService.getMemos(userId));
    }

    @Operation(summary = "버킷리스트 달성 여부 체크")
    @PatchMapping("/complete/{bucketlistId}")
    public ApiResponse<?> bucketlistComplete(@PathVariable Long bucketlistId) {
        return ApiResponse.ok(bucketlistService.checkMemo(bucketlistId));
    }

    @Operation(summary = "버킷리스트 이동")
    @PatchMapping("/move/{bucketlistId}")
    public ApiResponse<?> bucketlistMove(@PathVariable Long bucketlistId,
                                         @RequestParam double x,
                                         @RequestParam double y) {
        return ApiResponse.ok(bucketlistService.moveMemo(bucketlistId, x, y));
    }

    @Operation(summary = "버킷리스트 삭제")
    @DeleteMapping("/{bucketlistId}")
    public ApiResponse<?> deleteBucketlist(@PathVariable Long bucketlistId) {
        return ApiResponse.ok(bucketlistService.deleteMemo(bucketlistId));
    }
}
