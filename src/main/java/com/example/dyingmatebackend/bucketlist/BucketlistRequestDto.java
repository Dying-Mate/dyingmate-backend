package com.example.dyingmatebackend.bucketlist;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
public class BucketlistRequestDto {

    public String title;
    public String content;
    public double xLoc;
    public double yLoc;
    public MultipartFile photo;

}
