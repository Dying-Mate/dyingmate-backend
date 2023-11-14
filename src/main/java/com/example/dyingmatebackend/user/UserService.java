package com.example.dyingmatebackend.user;

import com.example.dyingmatebackend.bucketlist.BucketlistRepository;
import com.example.dyingmatebackend.comment.CommentRepository;
import com.example.dyingmatebackend.exception.ApplicatonException;
import com.example.dyingmatebackend.exception.ErrorCode;
import com.example.dyingmatebackend.friend.repository.FriendListRepository;
import com.example.dyingmatebackend.friend.repository.FriendRequestRepository;
import com.example.dyingmatebackend.funeral.FuneralRepository;
import com.example.dyingmatebackend.jwt.JwtAuthenticationProvider;
import com.example.dyingmatebackend.map.Map;
import com.example.dyingmatebackend.map.MapRepository;
import com.example.dyingmatebackend.map.MapService;
import com.example.dyingmatebackend.message.MessageRepository;
import com.example.dyingmatebackend.user.dto.LoginResponse;
import com.example.dyingmatebackend.will.WillRepository;
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

    private final WillRepository willRepository;
    private final MessageRepository messageRepository;
    private final FuneralRepository funeralRepository;
    private final BucketlistRepository bucketlistRepository;
    private final CommentRepository commentRepository;
    private final MapService mapService;
    private final FriendRequestRepository friendRequestRepository;
    private final FriendListRepository friendListRepository;

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
            String accessToken = jwtAuthenticationProvider.createAccessToken(user.getUserId(), user.getEmail());
            String refreshToken = jwtAuthenticationProvider.createRefreshToken(user.getUserId(), user.getEmail());

            LoginResponse response = LoginResponse.builder()
                    .id(user.getUserId())
                    .name(user.getName())
                    .email(user.getEmail())
                    .photoNum(user.getPhotoNum())
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

    @Transactional
    public String modifyName(Long userId, String name) {
        User user = userRepository.findById(userId).get();
        user.setName(name);
        return user.getName();
    }

    // 초기화
    @Transactional
    public String resetUser(Long userId) {
        willRepository.deleteByUserUserId(userId);
        messageRepository.deleteByUserUserId(userId);
        funeralRepository.deleteByUserUserId(userId);
        bucketlistRepository.deleteByUserUserId(userId);
        commentRepository.deleteByUserUserId(userId);
        mapService.resetMap(userId);

        User user = userRepository.findById(userId).get();
        friendRequestRepository.deleteBySenderEmail(user.getEmail());
        friendRequestRepository.deleteByReceiverEmail(user.getEmail());
        friendListRepository.deleteByUserUserId(userId);
        friendListRepository.deleteByFriendEmail(user.getEmail());
        return "초기화 완료";
    }
}
