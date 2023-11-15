package com.example.dyingmatebackend.funeral;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FuneralRepository extends JpaRepository<Funeral, Long> {
    Funeral findByUserUserId(Long userId);
    void deleteByUserUserId(Long userId);
    boolean existsByUserUserId(Long userId);
}
