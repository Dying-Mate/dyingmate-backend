package com.example.dyingmatebackend.map;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class MapService {

    private final MapRepository mapRepository;

    // 지도 잠금 오픈
    @Transactional
    public String openStage(Long userId, int stage) {
        Map map = mapRepository.findByUserUserId(userId);
        map.updateMap(stage);
        return "Stage" + stage + " Open!";
    }

    // 지도 stage 조회
    public MapDto getMap(Long userId) {
        return MapDto.of(mapRepository.findByUserUserId(userId));
    }

    public void resetMap(Long userId) {
        mapRepository.findByUserUserId(userId).resetMap();
    }
}
