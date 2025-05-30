package com.frank.apisocialnetwork.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ApiSocialNetworkException extends RuntimeException {
    private HttpStatus status;
    public ApiSocialNetworkException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
}
