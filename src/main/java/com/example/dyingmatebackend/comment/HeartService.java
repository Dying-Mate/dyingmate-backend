package com.example.dyingmatebackend.comment;

import com.example.dyingmatebackend.exception.ApplicatonException;
import com.example.dyingmatebackend.exception.ErrorCode;
import com.example.dyingmatebackend.user.User;
import com.example.dyingmatebackend.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class HeartService {

    private final HeartRepository heartRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    // 댓글 좋아요 수 추가
    public int addHeartCount(Long userId, Long commentId) {
        User user = userRepository.findById(userId).get();
        Comment comment = commentRepository.findById(commentId).get();

        if (heartRepository.existsByUserAndComment(user, comment)) { // 좋아요 이미 눌렀을 시 exception
            throw new ApplicatonException(ErrorCode.ALREADY_PUSH_HEART);
        }

        comment.increaseLikeNum();
        heartRepository.save(Heart.builder()
                .user(user)
                .comment(comment)
                .build());

        return comment.getLikeNum();
    }

    // 댓글 좋아요 취소
    @Transactional
    public int cancelHeart(Long userId, Long commentId) {
        User user = userRepository.findById(userId).get();
        Comment comment = commentRepository.findById(commentId).get();

        if (!heartRepository.existsByUserAndComment(user, comment)) { // 좋아요를 눌러놓지 않았을 시 exception
            throw new ApplicatonException(ErrorCode.NOT_PUSH_HEART);
        }

        comment.decreaseLikeNum();
        heartRepository.deleteByUserAndComment(user, comment);

        return comment.getLikeNum();
    }
}
