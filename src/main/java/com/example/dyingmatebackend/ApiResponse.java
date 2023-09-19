package com.example.dyingmatebackend;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
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

    public static ApiResponse<?> createError(HttpStatus status, String message) {
        return new ApiResponse<>(status, message, null);
    }
}
