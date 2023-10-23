package com.example.dyingmatebackend.friend.entity;

import com.example.dyingmatebackend.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FriendList { // 친구 목록 (친구 맺어진 목록들만!)

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long friendListId;

    private String friendEmail;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;
}
