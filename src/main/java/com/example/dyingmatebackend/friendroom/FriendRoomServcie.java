package com.example.dyingmatebackend.friendroom;

import com.example.dyingmatebackend.bucketlist.Bucketlist;
import com.example.dyingmatebackend.bucketlist.BucketlistRepository;
import com.example.dyingmatebackend.bucketlist.dto.res.BucketlistResponseList;
import com.example.dyingmatebackend.bucketlist.dto.res.FileResponse;
import com.example.dyingmatebackend.bucketlist.dto.res.TitleResponse;
import com.example.dyingmatebackend.funeral.Funeral;
import com.example.dyingmatebackend.funeral.FuneralRepository;
import com.example.dyingmatebackend.funeral.FuneralResponseDto;
import com.example.dyingmatebackend.message.Message;
import com.example.dyingmatebackend.message.MessageRepository;
import com.example.dyingmatebackend.message.MessageResponseDto;
import com.example.dyingmatebackend.user.User;
import com.example.dyingmatebackend.user.UserRepository;
import com.example.dyingmatebackend.will.Will;
import com.example.dyingmatebackend.will.WillRepository;
import com.example.dyingmatebackend.will.WillResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class FriendRoomServcie {

    private final UserRepository userRepository;
    private final WillRepository willRepository;
    private final MessageRepository messageRepository;
    private final FuneralRepository funeralRepository;
    private final BucketlistRepository bucketlistRepository;

    public WillResponseDto getWill(String email) {
        User user = userRepository.findByEmail(email).get();
        Will friendWill = willRepository.findByUserUserId(user.getUserId());
        return WillResponseDto.toDto(friendWill);
    }

    public MessageResponseDto getMessage(String email) {
        User user = userRepository.findByEmail(email).get();
        Message friendMessage = messageRepository.findByUserUserId(user.getUserId());
        return MessageResponseDto.toDto(friendMessage);
    }

    public FuneralResponseDto getFuneral(String email) {
        User user = userRepository.findByEmail(email).get();
        Funeral friendFuneral = funeralRepository.findByUserUserId(user.getUserId());
        return FuneralResponseDto.toDto(friendFuneral);
    }

    public BucketlistResponseList getBucketlist(String email) {
        User user = userRepository.findByEmail(email).get();
        List<Bucketlist> bucketlists = bucketlistRepository.findByUserUserId(user.getUserId());

        List<FileResponse> fileResponseList = bucketlists.stream()
                .filter(memo -> memo.getTitle() == null)
                .map(FileResponse::of)
                .collect(Collectors.toList());

        List<TitleResponse> titleResponseList = bucketlists.stream()
                .filter(memo -> memo.getTitle() != null)
                .map(TitleResponse::of)
                .collect(Collectors.toList());

        return BucketlistResponseList.of(fileResponseList, titleResponseList);
    }
}
