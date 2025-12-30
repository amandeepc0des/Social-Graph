package com.socialgraph.analyzer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PostNotFoundDto {
    String status;
    String message;
}
