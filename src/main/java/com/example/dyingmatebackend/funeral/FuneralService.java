package com.example.dyingmatebackend.funeral;

import com.amazonaws.services.s3.AmazonS3Client;
import com.example.dyingmatebackend.s3.S3Uploader;
import com.example.dyingmatebackend.user.User;
import com.example.dyingmatebackend.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.net.URL;

@Slf4j
@RequiredArgsConstructor
@Service
public class FuneralService {

    public final FuneralRepository funeralRepository;
    public final UserRepository userRepository;
    private final S3Uploader s3Uploader;
    private final AmazonS3Client s3Client;

    // 장례방식 저장
    public String saveFuneral(String email, FuneralRequestDto funeralRequestDto) throws IOException {
        User user = userRepository.findByEmail(email).get();

        Funeral funeral = Funeral.builder()
                .method(funeralRequestDto.getMethod())
                .epitaph(funeralRequestDto.getEpitaph())
                .portrait_photo(funeralRequestDto.getPortrait_photo().getOriginalFilename())
                .user(user)
                .build();

        s3Uploader.uploadImage(user.getEmail(), "funeral", funeralRequestDto.getPortrait_photo());

        funeralRepository.save(funeral);
        return "장례준비 저장";
    }

    // 장례방식 조회
    public FuneralResponseDto getFuneral(Long userId) {
        User user = userRepository.findById(userId).get();
        Funeral funeral = funeralRepository.findByUserUserId(userId);

        String path = user.getEmail() + "-funeral-" + funeral.getPortrait_photo();
        URL url = s3Client.getUrl("dying-mate-server.link", path);

        return FuneralResponseDto.toDto(funeral, url.toString());
    }

    // 장례방식 수정
    @Transactional
    public FuneralResponseDto modifyFuneral(Long userId, FuneralRequestDto funeralRequestDto) throws IOException {
        User user = userRepository.findById(userId).get();
        Funeral funeral = funeralRepository.findByUserUserId(userId);

        String deleteImg = user.getEmail() + "-funeral-" + funeral.getPortrait_photo();
        s3Uploader.deleteImage(deleteImg);

        s3Uploader.uploadImage(user.getEmail(), "funeral", funeralRequestDto.getPortrait_photo());
        funeral.updateFuneral(funeralRequestDto);

        String path = user.getEmail() + "-funeral-" + funeral.getPortrait_photo();
        URL url = s3Client.getUrl("dying-mate-server.link", path);
        return FuneralResponseDto.toDto(funeral, url.toString());
    }
}
