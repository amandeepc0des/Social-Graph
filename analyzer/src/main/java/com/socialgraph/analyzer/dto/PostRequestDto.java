package com.socialgraph.analyzer.dto;

import com.socialgraph.analyzer.entity.Privacy;
import lombok.Data;

@Data
public class PostRequestDto {
    String content;
    String imageUrl;
    Privacy privacy;
    Long userId;
}
