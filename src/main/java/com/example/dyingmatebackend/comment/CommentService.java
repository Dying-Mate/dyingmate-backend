package com.example.dyingmatebackend.comment;

import com.example.dyingmatebackend.user.User;
import com.example.dyingmatebackend.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    // 댓글 등록
    public String registerComment(Long userId, CommentRequestDto commentRequestDto) {
        User user = userRepository.findById(userId).get();

        Comment newComment = Comment.builder()
                .content(commentRequestDto.getContent())
                .user(user)
                .build();

        commentRepository.save(newComment);
        return "댓글 등록 완료";
    }

    // 댓글 조회
    public List<CommentResponseDto> getComments() {
        List<Comment> all = commentRepository.findAll();

        List<CommentResponseDto> commentList = new ArrayList<>();

        Comparator<Comment> comparator = (date1, date2) -> Long.valueOf(
                date1.getCreation_date().getTime()).compareTo(date2.getCreation_date().getTime());

        Collections.sort(all, comparator.reversed());

        for (Comment comment : all) {
            commentList.add(CommentResponseDto.of(comment));
        }

        return commentList;
    }

    // 좋아요 수 추가
    @Transactional
    public String addLikeNum(Long userId, Long commentId) {
        Comment comment = commentRepository.findById(commentId).get();
        comment.increaseLikeNum();

        return "좋아요 수 추가";
    }
}
