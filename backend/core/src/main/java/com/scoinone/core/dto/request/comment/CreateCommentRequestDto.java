package com.scoinone.core.dto.request.comment;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateCommentRequestDto {
    private Long postId;
    private String content;
    private String author;
}