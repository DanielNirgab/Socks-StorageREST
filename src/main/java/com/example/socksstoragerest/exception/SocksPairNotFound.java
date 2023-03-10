package com.example.socksstoragerest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class SocksPairNotFound extends RuntimeException {
    public SocksPairNotFound() {
        super("Pair not found, not enough items");
    }
}
