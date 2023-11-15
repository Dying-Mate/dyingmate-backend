package com.example.dyingmatebackend.ending;

import com.example.dyingmatebackend.ApiResponse;
import com.example.dyingmatebackend.jwt.JwtAuthenticationProvider;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/ending")
@Tag(name = "Ending")
public class EndingController {

    private final EndingService endingService;
    private final JwtAuthenticationProvider jwtAuthenticationProvider;

    @Operation(summary = "모든 기능 수행 여부 반환")
    @GetMapping("")
    public ApiResponse<?> isAllDone() {
        Long userId = jwtAuthenticationProvider.getUserId();
        return ApiResponse.ok(endingService.isAllDone(userId));
    }
}
