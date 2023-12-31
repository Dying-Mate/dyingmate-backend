package com.example.dyingmatebackend.message;

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
@Table(name = "message")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_id")
    private Long messageId;

    @Column(nullable = false)
    private String message;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    public void updateMessage(String message) {
        this.message = message;
    }
}
