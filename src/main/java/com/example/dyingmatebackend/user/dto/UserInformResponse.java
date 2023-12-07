package com.example.dyingmatebackend.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class UserInformResponse {

    private String name;
    private int photoNum;

}
