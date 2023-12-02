package com.example.dyingmatebackend.message;

import com.example.dyingmatebackend.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MessageRepository extends JpaRepository<Message, Long> {
    Message findByUserUserId(Long userId);
    Optional<Message> findByUser(User user);
    void deleteByUserUserId(Long userId);
    boolean existsByUserUserId(Long userId);
}
