package com.example.dyingmatebackend.bucketlist;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class BucketlistResponseDto {

    Long bucketlistId;
    String title;
    String content;
    String isComplete;
    double xLoc;
    double yLoc;
    String photo;

    public static BucketlistResponseDto toDto(Bucketlist bucketlist) {
        String isComplete;

        if (bucketlist.isComplete() == true)  isComplete = "true";
        else isComplete = "false";

        return BucketlistResponseDto.builder()
                .bucketlistId(bucketlist.getBucketlistId())
                .title(bucketlist.getTitle())
                .content(bucketlist.getContent())
                .isComplete(isComplete)
                .xLoc(bucketlist.getXLoc())
                .yLoc(bucketlist.getYLoc())
                .photo(bucketlist.getPhoto())
                .build();
    }
}
