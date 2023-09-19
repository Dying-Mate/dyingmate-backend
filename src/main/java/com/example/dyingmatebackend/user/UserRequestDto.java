package com.example.dyingmatebackend.user;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class UserRequestDto {
    private String email;
    private String pwd;
}
