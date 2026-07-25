package com.example.pulselist.config;

import com.example.pulselist.errors.APIError;
import com.example.pulselist.exceptions.AccountAlreadyExistsException;
import com.example.pulselist.exceptions.InvalidFirebaseTokenException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidFirebaseTokenException.class)
    public ResponseEntity<APIError> handleInvalidToken(InvalidFirebaseTokenException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new APIError("INVALID_TOKEN", ex.getMessage()));
    }

    @ExceptionHandler(AccountAlreadyExistsException.class)
    public ResponseEntity<APIError> handleDuplicateAccount(AccountAlreadyExistsException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new APIError("ACCOUNT_EXISTS", ex.getMessage()));
    }
}