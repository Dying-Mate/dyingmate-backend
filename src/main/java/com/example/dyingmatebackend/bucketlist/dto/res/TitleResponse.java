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
public class TitleResponse {

    private Long bucketlistId;
    private String title;
    private double memoX;
    private double memoY;

    public static TitleResponse of(Bucketlist bucketlist) {
        return TitleResponse.builder()
                .bucketlistId(bucketlist.getBucketlistId())
                .title(bucketlist.getTitle())
                .memoX(bucketlist.getMemoX())
                .memoY(bucketlist.getMemoY())
                .build();
    }
}
