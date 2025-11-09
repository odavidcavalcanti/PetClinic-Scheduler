package com.app.petclinic_scheduler.exception;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public record ExceptionResponseDTO(
        LocalDateTime timestamp,
        Integer status,
        String error,
        String message,
        String path) {
    public static ExceptionResponseDTO of (HttpStatus status, String message, String path) {
        return new ExceptionResponseDTO(
                LocalDateTime.now(),
                status.value(),
                status.getReasonPhrase(),
                message,
                path);
    }
}
