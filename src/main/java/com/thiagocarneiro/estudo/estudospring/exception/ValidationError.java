package com.thiagocarneiro.vendacerta.vendacerta.exception;

public record ValidationError(
        String field, String message
) {
}
