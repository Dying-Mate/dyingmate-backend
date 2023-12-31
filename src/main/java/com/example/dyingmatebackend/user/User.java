package com.example.dyingmatebackend.user;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user")
public class User { // 사용자 정보를 담는 인터페이스

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private String email; // (=username)

    private String pwd;

    private String name;

    private int photoNum;

    public void setName(String name) {
        this.name = name;
    }
}
