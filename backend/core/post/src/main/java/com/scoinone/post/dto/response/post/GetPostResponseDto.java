package com.scoinone.post.dto.response.post;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.scoinone.post.common.type.PostType;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetPostResponseDto {
    private Long postId;
    private PostType postType;
    private String title;
    private String content;
    private String author;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;
}