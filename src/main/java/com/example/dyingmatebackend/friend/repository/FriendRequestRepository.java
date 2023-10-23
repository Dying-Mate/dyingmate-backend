package com.example.dyingmatebackend.friend.repository;

import com.example.dyingmatebackend.friend.entity.FriendRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FriendRequestRepository extends JpaRepository<FriendRequest, Long> {
    List<FriendRequest> findByReceiverEmail(String email);
    FriendRequest findBySenderEmail(String email);
    void deleteBySenderEmail(String email);
}
