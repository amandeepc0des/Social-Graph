package com.socialgraph.analyzer.exception;

public class CannotFriendYourselfException extends RuntimeException {
    public CannotFriendYourselfException() {
        super("A user cannot friend themselves.");
    }
}
