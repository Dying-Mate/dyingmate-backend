package com.example.dyingmatebackend.friend;

import com.example.dyingmatebackend.exception.ApplicatonException;
import com.example.dyingmatebackend.exception.ErrorCode;
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
    public List<FriendSearch> searchFriend(Long userId) {
        User user = userRepository.findById(userId).get();
//        List<User> searchResult = userRepository.findByEmailContaining(email);
//
//        List<FriendSearch> friendSearchList = searchResult.stream()
//                .map(FriendSearch::of)
//                .collect(Collectors.toList());
//
//        return friendSearchList;

        // 로그인한 사용자, 이미 친구 요청한 사람, 이미 친구 맺은 사람 빼고 반환하기

        List<User> all = userRepository.findAll();

        // 이미 친구 요청한 정보 제거
        List<FriendRequest> friendRequests = friendRequestRepository.findBySenderEmail(user.getEmail()); // 내가 친구 요청 보낸 사람들
        List<String> requestEmails = new ArrayList<>();

        for (FriendRequest request : friendRequests) {
            requestEmails.add(request.getReceiverEmail());
        }

        // 이미 친구 추가된 정보 제거
        List<FriendList> friendLists = friendListRepository.findByUserUserId(userId);
        List<String> addEmails = new ArrayList<>();

        for (FriendList addFriend : friendLists) {
            addEmails.add(addFriend.getFriendEmail());
        }

        // 친구 요청 받은 정보 제거
        List<FriendRequest> friendRequested = friendRequestRepository.findByReceiverEmail(user.getEmail());
        List<String> requestedEmails = new ArrayList<>();

        for (FriendRequest requested : friendRequested) {
            requestedEmails.add(requested.getSenderEmail());
        }

        List<FriendSearch> friendSearchList = all.stream()
                .map(FriendSearch::of)
                .filter(me -> !me.getFriendEmail().equals(user.getEmail())) // 사용자 빼고!
                .filter(request -> !requestEmails.contains(request.getFriendEmail())) // 이미 요청한 사람 빼고!
                .filter(requested -> !requestedEmails.contains(requested.getFriendEmail())) // 이미 요청 받은 사람 빼고!
                .filter(add -> !addEmails.contains(add.getFriendEmail())) // 이미 친구 맺은 사람 빼고!
                .collect(Collectors.toList());

        return friendSearchList;
    }

    // 친구 요청
    public String requestFriend(Long userId, String friendEmail) {
        User user = userRepository.findById(userId).get();

        // 이미 친구가 되어있을 경우 exception
        List<FriendList> friendLists = friendListRepository.findByUserUserId(userId);

        for (FriendList friend : friendLists) {
            if (friend.getFriendEmail() != friendEmail) {
                throw new ApplicatonException(ErrorCode.ALREADY_ADD_FRIEND);
            }
        }

        // 이미 친구 요청을 했을 경우 exception
        List<FriendRequest> friendRequests = friendRequestRepository.findBySenderEmail(user.getEmail());

        for (FriendRequest request : friendRequests) {
            if (request.getReceiverEmail() != friendEmail) {
                throw new ApplicatonException(ErrorCode.ALREADY_REQUEST_FRIEND);
            }
        }

        // 친구 요청 가능
        friendRequestRepository.save(FriendRequest.builder()
                .senderEmail(user.getEmail())
                .receiverEmail(friendEmail)
                .build());

        return friendEmail + " 친구 요청 완료";
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
        friendRequestRepository.delete(friendRequestRepository.findByReceiverEmailAndSenderEmail(user.getEmail(), acceptEmail));
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
