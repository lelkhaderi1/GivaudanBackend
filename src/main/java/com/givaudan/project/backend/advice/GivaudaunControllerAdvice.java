package com.givaudan.project.backend.advice;

import com.givaudan.project.backend.exception.NotFoundException;
import com.givaudan.project.backend.exception.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.StringJoiner;

/**
 * un Rest Controller Advice pour personaliser les errors
 */
@RestControllerAdvice
@Slf4j
public class GivaudaunControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public Object handleNotFound(NotFoundException ex) {
        log.error(ex.getMessage());
        return new HttpEntity<>(ex.getMessage());
    }

    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public Object handleBadResuest(ValidationException ex) {
        log.error(ex.getMessage());

        StringJoiner joiner = new StringJoiner("\n");
        joiner.add(ex.getMessage());
        ex.getErrors().getAllErrors().forEach(c -> joiner.add(c.getDefaultMessage()));
        log.error(ex.getMessage());

        return new HttpEntity<>(joiner.toString());

    }


}
