package com.example.dyingmatebackend.comment;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    void deleteByUserUserId(Long userId);
}
