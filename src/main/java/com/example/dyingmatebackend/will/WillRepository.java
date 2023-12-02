package com.example.dyingmatebackend.will;

import com.example.dyingmatebackend.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WillRepository extends JpaRepository<Will, Long> {
    Will findByUserUserId(Long userId);
    Optional<Will> findByUser(User user);
    void deleteByUserUserId(Long userId);
    boolean existsByUserUserId(Long userId);
}
