package com.scoinone.post.dto.response.post;

import com.scoinone.post.dto.common.PageInfoDto;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetPostsResponseDto {
    private PageInfoDto pageInfo;
    private List<GetPostResponseDto> posts;
}
