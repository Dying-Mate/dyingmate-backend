package com.example.dyingmatebackend.friendroom;

import com.example.dyingmatebackend.bucketlist.dto.res.BucketlistResponseList;
import com.example.dyingmatebackend.funeral.FuneralResponseDto;
import com.example.dyingmatebackend.message.MessageResponseDto;
import com.example.dyingmatebackend.will.WillResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class FriendRoomResponse {
    private WillResponseDto willResponseDto;
    private MessageResponseDto messageResponseDto;
    private FuneralResponseDto funeralResponseDto;
    private BucketlistResponseList bucketlistResponseList;

    public static FriendRoomResponse of(WillResponseDto willResponseDto, MessageResponseDto messageResponseDto,
                                 FuneralResponseDto funeralResponseDto, BucketlistResponseList bucketlistResponseList) {
        return FriendRoomResponse.builder()
                .willResponseDto(willResponseDto)
                .messageResponseDto(messageResponseDto)
                .funeralResponseDto(funeralResponseDto)
                .bucketlistResponseList(bucketlistResponseList)
                .build();
    }
}
