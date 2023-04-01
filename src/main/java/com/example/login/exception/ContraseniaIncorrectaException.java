package com.example.login.exception;

public class ContraseniaIncorrectaException extends RuntimeException {
    public ContraseniaIncorrectaException(String mensaje) {
        super(mensaje);
    }
}