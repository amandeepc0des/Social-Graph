package com.socialgraph.analyzer.exception;

import org.springframework.http.HttpStatus;

public class NotificationNotFoundException extends RuntimeException{

    HttpStatus status;
    public NotificationNotFoundException(String message, HttpStatus status)
    {
        super(message);
        this.status= status;
    }
}
