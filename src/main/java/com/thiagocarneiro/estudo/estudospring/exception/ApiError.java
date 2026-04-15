package com.thiagocarneiro.vendacerta.vendacerta.exception;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record ApiError(
        @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
        LocalDateTime timestamp,
        Integer status,
        String error,
        String message,
        String path
) {
}
