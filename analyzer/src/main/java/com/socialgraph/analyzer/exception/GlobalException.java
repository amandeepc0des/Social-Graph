package com.socialgraph.analyzer.exception;

import com.socialgraph.analyzer.dto.PostNotFoundDto;
import com.socialgraph.analyzer.dto.UserNotFoundDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalException {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<UserNotFoundDto> resolveUserNotFoundException(UserNotFoundException ex)
    {
        return ResponseEntity.status(ex.status).body(new UserNotFoundDto(ex.status.toString(), ex.getMessage()));
    }

    @ExceptionHandler(PostNotFoundException.class)
    public ResponseEntity<PostNotFoundDto> resolvePostNotFoundException(PostNotFoundException ex)
    {
        return ResponseEntity.status(ex.status).body(new PostNotFoundDto(ex.status.toString(), ex.getMessage()));
    }
    
    @ExceptionHandler(DuplicateFriendRequestException.class)
    public ResponseEntity<String> resolveDuplicateFriendRequestException(DuplicateFriendRequestException ex)
    {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }   

    @ExceptionHandler(CannotFriendYourselfException.class)
    public ResponseEntity<String> resolveCannotFriendYourselfException(CannotFriendYourselfException ex)
    {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(FriendshipNotFoundException.class)
    public ResponseEntity<String> resolveFriendshipNotFoundException(FriendshipNotFoundException ex)
    {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}
