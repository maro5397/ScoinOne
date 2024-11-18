package com.scoinone.core.dto.response.comment;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetCommentResponseDto {
    private Long commentId;
    private Long postId;
    private String content;
    private String author;
    private String createdAt;
    private String updatedAt;
}
