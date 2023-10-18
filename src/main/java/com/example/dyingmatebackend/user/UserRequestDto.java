package com.example.dyingmatebackend.user;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class UserRequestDto {
    private String email;
    private String pwd;
    private int photoNum; // 1번: 할머니, 2번: 아저씨, 3번: 청년
}
