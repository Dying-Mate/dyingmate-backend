package com.example.dyingmatebackend.funeral;

import com.example.dyingmatebackend.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FuneralRepository extends JpaRepository<Funeral, Long> {
    Funeral findByUserUserId(Long userId);
    Optional<Funeral> findByUser(User user);
    void deleteByUserUserId(Long userId);
    boolean existsByUserUserId(Long userId);
}
