package com.example.dyingmatebackend;

import com.example.dyingmatebackend.exception.ErrorCode;
import lombok.*;
import org.springframework.http.HttpStatus;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class ApiResponse<T> {

    private HttpStatus status;
    private String message;
    private T data;

    public static<T> ApiResponse<?> createSuccess(String message, T data) {
        return new ApiResponse<>(HttpStatus.OK, message, data);
    }

    public static<T> ApiResponse<?> createSuccessWithNoData(String message) {
        return new ApiResponse<>(HttpStatus.OK, message, null);
    }

    public static <T> ApiResponse<T> ok() {
        return (ApiResponse<T>) ApiResponse.builder()
                .status(HttpStatus.OK)
                .message("성공")
                .data(null)
                .build();
    }

    public static <T> ApiResponse<T> ok(T data) {
        return (ApiResponse<T>) ApiResponse.builder()
                .status(HttpStatus.OK)
                .message("성공")
                .data(data)
                .build();
    }

    public static <T> ApiResponse<T> error(ErrorCode errorCode) {
        return (ApiResponse<T>) ApiResponse.builder()
                .status(errorCode.getHttpStatus())
                .message(errorCode.getMessage())
                .build();
    }
}
