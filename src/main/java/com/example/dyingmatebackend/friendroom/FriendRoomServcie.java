package com.example.dyingmatebackend.friendroom;

import com.amazonaws.services.s3.AmazonS3Client;
import com.example.dyingmatebackend.bucketlist.Bucketlist;
import com.example.dyingmatebackend.bucketlist.BucketlistRepository;
import com.example.dyingmatebackend.bucketlist.dto.res.BucketlistResponseList;
import com.example.dyingmatebackend.bucketlist.dto.res.FileResponse;
import com.example.dyingmatebackend.bucketlist.dto.res.TitleResponse;
import com.example.dyingmatebackend.exception.ApplicatonException;
import com.example.dyingmatebackend.exception.ErrorCode;
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

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class FriendRoomServcie {

    private final UserRepository userRepository;
    private final WillRepository willRepository;
    private final MessageRepository messageRepository;
    private final FuneralRepository funeralRepository;
    private final BucketlistRepository bucketlistRepository;
    private final AmazonS3Client s3Client;

    public FriendRoomResponse getData(String email) {
        User user = userRepository.findByEmail(email).get();

        // 친구 유언장
        Will nullWill = new Will(); // 유언장 아직 기록하지 않았을 경우
        Will friendWill = willRepository.findByUser(user).orElseGet(() -> nullWill);

        // 친구 부고문자
        Message nullMessage = new Message(); // 부고문자 아직 기록하지 않았을 경우
        Message friendMessage = messageRepository.findByUser(user).orElseGet(() -> nullMessage);

        // 친구 장례준비
        Funeral nullFuneral = new Funeral(); // 장례준비 아직 기록하지 않았을 경우
        Funeral friendFuneral = funeralRepository.findByUser(user).orElseGet(() -> nullFuneral);

        String funeralPath = user.getEmail() + "-funeral-" + friendFuneral.getPortrait_photo();
        URL funeralUrl = s3Client.getUrl("dying-mate-server.link", funeralPath);

        // 친구 버킷리스트
        List<Bucketlist> nullBucketlist = new ArrayList<>();
        List<Bucketlist> friendBucketlist = bucketlistRepository.findByUser(user).orElseGet(() -> nullBucketlist);

        List<FileResponse> friendFileBucket = new ArrayList<>();
        for (Bucketlist bucketlist : friendBucketlist) {
            String imageUrl;
            if (bucketlist.getPhoto() == null) imageUrl = null;
            else {
                String bucketlistPath = user.getEmail() + "-bucketlist-" + bucketlist.getPhoto();
                URL bucketlistUrl = s3Client.getUrl("dying-mate-server.link", bucketlistPath);
                imageUrl = bucketlistUrl.toString();
            }
            friendFileBucket.add(FileResponse.of(bucketlist, imageUrl));
        }

        List<TitleResponse> friendTitleBucket = friendBucketlist.stream()
                .filter(memo -> memo.getTitle() != null)
                .map(TitleResponse::of)
                .collect(Collectors.toList());


        return FriendRoomResponse.of(WillResponseDto.toDto(friendWill),
                                    MessageResponseDto.toDto(friendMessage),
                                    FuneralResponseDto.toDto(friendFuneral, funeralUrl.toString()),
                                    BucketlistResponseList.of(friendFileBucket, friendTitleBucket));
    }

    public WillResponseDto getWill(String email) {
        User user = userRepository.findByEmail(email).get();
        Will friendWill = willRepository.findByUserUserId(user.getUserId());

        if (friendWill == null) throw new ApplicatonException(ErrorCode.FRIEND_WILL_NULL);

        return WillResponseDto.toDto(friendWill);
    }

    public MessageResponseDto getMessage(String email) {
        User user = userRepository.findByEmail(email).get();
        Message friendMessage = messageRepository.findByUserUserId(user.getUserId());

        if (friendMessage == null) throw new ApplicatonException(ErrorCode.FRIEND_MESSAGE_NULL);

        return MessageResponseDto.toDto(friendMessage);
    }

    public FuneralResponseDto getFuneral(String email) {
        User user = userRepository.findByEmail(email).get();
        Funeral friendFuneral = funeralRepository.findByUserUserId(user.getUserId());

        String imageUrl;
        if (friendFuneral == null) throw new ApplicatonException(ErrorCode.FRIEND_FUNERAL_NULL);
        else {
            String path = user.getEmail() + "-funeral-" + friendFuneral.getPortrait_photo();
            URL url = s3Client.getUrl("dying-mate-server.link", path);
            imageUrl = url.toString();
        }

        return FuneralResponseDto.toDto(friendFuneral, imageUrl);
    }

    public BucketlistResponseList getBucketlist(String email) {
        User user = userRepository.findByEmail(email).get();
        List<Bucketlist> bucketlists = bucketlistRepository.findByUserUserId(user.getUserId());

        if (bucketlists.isEmpty()) throw new ApplicatonException(ErrorCode.FRIEND_BUCKETLIST_NULL);

        String imageUrl;
        List<FileResponse> fileResponseList = new ArrayList<>();

        for (Bucketlist bucketlist : bucketlists) {
            if (bucketlist.getTitle() == null) {
                if (bucketlist.getPhoto() == null) imageUrl = null;
                else {
                    String path = user.getEmail() + "-bucketlist-" + bucketlist.getPhoto();
                    URL url = s3Client.getUrl("dying-mate-server.link", path);
                    imageUrl = url.toString();
                }
                fileResponseList.add(FileResponse.of(bucketlist, imageUrl));
            }
        }

        List<TitleResponse> titleResponseList = bucketlists.stream()
                .filter(memo -> memo.getTitle() != null)
                .map(TitleResponse::of)
                .collect(Collectors.toList());

        return BucketlistResponseList.of(fileResponseList, titleResponseList);
    }
}
