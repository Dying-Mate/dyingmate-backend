package com.example.dyingmatebackend.exception;

import com.example.dyingmatebackend.ApiResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(ApplicatonException.class)
    public ApiResponse<?> handleException(ApplicatonException e) {
        return ApiResponse.error(e.getErrorCode());
    }
}
