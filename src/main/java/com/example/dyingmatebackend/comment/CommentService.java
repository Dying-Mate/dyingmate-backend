package com.example.dyingmatebackend.comment;

import com.example.dyingmatebackend.user.User;
import com.example.dyingmatebackend.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

        for (Comment comment : all) {
            commentList.add(CommentResponseDto.of(comment));
        }

        return commentList;
    }
}
