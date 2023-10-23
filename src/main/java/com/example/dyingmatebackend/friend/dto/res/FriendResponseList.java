package com.example.dyingmatebackend.friend.dto.res;

import com.example.dyingmatebackend.friend.entity.FriendRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FriendResponseList {

    private List<FriendRequestResponse> friendRequestResponseList;
    private List<FriendListResponse> friendListResponseList;

    public static FriendResponseList of(List<FriendRequestResponse> friendRequestResponseList,
                                        List<FriendListResponse> friendListResponseList) {
        return FriendResponseList.builder()
                .friendRequestResponseList(friendRequestResponseList)
                .friendListResponseList(friendListResponseList)
                .build();
    }
}
