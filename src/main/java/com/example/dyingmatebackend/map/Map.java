package com.example.dyingmatebackend.map;

import com.example.dyingmatebackend.user.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "map")
public class Map {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "map_id")
    private Long mapId;

    @Builder.Default private boolean stage1 = true;
    @Builder.Default private boolean stage2 = false;
    @Builder.Default private boolean stage3 = false;
    @Builder.Default private boolean stage4 = false;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    public void updateMap(int stage) {
        switch (stage) {
            case 2:
                this.stage2 = true;
                break;
            case 3:
                this.stage3 = true;
                break;
            case 4:
                this.stage4 = true;
                break;
        }
    }

    public void resetMap() {
        this.stage2 = false;
        this.stage3 = false;
        this.stage4 = false;
    }
}
