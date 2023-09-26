package com.example.dyingmatebackend.funeral;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FuneralRequestDto {

    public int method;
    public String epitaph;
    public String portrait_photo;

}
