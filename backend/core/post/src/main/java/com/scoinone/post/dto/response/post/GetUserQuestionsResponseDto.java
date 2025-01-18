package com.scoinone.post.dto.response.post;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetUserQuestionsResponseDto {
    private List<GetPostResponseDto> posts;
}
