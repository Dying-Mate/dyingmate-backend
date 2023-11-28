package com.example.dyingmatebackend.bucketlist.dto.res;

import com.example.dyingmatebackend.bucketlist.Bucketlist;
import lombok.*;

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

    public static FileResponse of(Bucketlist bucketlist, String imageUrl) {
        String Complete;

        if (bucketlist.isComplete() == true) Complete = "true";
        else Complete = "false";

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
