package com.example.dyingmatebackend.bucketlist.dto.res;

import com.example.dyingmatebackend.bucketlist.Bucketlist;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileResponse {

    private Long bucketlistId;
    private String content;
    private String isComplete;
    private double memoX;
    private double memoY;
    private String photo;

    public static FileResponse of(Bucketlist bucketlist) {
        String Complete;

        if (bucketlist.isComplete() == true) Complete = "true";
        else Complete = "false";

        return FileResponse.builder()
                .bucketlistId(bucketlist.getBucketlistId())
                .content(bucketlist.getContent())
                .isComplete(Complete)
                .memoX(bucketlist.getMemoX())
                .memoY(bucketlist.getMemoY())
                .photo(bucketlist.getPhoto())
                .build();
    }

}
