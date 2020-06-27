package com.donus.movies.model.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class AlreadyExistsException extends RuntimeException {
    public AlreadyExistsException(String message) { super(message); }
}

