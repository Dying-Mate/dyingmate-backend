package com.example.dyingmatebackend.bucketlist.dto.req;

import com.example.dyingmatebackend.bucketlist.Bucketlist;
import com.example.dyingmatebackend.user.User;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ContentRequest {

    private String content;
    private double memoX;
    private double memoY;

    public Bucketlist toEntity(User user) {
        return Bucketlist.builder()
                .title(null)
                .content(content)
                .isComplete(false)
                .memoX(memoX)
                .memoY(memoY)
                .photo(null)
                .user(user)
                .build();
    }
}
