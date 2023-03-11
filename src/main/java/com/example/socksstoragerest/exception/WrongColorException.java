package com.example.socksstoragerest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class WrongColorException extends RuntimeException {
    public WrongColorException() {
        super("Wrong color entered");
    }
}
