package com.example.dyingmatebackend.funeral;

import com.example.dyingmatebackend.user.User;
import com.example.dyingmatebackend.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class FuneralService {

    public final FuneralRepository funeralRepository;
    public final UserRepository userRepository;

    // 장례방식 저장
    public void saveFuneral(String email, FuneralRequestDto funeralRequestDto) {
        User user = userRepository.findByEmail(email).get();
        if (funeralRequestDto != null) {
            Funeral funeral = Funeral.builder()
                    .method(funeralRequestDto.getMethod())
                    .epitaph(funeralRequestDto.getEpitaph())
                    .portrait_photo(funeralRequestDto.getPortrait_photo())
                    .user(user)
                    .build();

            funeralRepository.save(funeral);
        }
    }

    // 장례방식 조회
    public FuneralResponseDto getFuneral(Long funeralId) {
        Funeral funeral = funeralRepository.findById(funeralId).get();

        return FuneralResponseDto.toDto(funeral);
    }

    // 장례방식 수정
    @Transactional
    public FuneralResponseDto patchFuneral(Long funeralId, FuneralRequestDto funeralRequestDto) {
        Funeral funeral = funeralRepository.findById(funeralId).get();
        funeral.updateFuneral(funeralRequestDto);
        return FuneralResponseDto.toDto(funeral);
    }
}
