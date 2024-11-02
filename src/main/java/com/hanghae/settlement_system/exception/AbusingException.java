package com.hanghae.settlement_system.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class AbusingException extends RuntimeException {

    public AbusingException(String message) {
        super(message);
    }
}

