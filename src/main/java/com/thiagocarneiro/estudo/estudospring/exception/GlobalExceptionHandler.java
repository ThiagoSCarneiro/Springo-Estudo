package com.thiagocarneiro.estudo.estudospring.exception;

import com.thiagocarneiro.vendacerta.vendacerta.exception.ValidationError;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ApiError> handleNotFound(EntityNotFoundException ex, HttpServletRequest request) {
        var error = new ApiError(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                "Resource not found",
                ex.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);

    }

    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_CONTENT)
    public ResponseEntity<ApiError> handleBusiness(BusinessException ex, HttpServletRequest request) {
        var error = new ApiError(
                LocalDateTime.now(),
                HttpStatus.UNPROCESSABLE_CONTENT.value(),
                "Business rule violation",
                ex.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_CONTENT).body(error);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<ApiError> handleDataIntegrity(DataIntegrityViolationException ex, HttpServletRequest request) {
        var error = new ApiError(
                LocalDateTime.now(),
                HttpStatus.CONFLICT.value(),
                "Data violation",
                ex.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    @ExceptionHandler(GenericException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ApiError> handleGeneric(GenericException ex, HttpServletRequest request) {
        var error = new ApiError(
                LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Error Server, Try again",
                ex.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<List<ValidationError>> handleValidation(MethodArgumentNotValidException ex, HttpServletRequest request) {
        List<ValidationError> errors = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(f -> new ValidationError(f.getField(), f.getDefaultMessage()))
                .toList();
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseEntity<ApiError> handleAccesDeniedException(AccessDeniedException ex, HttpServletRequest request) {
       var error = new ApiError(
            LocalDateTime.now(),
            HttpStatus.FORBIDDEN.value(),
            "Acces denied",
               ex.getMessage(),
               request.getRequestURI()
       );
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
    }


}
