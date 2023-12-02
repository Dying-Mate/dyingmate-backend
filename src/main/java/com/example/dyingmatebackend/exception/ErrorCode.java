package com.example.dyingmatebackend.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    DUPLICATE_EMAIL(HttpStatus.UNAUTHORIZED, "중복된 이메일입니다."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않은 이메일입니다."),
    INCORRECT_PASSWORD(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다."),
    ALREADY_REQUEST_FRIEND(HttpStatus.BAD_REQUEST, "이미 친구 요청하였습니다."),
    ALREADY_ADD_FRIEND(HttpStatus.BAD_REQUEST, "이미 친구 추가하였습니다."),
    ALREADY_PUSH_HEART(HttpStatus.BAD_REQUEST, "이미 좋아요를 눌렀습니다."),
    NOT_PUSH_HEART(HttpStatus.BAD_REQUEST, "좋아요를 누르지 않았습니다."),
    FRIEND_WILL_NULL(HttpStatus.NO_CONTENT, "친구가 아직 유언장을 기록하지 않았습니다."),
    FRIEND_MESSAGE_NULL(HttpStatus.NO_CONTENT, "친구가 아직 부고문자를 기록하지 않았습니다."),
    FRIEND_FUNERAL_NULL(HttpStatus.NO_CONTENT, "친구가 아직 장례방식을 기록하지 않았습니다."),
    FRIEND_BUCKETLIST_NULL(HttpStatus.NO_CONTENT, "친구가 아직 버킷리스트를 기록하지 않았습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
