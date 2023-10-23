package com.example.dyingmatebackend.friend.dto.res;

import com.example.dyingmatebackend.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FriendListResponse {

    private String email;
    private String name;
    private int photo;

    public static FriendListResponse of(User user) {
        return FriendListResponse.builder()
                .email(user.getEmail())
                .name(user.getName())
                .photo(user.getPhotoNum())
                .build();
    }
}
