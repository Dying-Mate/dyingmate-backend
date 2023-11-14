package com.example.dyingmatebackend.friend.repository;

import com.example.dyingmatebackend.friend.entity.FriendRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FriendRequestRepository extends JpaRepository<FriendRequest, Long> {
    List<FriendRequest> findByReceiverEmail(String email);
    List<FriendRequest> findBySenderEmail(String email);
    FriendRequest findByReceiverEmailAndSenderEmail(String userEmail, String acceptEmail);
    void deleteBySenderEmail(String email);
    void deleteByReceiverEmail(String email);
}
