package com.example.dyingmatebackend.bucketlist.dto.req;

import com.example.dyingmatebackend.bucketlist.Bucketlist;
import com.example.dyingmatebackend.user.User;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FileRequest {

    private String content;
    private double memoX;
    private double memoY;
    private MultipartFile photo;

    public Bucketlist toEntity(User user) {
        String photoSave;

        if (photo.isEmpty()) photoSave = null;
        else photoSave = photo.getOriginalFilename();

        return Bucketlist.builder()
                .title(null)
                .content(content)
                .isComplete(false) // 추가할 때는 아직 완료하지 않았으니까 달성 여부 false
                .memoX(memoX)
                .memoY(memoY)
                .photo(photoSave)
                .user(user)
                .build();
    }
}
