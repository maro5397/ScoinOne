package com.scoinone.core.dto.response.post;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class GetPostListResponseDto {
    private List<PostDto> posts;

    @Getter
    @Setter
    public static class PostDto {
        private String postId;
        private String postType;
        private String title;
        private String author;
        private String createdAt;
        private String updatedAt;
    }
}

