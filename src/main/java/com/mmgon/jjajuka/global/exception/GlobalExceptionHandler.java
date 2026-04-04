package com.mmgon.jjajuka.global.exception;

import com.mmgon.jjajuka.domain.notification.exception.NotificationException;
import com.mmgon.jjajuka.domain.swap.exception.SwapException;
import com.mmgon.jjajuka.domain.vacancy.exception.VacancyException;
import com.mmgon.jjajuka.global.dto.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.RestClientException;

import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(SwapException.class)
    public ResponseEntity<ErrorResponse> handleSwapException(SwapException e) {
        log.error("{} occurred: {}", e.getClass().getSimpleName(), e.getMessage());
        ErrorResponse response = ErrorResponse.of(
                e.getErrorCode().getCode(),
                e.getErrorCode().getMessage()
        );
        return ResponseEntity
                .status(e.getErrorCode().getHttpStatus())
                .body(response);
    }

    @ExceptionHandler(VacancyException.class)
    public ResponseEntity<ErrorResponse> handleVacancyException(VacancyException e) {
        log.error("{} occurred: {}", e.getClass().getSimpleName(), e.getMessage());
        ErrorResponse response = ErrorResponse.of(
                e.getErrorCode().getCode(),
                e.getErrorCode().getMessage()
        );
        return ResponseEntity
                .status(e.getErrorCode().getHttpStatus())
                .body(response);
    }

    @ExceptionHandler(NotificationException.class)
    public ResponseEntity<ErrorResponse> handleNotificationException(NotificationException e) {
        log.error("NotificationException occurred: {}", e.getMessage());
        ErrorResponse response = ErrorResponse.of(
                e.getErrorCode().getCode(),
                e.getErrorCode().getMessage()
        );
        return ResponseEntity
                .status(e.getErrorCode().getHttpStatus())
                .body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException e) {
        String errorMessage = e.getBindingResult().getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining(", "));

        log.error("Validation error: {}", errorMessage);

        ErrorResponse response = ErrorResponse.of("VALIDATION_ERROR", errorMessage);

        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException e) {
        log.error("IllegalArgumentException occurred: {}", e.getMessage(), e);
        ErrorResponse response = ErrorResponse.of("BAD_REQUEST", e.getMessage());
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ErrorResponse> handleIllegalStateException(IllegalStateException e) {
        log.error("IllegalStateException occurred: {}", e.getMessage(), e);
        ErrorResponse response = ErrorResponse.of("PROCESSING_ERROR", e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(response);
    }

    @ExceptionHandler(RestClientException.class)
    public ResponseEntity<ErrorResponse> handleRestClientException(RestClientException e) {
        log.error("RestClientException occurred: {}", e.getMessage(), e);
        ErrorResponse response = ErrorResponse.of(
                "AI_SERVER_ERROR",
                "AI 서버와 통신하는 중 오류가 발생했습니다. " + e.getMessage()
        );
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(response);
    }
}
