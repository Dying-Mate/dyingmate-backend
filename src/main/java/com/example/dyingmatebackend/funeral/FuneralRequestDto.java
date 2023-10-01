package com.example.dyingmatebackend.funeral;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
public class FuneralRequestDto {

    public int method;
    public String epitaph;
    public MultipartFile portrait_photo;

}
