package com.example.dyingmatebackend.bucketlist;

import com.example.dyingmatebackend.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "bucketlist")
public class Bucketlist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bucketlist_id")
    private Long bucketlistId;

    private String title;
    private String content;

    private boolean isComplete; // true = 1 = 완료, false = 0 = 미완료

    private double xLoc;
    private double yLoc;

    @Column(nullable = true)
    private String photo;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    public void checkComplete(boolean check) {
        this.isComplete = check;
    }
}
