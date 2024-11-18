package com.carlosjpa.libraryapi.exceptions;

public class LivroNaoSalvoException extends RuntimeException {
    public LivroNaoSalvoException(String message) {
        super(message);
    }
}
