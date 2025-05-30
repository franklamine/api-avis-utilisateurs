package com.frank.apisocialnetwork.exception;

import lombok.Data;

import java.time.Instant;


@Data
public class ApiError {
    private String message;
    private Instant timestamp;
}
