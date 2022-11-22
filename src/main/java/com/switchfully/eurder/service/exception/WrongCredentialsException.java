package com.switchfully.eurder.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class WrongCredentialsException extends RuntimeException {
    public WrongCredentialsException() {
        super("The username and/or password don't match. Please try again.");
    }
}
