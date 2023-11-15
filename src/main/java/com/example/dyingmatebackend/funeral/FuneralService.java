package com.example.dyingmatebackend.funeral;

import com.example.dyingmatebackend.s3.S3Uploader;
import com.example.dyingmatebackend.user.User;
import com.example.dyingmatebackend.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Service
public class FuneralService {

    public final FuneralRepository funeralRepository;
    public final UserRepository userRepository;
    private final S3Uploader s3Uploader;

    // 장례방식 저장
    public String saveFuneral(String email, FuneralRequestDto funeralRequestDto) throws IOException {
        User user = userRepository.findByEmail(email).get();

        Funeral funeral = Funeral.builder()
                .method(funeralRequestDto.getMethod())
                .epitaph(funeralRequestDto.getEpitaph())
                .portrait_photo(funeralRequestDto.getPortrait_photo().getOriginalFilename())
                .user(user)
                .build();

        s3Uploader.uploadImage(funeralRequestDto.getPortrait_photo());

        funeralRepository.save(funeral);
        return "장례준비 저장";
    }

    // 장례방식 조회
    public FuneralResponseDto getFuneral(Long userId) {
        Funeral funeral = funeralRepository.findByUserUserId(userId);
        return FuneralResponseDto.toDto(funeral);
    }

    // 장례방식 수정
    @Transactional
    public FuneralResponseDto modifyFuneral(Long userId, FuneralRequestDto funeralRequestDto) {
        Funeral funeral = funeralRepository.findByUserUserId(userId);
        funeral.updateFuneral(funeralRequestDto);
        return FuneralResponseDto.toDto(funeral);
    }
}
