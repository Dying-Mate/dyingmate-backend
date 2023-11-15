package com.example.dyingmatebackend.map;

import com.example.dyingmatebackend.ApiResponse;
import com.example.dyingmatebackend.jwt.JwtAuthenticationProvider;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/map")
@Tag(name = "Map")
public class MapController {

    private final MapService mapService;
    private final JwtAuthenticationProvider jwtAuthenticationProvider;

    @Operation(summary = "지도 잠금 open")
    @PatchMapping("/open/{stage}")
    public ApiResponse<?> openStage(@PathVariable int stage) {
        Long userId = jwtAuthenticationProvider.getUserId();
        return ApiResponse.ok(mapService.openStage(userId, stage));
    }

    @Operation(summary = "지도 stage 조회")
    @GetMapping("")
    public ApiResponse<?> getMap() {
        Long userId = jwtAuthenticationProvider.getUserId();
        return ApiResponse.ok(mapService.getMap(userId));
    }
}
