package com.example.dyingmatebackend.comment;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentResponseDto {

    private Long commentId;
    private int profile;
    private String name;
    private String content;
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm", timezone="Asia/Seoul")
    private Date creationTime;
    private int likeNum;
    private boolean isPush;

    public static CommentResponseDto of(Comment comment, boolean isPush) {
        return CommentResponseDto.builder()
                .commentId(comment.getCommentId())
                .profile(comment.getUser().getPhotoNum())
                .name(comment.getUser().getName())
                .content(comment.getContent())
                .creationTime(comment.getCreation_date())
                .likeNum(comment.getLikeNum())
                .isPush(isPush)
                .build();
    }
}
