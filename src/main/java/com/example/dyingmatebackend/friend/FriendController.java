package com.example.dyingmatebackend.friend;

import com.example.dyingmatebackend.ApiResponse;
import com.example.dyingmatebackend.exception.ApplicatonException;
import com.example.dyingmatebackend.friend.dto.res.FriendSearch;
import com.example.dyingmatebackend.jwt.JwtAuthenticationProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/friend")
public class FriendController {

    private final FriendService friendService;
    private final JwtAuthenticationProvider jwtAuthenticationProvider;

    // 친구 검색
    @GetMapping("/search")
    public ApiResponse<?> searchFriend() {
        Long userId = jwtAuthenticationProvider.getUserId();
        return ApiResponse.ok(friendService.searchFriend(userId)); // 모든 유저 반환
    }

    // 친구 요청
    @PostMapping("/add")
    public ApiResponse<?> requestFriend(@RequestBody FriendSearch friendSearch) {
        Long userId = jwtAuthenticationProvider.getUserId();
        String friendEmail = friendSearch.getFriendEmail();
        return ApiResponse.ok(friendService.requestFriend(userId, friendEmail));
    }

    // 친구 맺은 목록 & 친구 요청 받은 목록 조회
    @GetMapping("/list")
    public ApiResponse<?> getFriendAllList() {
        Long userId = jwtAuthenticationProvider.getUserId();
        return ApiResponse.ok(friendService.getFriendAllList(userId));
    }

    // 친구 수락
    @PostMapping("/accept")
    public ApiResponse<?> acceptFriend(@RequestParam String acceptEmail) {
        Long userId = jwtAuthenticationProvider.getUserId();
        return ApiResponse.ok(friendService.acceptFriend(userId, acceptEmail));
    }

    // 친구 취소
    @DeleteMapping("/refuse")
    public ApiResponse<?> refuseFriend(@RequestParam String refuseEmail) {
        return ApiResponse.ok(friendService.refuseFriend(refuseEmail));
    }
}
