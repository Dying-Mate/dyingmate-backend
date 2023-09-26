package com.example.dyingmatebackend.will;

import com.example.dyingmatebackend.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class WillRequestDto {

    private String content;

    public Will toEntity(User user, String content) {
        return Will.builder()
                .user(user)
                .content(content)
                .build();
    }
}
