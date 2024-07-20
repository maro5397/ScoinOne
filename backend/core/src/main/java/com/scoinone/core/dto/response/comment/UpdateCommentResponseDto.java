package com.scoinone.core.dto.response.comment;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateCommentResponseDto {
    private String commentId;
    private String postId;
    private String content;
    private String author;
    private String createdAt;
    private String updatedAt;
}