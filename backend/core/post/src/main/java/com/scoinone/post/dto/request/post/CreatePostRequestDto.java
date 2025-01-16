package com.scoinone.post.dto.request.post;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreatePostRequestDto {
    private String title;
    private String content;
    private String username;
}