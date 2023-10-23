package com.example.dyingmatebackend.friend.repository;

import com.example.dyingmatebackend.friend.entity.FriendList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FriendListRepository extends JpaRepository<FriendList, Long> {
    List<FriendList> findByUserUserId(Long userId);
}
