package com.example.demo.Exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<SimpleResponse> handleException(CustomBaseException exception){
        return ResponseEntity.status(exception.getHttpStatus()).body(exception.getSimpleResponse());
    }
}
