package com.sky2dev.day17.dto;

public record ApiResponse<T>(String marker, String message, T data) {
}
