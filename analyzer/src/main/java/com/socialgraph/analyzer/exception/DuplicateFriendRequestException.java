package com.socialgraph.analyzer.exception;

public class DuplicateFriendRequestException extends RuntimeException {
    public DuplicateFriendRequestException() {
        super("A friend request between these users already exists.");
    }
}
