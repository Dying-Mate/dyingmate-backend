package com.example.dyingmatebackend.bucketlist.dto.res;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MoveLocationResponse {

    private Long bucketlistId;
    private double memoX;
    private double memoY;

}
