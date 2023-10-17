package com.example.dyingmatebackend.map;

import com.example.dyingmatebackend.ApiResponse;
import com.example.dyingmatebackend.jwt.JwtAuthenticationProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/map")
public class MapController {

    private final MapService mapService;
    private final JwtAuthenticationProvider jwtAuthenticationProvider;

    // 지도 잠금 오픈
    @PatchMapping("/open/{stage}")
    public ApiResponse<?> openStage(@PathVariable int stage) {
        Long userId = jwtAuthenticationProvider.getUserId();
        return ApiResponse.ok(mapService.openStage(userId, stage));
    }

     // 지도 stage 조회
    @GetMapping("")
    public ApiResponse<?> getMap() {
        Long userId = jwtAuthenticationProvider.getUserId();
        return ApiResponse.ok(mapService.getMap(userId));
    }
}
