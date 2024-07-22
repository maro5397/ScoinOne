package com.scoinone.core.dto.response.post;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreatePostResponseDto {
    private String postId;
    private String postType;
    private String title;
    private String content;
    private String author;
    private String createdAt;
    private String updatedAt;
}