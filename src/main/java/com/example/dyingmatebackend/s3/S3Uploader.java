package com.example.dyingmatebackend.s3;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RequiredArgsConstructor
@Component
@Service
public class S3Uploader {

    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public String uploadImage(MultipartFile file) throws IOException { // 이미지 S3에 업로드하고 이미지 url 반환
        String s3FileName = UUID.randomUUID() + "-" + file.getOriginalFilename(); // image 이름 중복 방지를 위해 랜덤으로 생성

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(file.getContentType()); // 이미지 외 다른 파일 받을 시 파일 형식에 맞게 변환
        metadata.setContentLength(file.getSize());

        amazonS3.putObject(bucket, s3FileName, file.getInputStream(), metadata);

        return amazonS3.getUrl(bucket, s3FileName).toString();
    }

    public void deleteImage(String fileName) { // 이미지 삭제
        amazonS3.deleteObject(new DeleteObjectRequest(bucket, fileName));
    }
}
