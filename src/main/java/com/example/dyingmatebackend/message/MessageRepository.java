package com.example.dyingmatebackend.message;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {
    Message findByUserUserId(Long userId);
    void deleteByUserUserId(Long userId);
    boolean existsByUserUserId(Long userId);
}
