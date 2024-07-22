package com.scoinone.core.dto.request.post;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdatePostRequestDto {
    private String title;
    private String content;
}