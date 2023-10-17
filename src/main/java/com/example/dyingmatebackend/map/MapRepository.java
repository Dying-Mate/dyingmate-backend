package com.example.dyingmatebackend.map;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MapRepository extends JpaRepository<Map, Long> {

    Map findByUserUserId(Long userId);
}
