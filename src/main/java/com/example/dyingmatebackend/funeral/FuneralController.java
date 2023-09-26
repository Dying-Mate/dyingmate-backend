package com.example.dyingmatebackend.funeral;

import com.example.dyingmatebackend.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/funeral")
public class FuneralController {

    public final FuneralService funeralService;

    // 장례방식 저장
    @PostMapping("/save")
    public ApiResponse<?> saveFuneral(@RequestBody FuneralRequestDto funeralRequestDto, Authentication authentication) {
        funeralService.saveFuneral(authentication.getName(), funeralRequestDto);
        return ApiResponse.createSuccessWithNoData("장례방식 저장 성공");
    }

    // 장례방식 조회
    @GetMapping("/select/{funeralId}")
    public ApiResponse<?> getFuneral(@PathVariable Long funeralId) {
        FuneralResponseDto funeralResponseDto = funeralService.getFuneral(funeralId);

        return ApiResponse.createSuccess("장례방식 조회", funeralResponseDto);
    }

    // 장례방식 수정
    @PatchMapping("/modify/{funeralId}")
    public ApiResponse<?> modifyFuneral(@PathVariable Long funeralId, @RequestBody FuneralRequestDto funeralRequestDto) {
        FuneralResponseDto funeralResponseDto = funeralService.patchFuneral(funeralId, funeralRequestDto);

        return ApiResponse.createSuccess("장례방식 수정", funeralResponseDto);
    }
}
