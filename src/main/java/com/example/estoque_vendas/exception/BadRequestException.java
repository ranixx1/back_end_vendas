package com.example.estoque_vendas.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST) // Mapeia esta exceção para um status HTTP 400 (Bad Request)
public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {
        super(message);
    }
}