package com.example.dyingmatebackend.friend.dto.res;

import com.example.dyingmatebackend.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FriendSearch {

    private String friendEmail;
    private String friendName;
    private int friendProfile;

    public static FriendSearch of(User user) {
        return FriendSearch.builder()
                .friendEmail(user.getEmail())
                .friendName(user.getName())
                .friendProfile(user.getPhotoNum())
                .build();
    }
}
