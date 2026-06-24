package com.sky2dev.day23.dto;

import java.time.Instant;

public record ApiResponse<T>(
        boolean success,
        String marker,
        String message,
        T data,
        Instant timestamp
) {
    public static <T> ApiResponse<T> success(String marker, String message, T data) {
        return new ApiResponse<>(true, marker, message, data, Instant.now());
    }

    public static <T> ApiResponse<T> failure(String marker, String message, T data) {
        return new ApiResponse<>(false, marker, message, data, Instant.now());
    }
}
