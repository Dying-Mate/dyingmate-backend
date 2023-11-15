package com.example.dyingmatebackend.bucketlist;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BucketlistRepository extends JpaRepository<Bucketlist, Long> {
    List<Bucketlist> findByUserUserId(Long userId);
    void deleteByUserUserId(Long userId);
    boolean existsByUserUserId(Long userId);
}
