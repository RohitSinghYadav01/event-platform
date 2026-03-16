package com.event.eventservice.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(OrganizerNotFoundException.class)
    public ResponseEntity<String> handleOrganizerNotFoundException(OrganizerNotFoundException ex){
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}
