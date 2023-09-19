package com.example.dyingmatebackend.user;

import com.example.dyingmatebackend.jwt.JwtAuthenticationProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtAuthenticationProvider jwtAuthenticationProvider;

    // 회원가입
    public UserResponseDto join(UserRequestDto userRequestDto) {
        User user = User.builder()
                .email(userRequestDto.getEmail())
                .pwd(passwordEncoder.encode(userRequestDto.getPwd()))
                .build();

        userRepository.save(user);

        UserResponseDto response = UserResponseDto.builder()
                .email(user.getEmail())
                .build();

        return response;
    }

    // 로그인
    public UserResponseDto login(UserRequestDto userRequestDto) {
        String token = jwtAuthenticationProvider.createAccesssToken(userRequestDto);

        UserResponseDto response = UserResponseDto.builder()
                .email(userRequestDto.getEmail())
                .token(token)
                .build();

        return response;
    }
}
