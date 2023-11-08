package com.example.dyingmatebackend.friendroom;

import com.example.dyingmatebackend.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/friendroom")
public class FriendRoomController {

    private final FriendRoomServcie friendRoomServcie;

    // 친구 기록 한번에 반환
    @GetMapping("/{email}")
    public ApiResponse<?> getData(@PathVariable String email) {
        return ApiResponse.ok(friendRoomServcie.getData(email));
    }

    // 친구 유언장
    @GetMapping("/{email}/will")
    public ApiResponse<?> getWill(@PathVariable String email) {
        return ApiResponse.ok(friendRoomServcie.getWill(email));
    }

    // 친구 부고문자
    @GetMapping("/{email}/message")
    public ApiResponse<?> getMessage(@PathVariable String email) {
        return ApiResponse.ok(friendRoomServcie.getMessage(email));
    }

    // 친구 장례방식
    @GetMapping("{email}/funeral")
    public ApiResponse<?> getFuneral(@PathVariable String email) {
        return ApiResponse.ok(friendRoomServcie.getFuneral(email));
    }

    // 친구 버킷리스트
    @GetMapping("{email}/bucketlist")
    public ApiResponse<?> getBucketlist(@PathVariable String email) {
        return ApiResponse.ok(friendRoomServcie.getBucketlist(email));
    }
}
