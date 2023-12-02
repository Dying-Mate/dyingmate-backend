package com.example.dyingmatebackend.bucketlist;

import com.example.dyingmatebackend.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BucketlistRepository extends JpaRepository<Bucketlist, Long> {
    List<Bucketlist> findByUserUserId(Long userId);
    Optional<List<Bucketlist>> findByUser(User user);
    void deleteByUserUserId(Long userId);
    boolean existsByUserUserId(Long userId);
}
