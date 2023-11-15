package com.example.dyingmatebackend.ending;

import com.example.dyingmatebackend.bucketlist.BucketlistRepository;
import com.example.dyingmatebackend.exception.ApplicatonException;
import com.example.dyingmatebackend.exception.ErrorCode;
import com.example.dyingmatebackend.funeral.FuneralRepository;
import com.example.dyingmatebackend.message.MessageRepository;
import com.example.dyingmatebackend.will.WillRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class EndingService {

    private final WillRepository willRepository;
    private final MessageRepository messageRepository;
    private final FuneralRepository funeralRepository;
    private final BucketlistRepository bucketlistRepository;

    public boolean isAllDone(Long userId) {
        if (willRepository.existsByUserUserId(userId) && messageRepository.existsByUserUserId(userId)
                && funeralRepository.existsByUserUserId(userId) && bucketlistRepository.existsByUserUserId(userId)) {
            return true;
        } else {
            return false;
        }
    }
}
