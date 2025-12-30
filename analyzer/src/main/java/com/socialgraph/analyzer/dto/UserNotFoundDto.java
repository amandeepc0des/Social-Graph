package com.socialgraph.analyzer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
public class UserNotFoundDto {
    String message;
    String status;
}
