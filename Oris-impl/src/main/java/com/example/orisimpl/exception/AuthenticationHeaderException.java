package com.example.orisimpl.exception;

import org.springframework.http.HttpStatus;

public class AuthenticationHeaderException extends ServiceException{

    public AuthenticationHeaderException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
