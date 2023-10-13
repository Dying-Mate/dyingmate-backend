package com.example.dyingmatebackend.bucketlist.dto.res;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BucketlistResponseList {

    private List<FileResponse> fileResponseList;
    private List<TitleResponse> titleResponseList;

    public static BucketlistResponseList of(List<FileResponse> fileResponseList,
                                            List<TitleResponse> titleResponseList) {
        return BucketlistResponseList.builder()
                .fileResponseList(fileResponseList)
                .titleResponseList(titleResponseList)
                .build();
    }
}
