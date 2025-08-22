package com.alexandre.estoque.web.api;

import org.springframework.http.*;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,Object>> handleValidation(MethodArgumentNotValidException ex){
        Map<String,Object> body = new HashMap<>();
        body.put("error","validation");
        body.put("fields", ex.getBindingResult().getFieldErrors()
            .stream().collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage, (a,b)->a)));
        return ResponseEntity.badRequest().body(body);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String,Object>> handleBusiness(IllegalArgumentException ex){
        return ResponseEntity.badRequest().body(Map.of("error","business","message",ex.getMessage()));
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<Map<String,Object>> handleNotFound(NoSuchElementException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error","not_found"));
    }
}
