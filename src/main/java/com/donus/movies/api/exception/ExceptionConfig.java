package com.donus.movies.api.exception;

import com.donus.movies.model.exception.AlreadyExistsException;
import com.donus.movies.model.exception.CreationObjectNotFoundException;
import com.donus.movies.model.exception.ObjectNotFoundException;
import com.donus.movies.model.exception.ValidationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ExceptionConfig extends ResponseEntityExceptionHandler {
    @ExceptionHandler({
        AlreadyExistsException.class,
        CreationObjectNotFoundException.class,
        ValidationException.class
    })
    public ResponseEntity badRequest(Exception ex) {
        return ResponseEntity.badRequest().body(new ErrorObject(ex.getMessage()));
    }

    @ExceptionHandler({
        ObjectNotFoundException.class
    })
    public ResponseEntity resourceNotFound(Exception ex) {
        return ResponseEntity.notFound().build();
    }
}