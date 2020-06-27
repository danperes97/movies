package com.donus.movies.model.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class CreationObjectNotFoundException extends RuntimeException {
    public CreationObjectNotFoundException(String message) { super(message); }
}
