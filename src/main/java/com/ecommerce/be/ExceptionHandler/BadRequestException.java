package com.ecommerce.be.ExceptionHandler;

import java.util.List;

import org.springframework.validation.ObjectError;

public class BadRequestException extends RuntimeException {

    private List<ObjectError> error;

    public BadRequestException() {
        super("Bad Request");
    }

    public BadRequestException(List<ObjectError> error) {
        super("Bad request");
        this.error = error;
    }

    public List<ObjectError> getAllErrors() {
        return error;
    }
}