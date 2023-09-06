package com.example.dyingmatebackend.will;

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
@Table(name = "will")
public class Will {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "will_id")
    private Long willId;

    @Column(nullable = false)
    private String content;
}
