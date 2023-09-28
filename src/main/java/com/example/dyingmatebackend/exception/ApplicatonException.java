package com.example.dyingmatebackend.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ApplicatonException extends RuntimeException {
    ErrorCode errorCode;
}
