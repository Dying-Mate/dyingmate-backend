package com.example.dyingmatebackend.will;

import com.example.dyingmatebackend.user.User;
import com.example.dyingmatebackend.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class WillService {

    public final WillRepository willRepository;
    public final UserRepository userRepository;

    // 유언장 저장
    public String saveWill(String email, WillRequestDto willRequestDto) {
        User user = userRepository.findByEmail(email).get();

        Will will = Will.builder()
                .content(willRequestDto.getContent())
                .user(user)
                .build();

        willRepository.save(will);
        return "유언장 저장";
    }

    // 유언장 조회
    public WillResponseDto getWill(Long userId) {
        Will will = willRepository.findByUserUserId(userId);
        return WillResponseDto.toDto(will);
    }

    // 유언장 수정
    @Transactional
    public WillResponseDto modifyWill(Long willId, WillRequestDto willRequestDto) {
        Will will = willRepository.findById(willId).get();
        will.updateContent(willRequestDto.getContent());
        return WillResponseDto.toDto(will);
    }
}
