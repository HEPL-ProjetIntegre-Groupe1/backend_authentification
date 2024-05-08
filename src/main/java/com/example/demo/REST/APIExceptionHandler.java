package com.example.demo.REST;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@ControllerAdvice
public class APIExceptionHandler {
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<String> handleException(Exception e) throws NoResourceFoundException {
        if(e instanceof NoResourceFoundException)
            throw (NoResourceFoundException) e;
        return new ResponseEntity<>("This service is currently unavailable. Please try again later", org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
