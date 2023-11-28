package com.example.dyingmatebackend.comment;

import com.example.dyingmatebackend.user.User;
import com.example.dyingmatebackend.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final HeartRepository heartRepository;

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
    public List<CommentResponseDto> getComments(Long userId) {
        List<Comment> all = commentRepository.findAll();

        List<CommentResponseDto> commentList = new ArrayList<>();

        Comparator<Comment> comparator = (date1, date2) -> Long.valueOf(
                date1.getCreation_date().getTime()).compareTo(date2.getCreation_date().getTime());

        Collections.sort(all, comparator.reversed());

        User user = userRepository.findById(userId).get();

        for (Comment comment : all) {
            boolean isPush;
            if (heartRepository.existsByUserAndComment(user, comment)) isPush = true;
            else isPush = false;

            commentList.add(CommentResponseDto.of(comment, isPush));
        }

        return commentList;
    }
}
