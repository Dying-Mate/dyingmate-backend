package com.example.dyingmatebackend.bucketlist;

import com.example.dyingmatebackend.user.User;
import com.example.dyingmatebackend.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class BucketlistService {

    private final BucketlistRepository bucketlistRepository;
    private final UserRepository userRepository;

    // 버킷리스트 추가
    public String addMemo(String email, BucketlistRequestDto bucketlistRequestDto) {
        User user = userRepository.findByEmail(email).get();

        Bucketlist bucketlist = Bucketlist.builder()
                .title(bucketlistRequestDto.getTitle())
                .content(bucketlistRequestDto.getContent())
                .isComplete(false)
                .xLoc(bucketlistRequestDto.getXLoc())
                .yLoc(bucketlistRequestDto.getYLoc())
                .photo(bucketlistRequestDto.getPhoto().getOriginalFilename())
                .user(user)
                .build();

        bucketlistRepository.save(bucketlist);
        return "버킷리스트 추가";
    }

    // 버킷리스트 조회
    public List<BucketlistResponseDto> getBucketlist(Long userId) {
        List<Bucketlist> bucketlists = bucketlistRepository.findByUserUserId(userId);

        List<BucketlistResponseDto> bucketlistResponseDtoList = new ArrayList<>();

        for (Bucketlist memo : bucketlists) {
            bucketlistResponseDtoList.add(BucketlistResponseDto.toDto(memo));
        }

        return bucketlistResponseDtoList;
    }

    // 버킷리스트 달성 여부 체크
    @Transactional
    public String checkBucketlist(Long bucketlistId) {
        Bucketlist bucketlist = bucketlistRepository.findById(bucketlistId).get();
        bucketlist.checkComplete(true);
        return "버킷리스트 달성 완료";
    }
}
