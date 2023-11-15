package com.example.dyingmatebackend.ending;

import com.example.dyingmatebackend.ApiResponse;
import com.example.dyingmatebackend.jwt.JwtAuthenticationProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/ending")
public class EndingController {

    private final EndingService endingService;
    private final JwtAuthenticationProvider jwtAuthenticationProvider;

    @GetMapping("")
    public ApiResponse<?> isAllDone() {
        Long userId = jwtAuthenticationProvider.getUserId();
        return ApiResponse.ok(endingService.isAllDone(userId));
    }
}
