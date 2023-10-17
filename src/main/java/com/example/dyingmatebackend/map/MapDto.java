package com.example.dyingmatebackend.map;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MapDto {

    private boolean stage1;
    private boolean stage2;
    private boolean stage3;
    private boolean stage4;

    public static MapDto of(Map map) {
        return MapDto.builder()
                .stage1(map.isStage1())
                .stage2(map.isStage2())
                .stage3(map.isStage3())
                .stage4(map.isStage4())
                .build();
    }
}
