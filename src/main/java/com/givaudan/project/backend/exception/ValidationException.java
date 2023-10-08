package com.givaudan.project.backend.exception;

import lombok.Data;
import org.springframework.validation.Errors;

@Data
public class ValidationException extends RuntimeException {
    private Errors errors;

    public ValidationException(String message, Errors errors) {
        super(message);
        this.errors = errors;
    }
}