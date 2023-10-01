package com.example.dyingmatebackend.user;

import com.example.dyingmatebackend.exception.ApplicatonException;
import com.example.dyingmatebackend.exception.ErrorCode;
import com.example.dyingmatebackend.jwt.JwtAuthenticationProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

        return new UserResponseDto(user.getUserId(), user.getEmail(), null);
    }

    // 회원가입 중복 여부
    public boolean checkEmailDuplicate(String email) {
        return userRepository.existsByEmail(email);
    }

    // 로그인
    public UserResponseDto login(UserRequestDto userRequestDto) {
        User user = userRepository.findByEmail(userRequestDto.getEmail()).orElseThrow(() ->
                new ApplicatonException(ErrorCode.USER_NOT_FOUND)); // 유저가 존재하지 않을 때

        if (passwordEncoder.matches(userRequestDto.getPwd(), user.getPwd())) { // 비밀번호가 일치할 때
            String token = jwtAuthenticationProvider.createAccesssToken(userRequestDto);

            UserResponseDto response = UserResponseDto.builder()
                    .email(userRequestDto.getEmail())
                    .token(token)
                    .build();

            return response;
        } else {
            throw new ApplicatonException(ErrorCode.INCORRECT_PASSWORD);
        }
    }
}
