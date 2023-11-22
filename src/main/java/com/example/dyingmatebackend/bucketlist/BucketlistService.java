package com.example.dyingmatebackend.bucketlist;

import com.example.dyingmatebackend.bucketlist.dto.req.ContentRequest;
import com.example.dyingmatebackend.bucketlist.dto.req.FileRequest;
import com.example.dyingmatebackend.bucketlist.dto.req.TitleRequest;
import com.example.dyingmatebackend.bucketlist.dto.res.BucketlistResponseList;
import com.example.dyingmatebackend.bucketlist.dto.res.FileResponse;
import com.example.dyingmatebackend.bucketlist.dto.res.TitleResponse;
import com.example.dyingmatebackend.s3.S3Uploader;
import com.example.dyingmatebackend.user.User;
import com.example.dyingmatebackend.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BucketlistService {

    private final BucketlistRepository bucketlistRepository;
    private final UserRepository userRepository;
    private final S3Uploader s3Uploader;

    // 버킷리스트 추가 (form-data)
    public String addFileMemo(String email, FileRequest fileRequest) throws IOException {
        User user = userRepository.findByEmail(email).get();
        bucketlistRepository.save(fileRequest.toEntity(user));
        s3Uploader.uploadImage(user.getEmail(), "bucketlist", fileRequest.getPhoto());
        return "버킷리스트 파일 데이터 추가";
    }

    // 버킷리스트 추가 (내용)
    public String addContentMemo(String email, ContentRequest contentRequest) {
        User user = userRepository.findByEmail(email).get();
        bucketlistRepository.save(contentRequest.toEntity(user));
        return "버킷리스트 내용 추가";
    }

    // 버킷리스트 추가 (타이틀)
    public String addTitleMemo(String email, TitleRequest titleRequest) {
        User user = userRepository.findByEmail(email).get();
        bucketlistRepository.save(titleRequest.toEntity(user));
        return "버킷리스트 타이틀 추가";
    }

    // 버킷리스트 조회
    public BucketlistResponseList getMemos(Long userId) {
        List<Bucketlist> bucketlists = bucketlistRepository.findByUserUserId(userId);

        List<FileResponse> fileResponseList = bucketlists.stream()
                .filter(memo -> memo.getTitle() == null)
                .map(FileResponse::of)
                .collect(Collectors.toList());

        List<TitleResponse> titleResponseList = bucketlists.stream()
                .filter(memo -> memo.getTitle() != null)
                .map(TitleResponse::of)
                .collect(Collectors.toList());

        return BucketlistResponseList.of(fileResponseList, titleResponseList);
    }

    // 버킷리스트 달성 여부 체크
    @Transactional
    public String checkMemo(Long bucketlistId) {
        Bucketlist bucketlist = bucketlistRepository.findById(bucketlistId).get();
        bucketlist.checkComplete(true);
        return "버킷리스트 달성 완료";
    }

    // 버킷리스트 이동
    @Transactional
    public String moveMemo(Long bucketlistId, double x, double y) {
        Bucketlist bucketlist = bucketlistRepository.findById(bucketlistId).get();
        bucketlist.updateXY(x, y);
        return "버킷리스트 이동 완료";
    }

    public String deleteMemo(Long bucketlistId) {
        bucketlistRepository.deleteById(bucketlistId);
        return "버킷리스트 삭제 완료";
    }
}
