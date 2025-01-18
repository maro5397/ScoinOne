package com.scoinone.post.dto.request.comment;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateCommentRequestDto {
    private Long postId;
    private String content;
}