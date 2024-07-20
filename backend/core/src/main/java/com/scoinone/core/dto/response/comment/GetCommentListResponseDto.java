package com.scoinone.core.dto.response.comment;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GetCommentListResponseDto {
    private List<CommentDto> comments;

    @Getter
    @Setter
    public static class CommentDto {
        private String commentId;
        private String postId;
        private String content;
        private String author;
        private String createdAt;
        private String updatedAt;
    }
}


