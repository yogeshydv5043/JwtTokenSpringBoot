package com.learn.jwt.security.exception;

import com.learn.jwt.security.dto.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorMessage> ResourceNotFoundExceptionHandler(ResourceNotFoundException ex) {
        ErrorMessage errorMessage = new ErrorMessage(LocalDateTime.now(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);

    }

    @ExceptionHandler(InvalidRefreshTokenException.class)
    public ResponseEntity<ErrorMessage> InvalidRefreshTokenExceptionHandler(InvalidRefreshTokenException ex) {
        ErrorMessage errorMessage = new ErrorMessage(LocalDateTime.now(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);

    }
}
