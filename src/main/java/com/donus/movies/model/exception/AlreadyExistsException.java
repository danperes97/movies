package com.donus.movies.model.exception;

public class AlreadyExistsException extends RuntimeException {
    public AlreadyExistsException(String message) { super(message); }
}

