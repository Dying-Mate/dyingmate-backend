package com.example.dyingmatebackend.funeral;

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
@Table(name = "funeral")
public class Funeral {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "funeral_id")
    private Long funeralId;

    @Column(nullable = false)
    private int method; // 장례방식

    @Column(nullable = false)
    private String epitaph; // 묘비명

    @Column(nullable = false)
    private String portrait_photo; // 영정사진

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    public void updateFuneral(FuneralRequestDto requestDto) {
        this.method = requestDto.getMethod();
        this.epitaph = requestDto.getEpitaph();
        this.portrait_photo = requestDto.getPortrait_photo();
    }
}
