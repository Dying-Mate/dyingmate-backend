package com.example.dyingmatebackend.user;

import com.example.dyingmatebackend.exception.ApplicatonException;
import com.example.dyingmatebackend.exception.ErrorCode;
import com.example.dyingmatebackend.jwt.JwtAuthenticationProvider;
import com.example.dyingmatebackend.map.Map;
import com.example.dyingmatebackend.map.MapRepository;
import com.example.dyingmatebackend.user.dto.LoginResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final MapRepository mapRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtAuthenticationProvider jwtAuthenticationProvider;

    // 회원가입
    public UserResponseDto join(UserRequestDto userRequestDto) {
        Random random = new Random();

        if (!userRepository.existsByEmail(userRequestDto.getEmail())) {
            User user = User.builder()
                    .email(userRequestDto.getEmail())
                    .pwd(passwordEncoder.encode(userRequestDto.getPwd()))
                    .photoNum(random.nextInt(3))
                    .build();

            userRepository.save(user);
            mapRepository.save(Map.builder().user(user).build());

            return new UserResponseDto(user.getUserId(), user.getEmail(), null);
        } else {
            throw new ApplicatonException(ErrorCode.DUPLICATE_EMAIL);
        }
    }

    // 회원가입 중복 여부
    public boolean checkEmailDuplicate(String email) {
        return userRepository.existsByEmail(email);
    }

    // 로그인
    public LoginResponse login(UserRequestDto userRequestDto) {
        User user = userRepository.findByEmail(userRequestDto.getEmail()).orElseThrow(() ->
                new ApplicatonException(ErrorCode.USER_NOT_FOUND)); // 유저가 존재하지 않을 때

        if (passwordEncoder.matches(userRequestDto.getPwd(), user.getPwd())) { // 비밀번호가 일치할 때
            String accessToken = jwtAuthenticationProvider.createAccessToken(user.getUserId(), user.getName());
            String refreshToken = jwtAuthenticationProvider.createRefreshToken(user.getUserId(), user.getName());

            LoginResponse response = LoginResponse.builder()
                    .id(user.getUserId())
                    .name(user.getName())
                    .email(user.getEmail())
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .build();

            return response;
        } else {
            throw new ApplicatonException(ErrorCode.INCORRECT_PASSWORD);
        }
    }

    // 사용자 이름 저장
    @Transactional
    public String saveName(Long userId, String name) {
        User user = userRepository.findById(userId).get();
        user.setName(name);
        return user.getName();
    }
}
