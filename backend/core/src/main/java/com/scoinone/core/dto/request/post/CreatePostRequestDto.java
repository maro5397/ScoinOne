package com.scoinone.core.dto.request.board;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreatePostRequestDto {
    private String title;
    private String content;
    private String author;
}