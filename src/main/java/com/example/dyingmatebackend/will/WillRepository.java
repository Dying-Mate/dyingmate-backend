package com.example.dyingmatebackend.will;

import org.springframework.data.jpa.repository.JpaRepository;

public interface WillRepository extends JpaRepository<Will, Long> {
    Will findByUserUserId(Long userId);
}
