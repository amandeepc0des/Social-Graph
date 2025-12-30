package com.socialgraph.analyzer.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class PostNotFoundException extends RuntimeException{

    HttpStatus status;
    public PostNotFoundException(String message, HttpStatus status){
        super(message);
        this.status = status;
    }
}

