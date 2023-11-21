package com.example.dyingmatebackend.funeral;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class FuneralResponseDto {

    int method;
    String epitaph;
    String portrait_photo;

    public static FuneralResponseDto toDto(Funeral funeral, String imageUrl) {
        return FuneralResponseDto.builder()
                .method(funeral.getMethod())
                .epitaph(funeral.getEpitaph())
                .portrait_photo(imageUrl)
                .build();
    }
}
