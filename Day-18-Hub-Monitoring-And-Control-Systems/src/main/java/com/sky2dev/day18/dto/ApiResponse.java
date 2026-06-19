package com.sky2dev.day18.dto;

public record ApiResponse<T>(String marker, String message, T data) {
}
