package com.example.dyingmatebackend.user;

import com.example.dyingmatebackend.client.KakaoClient;
import com.example.dyingmatebackend.jwt.JwtAuthenticationProvider;
import com.example.dyingmatebackend.user.dto.LoginResponse;
import com.example.dyingmatebackend.user.params.KakaoInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Random;

@RequiredArgsConstructor
@Service
public class OAuthService {

    private final KakaoClient kakaoClient;
    private final UserRepository userRepository;
    private final JwtAuthenticationProvider jwtAuthenticationProvider;

    public LoginResponse loginKakao(String authorizationCode) {
        String accessToken = kakaoClient.requestAccessToken(authorizationCode);
        KakaoInfoResponse info = kakaoClient.requestKakaoInfo(accessToken);

        Long userId = findOrCreateMember(info);
        User user = userRepository.findById(userId).get();

        LoginResponse response = LoginResponse.builder()
                .id(userId)
                .name(user.getName())
                .email(user.getEmail())
                .photoNum(user.getPhotoNum())
                .accessToken(jwtAuthenticationProvider.createAccessToken(userId, info.getProperties().getNickname()))
                .refreshToken(jwtAuthenticationProvider.createRefreshToken(userId, info.getProperties().getNickname()))
                .build();

        return response;
    }

    private Long findOrCreateMember(KakaoInfoResponse info) {
        return userRepository.findByEmail(info.getKakao_account().getEmail())
                .map(User::getUserId)
                .orElseGet(() -> newMember(info));
    }

    private Long newMember(KakaoInfoResponse info) {
        Random random = new Random();

        User user = User.builder()
                .userId(info.getId())
                .email(info.getKakao_account().getEmail())
                .name(info.getProperties().getNickname())
                .photoNum(random.nextInt(3))
                .build();

        userRepository.save(user);

        return user.getUserId();
    }

}
