package com.scoinone.core.dto.response.board;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreatePostResponseDto {
    private String postId;
    private String boardType;
    private String title;
    private String content;
    private String author;
    private String createdAt;
    private String updatedAt;
}