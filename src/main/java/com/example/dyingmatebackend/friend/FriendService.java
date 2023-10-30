package com.example.dyingmatebackend.friend;

import com.example.dyingmatebackend.friend.dto.res.FriendListResponse;
import com.example.dyingmatebackend.friend.dto.res.FriendRequestResponse;
import com.example.dyingmatebackend.friend.dto.res.FriendResponseList;
import com.example.dyingmatebackend.friend.dto.res.FriendSearch;
import com.example.dyingmatebackend.friend.entity.FriendList;
import com.example.dyingmatebackend.friend.entity.FriendRequest;
import com.example.dyingmatebackend.friend.repository.FriendListRepository;
import com.example.dyingmatebackend.friend.repository.FriendRequestRepository;
import com.example.dyingmatebackend.user.User;
import com.example.dyingmatebackend.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class FriendService {

    private final FriendListRepository friendListRepository;
    private final FriendRequestRepository friendRequestRepository;
    private final UserRepository userRepository;

    // 친구 검색
    public List<FriendSearch> searchFriend() {
//        List<User> searchResult = userRepository.findByEmailContaining(email);
//
//        List<FriendSearch> friendSearchList = searchResult.stream()
//                .map(FriendSearch::of)
//                .collect(Collectors.toList());
//
//        return friendSearchList;

        List<User> all = userRepository.findAll();

        List<FriendSearch> friendSearchList = all.stream()
                .map(FriendSearch::of)
                .collect(Collectors.toList());

        return friendSearchList;
    }

    // 친구 요청
    public String requestFriend(Long userId, FriendSearch friendSearch) {
        User user = userRepository.findById(userId).get();

        // TODO: 이미 친구가 되어있을 경우 친구 요청 안되게 하기

        friendRequestRepository.save(FriendRequest.builder()
                        .senderEmail(user.getEmail())
                        .receiverEmail(friendSearch.getFriendEmail())
                        .build());

        return friendSearch.getFriendEmail() + " 친구 요청 완료";
    }
    
    public FriendResponseList getFriendAllList(Long userId) {
        User user = userRepository.findById(userId).get();
        
        // 친구 요청 받은 목록 조회
        List<FriendRequest> requestLists = friendRequestRepository.findByReceiverEmail(user.getEmail());

        List<FriendRequestResponse> requestResponseList = new ArrayList<>(); // 친구 요청한 사람들의 List

        for (FriendRequest friendRequest : requestLists) {
            User senderInform = userRepository.findByEmail(friendRequest.getSenderEmail()).get();
            requestResponseList.add(FriendRequestResponse.of(senderInform));
        }

        // 친구 맺은 목록 조회
        List<FriendList> friendListLists = friendListRepository.findByUserUserId(user.getUserId());

        List<FriendListResponse> listResponseList = new ArrayList<>();

        for (FriendList friendList : friendListLists) {
            User friendInform = userRepository.findByEmail(friendList.getFriendEmail()).get();
            listResponseList.add(FriendListResponse.of(friendInform));
        }

        return FriendResponseList.of(requestResponseList, listResponseList);
    }

    // 친구 수락
    @Transactional
    public String acceptFriend(Long userId, String acceptEmail) {
        User user = userRepository.findById(userId).get();
        friendRequestRepository.delete(friendRequestRepository.findBySenderEmail(acceptEmail));
        friendListRepository.save(FriendList.builder()
                        .friendEmail(acceptEmail)
                        .user(user)
                        .build());

        return acceptEmail + " 친구 수락";
    }

    // 친구 취소
    @Transactional
    public String refuseFriend(String refuseEmail) {
        friendRequestRepository.deleteBySenderEmail(refuseEmail);

        return refuseEmail + " 친구 거부";
    }
}
