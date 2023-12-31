package com.example.dyingmatebackend.friend;

import com.example.dyingmatebackend.ApiResponse;
import com.example.dyingmatebackend.exception.ApplicatonException;
import com.example.dyingmatebackend.friend.dto.res.FriendSearch;
import com.example.dyingmatebackend.jwt.JwtAuthenticationProvider;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/friend")
@Tag(name = "Friend")
public class FriendController {

    private final FriendService friendService;
    private final JwtAuthenticationProvider jwtAuthenticationProvider;

    @Operation(summary = "친구 검색")
    @GetMapping("/search")
    public ApiResponse<?> searchFriend() {
        Long userId = jwtAuthenticationProvider.getUserId();
        return ApiResponse.ok(friendService.searchFriend(userId)); // 모든 유저 반환
    }

    @Operation(summary = "친구 요청")
    @PostMapping("/add")
    public ApiResponse<?> requestFriend(@RequestBody FriendSearch friendSearch) {
        Long userId = jwtAuthenticationProvider.getUserId();
        String friendEmail = friendSearch.getFriendEmail();
        return ApiResponse.ok(friendService.requestFriend(userId, friendEmail));
    }

    @Operation(summary = "친구 맺은 목록 & 친구 요청 받은 목록 조회")
    @GetMapping("/list")
    public ApiResponse<?> getFriendAllList() {
        Long userId = jwtAuthenticationProvider.getUserId();
        return ApiResponse.ok(friendService.getFriendAllList(userId));
    }

    @Operation(summary = "친구 수락")
    @PostMapping("/accept")
    public ApiResponse<?> acceptFriend(@RequestParam String acceptEmail) {
        Long userId = jwtAuthenticationProvider.getUserId();
        return ApiResponse.ok(friendService.acceptFriend(userId, acceptEmail));
    }

    @Operation(summary = "친구 거절")
    @DeleteMapping("/refuse")
    public ApiResponse<?> refuseFriend(@RequestParam String refuseEmail) {
        Long userId = jwtAuthenticationProvider.getUserId();
        return ApiResponse.ok(friendService.refuseFriend(refuseEmail, userId));
    }
}
