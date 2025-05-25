package com.boardv2.exception;

import com.boardv2.controller.api.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiResponse<?>> handleNotFound(NotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.failure(ex.getMessage()));
    }

    @ExceptionHandler(DeletedPostException.class)
    public ResponseEntity<ApiResponse<?>> handleDeletedPost(DeletedPostException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.failure(e.getMessage()));
    }

    /**
     * 서버사이드 DTO 검증 시 반환하는 값을 간단히 바꾸는 메서드
     * @param ex : MethodArgumentNotValidException
     * @return : 필드와 에러응답 메시지의 Map List를 포함한 ApiResponse
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<?>> handleValidationException(MethodArgumentNotValidException ex) {
        List<Map<String, String>> errors = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> Map.of(
                        "field", error.getField(),
                        "message", error.getDefaultMessage()
                ))
                .toList();

        ApiResponse<?> response = ApiResponse.failure("유효성 검사 실패", errors);
        return ResponseEntity.badRequest().body(response);
    }

}