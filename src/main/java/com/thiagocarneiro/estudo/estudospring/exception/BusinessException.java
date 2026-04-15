package com.thiagocarneiro.estudo.estudospring.exception;

public class BusinessException extends RuntimeException {
    public BusinessException(String message) {
        super(message);
    }
}
