package com.frank.apisocialnetwork.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(ApiSocialNetworkException.class)
    public ResponseEntity<ApiError> exception(ApiSocialNetworkException e) {
        ApiError apiError = new ApiError();
        apiError.setMessage(e.getMessage());
        apiError.setTimestamp(Instant.now());
        return new ResponseEntity<>(apiError, e.getStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> exception(Exception e) {
        ApiError apiError = new ApiError();
        apiError.setMessage(e.getMessage());
        apiError.setTimestamp(Instant.now());
        return new ResponseEntity<>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
