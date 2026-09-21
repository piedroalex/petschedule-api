package br.com.palm.petschedule_api.exception;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleNotFound(ResourceNotFoundException ex, HttpServletRequest req) {
        Map<String, Object> err = new HashMap<>();
        err.put("timestamp", Instant.now());
        err.put("status", HttpStatus.NOT_FOUND.value());
        err.put("error", "Recurso não encontrado");
        err.put("message", ex.getMessage());
        err.put("path", req.getRequestURI());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
    }
    
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleValidation(IllegalArgumentException ex, HttpServletRequest req) {
        Map<String, Object> err = new HashMap<>();
        err.put("timestamp", Instant.now());
        err.put("status", HttpStatus.BAD_REQUEST.value());
        err.put("error", "Requisição Inválida");
        err.put("message", ex.getMessage());
        err.put("path", req.getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidation(MethodArgumentNotValidException ex, HttpServletRequest req) {
        Map<String, Object> err = new HashMap<>();
        Map<String, String> fieldErrors = new HashMap<>();
        for (FieldError f : ex.getBindingResult().getFieldErrors()) {
            fieldErrors.put(f.getField(), f.getDefaultMessage());
        }
        err.put("timestamp", Instant.now());
        err.put("status", HttpStatus.BAD_REQUEST.value());
        err.put("error", "Erro de validação");
        err.put("fields", fieldErrors);
        err.put("path", req.getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }
}