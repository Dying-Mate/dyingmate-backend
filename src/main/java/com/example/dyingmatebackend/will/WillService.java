package com.example.dyingmatebackend.will;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class WillService {

    public final WillRepository willRepository;

    // 유언장 저장
    public Will saveWill(WillRequestDto willRequestDto) {
        Will will = Will.builder()
                .content(willRequestDto.getContent())
                .build();

        return willRepository.save(will);
    }

    // 유언장 조회
    public WillResponseDto getWill(Long willId) {
        WillResponseDto willResponseDto = WillResponseDto.toDto(willRepository.findById(willId).get());

        return willResponseDto;
    }
}
