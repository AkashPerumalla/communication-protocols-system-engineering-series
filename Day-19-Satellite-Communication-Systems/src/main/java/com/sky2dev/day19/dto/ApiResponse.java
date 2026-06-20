package com.sky2dev.day19.dto;

public record ApiResponse<T>(String marker, String message, T data) {
}
