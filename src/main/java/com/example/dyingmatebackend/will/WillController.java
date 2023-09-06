package com.example.dyingmatebackend.will;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/will")
public class WillController {

    private final WillService willService;

    // 유언장 저장
    @PostMapping("/post")
    public ResponseEntity<?> writeWill(@RequestBody WillRequestDto willRequestDto) {
        if (willRequestDto != null) {
            willService.saveWill(willRequestDto);
        }

        return ResponseEntity.ok().build();
    }

    // 유언장 조회
    @GetMapping("/get")
    public ResponseEntity<WillResponseDto> getWill(@RequestParam Long willId) {
        WillResponseDto willResponse = willService.getWill(willId);

        return ResponseEntity.ok(willResponse);
    }
}
