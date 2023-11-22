package com.example.dyingmatebackend.bucketlist.dto.res;

import com.amazonaws.services.s3.AmazonS3Client;
import com.example.dyingmatebackend.bucketlist.Bucketlist;
import lombok.*;

import java.net.URL;

@Getter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class FileResponse {

    private Long bucketlistId;
    private String content;
    private String isComplete;
    private double memoX;
    private double memoY;
    private String photo;

    private static final AmazonS3Client s3Client = new AmazonS3Client();

    public static FileResponse of(Bucketlist bucketlist) {
        String Complete;

        if (bucketlist.isComplete() == true) Complete = "true";
        else Complete = "false";

        String userEmail = bucketlist.getUser().getEmail();
        String path = userEmail + "-bucketlist-" + bucketlist.getPhoto();
        URL url = s3Client.getUrl("dying-mate-server.link", path);

        String imageUrl;

        if (bucketlist.getPhoto() == null) imageUrl = null;
        else imageUrl = url.toString();

        return FileResponse.builder()
                .bucketlistId(bucketlist.getBucketlistId())
                .content(bucketlist.getContent())
                .isComplete(Complete)
                .memoX(bucketlist.getMemoX())
                .memoY(bucketlist.getMemoY())
                .photo(imageUrl)
                .build();
    }

}
