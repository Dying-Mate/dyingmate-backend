package com.example.dyingmatebackend.comment;

import com.example.dyingmatebackend.user.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "comment")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    private String content;

    @CreationTimestamp
    private Date creation_date;

    private int likeNum;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public void increaseLikeNum() {
        this.likeNum++;
    }
}
