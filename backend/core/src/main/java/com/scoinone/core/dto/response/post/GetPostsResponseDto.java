package com.scoinone.core.dto.response.post;

import com.scoinone.core.dto.common.PageInfoDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GetPostsResponseDto {
    private PageInfoDto pageInfo;
    private List<GetPostResponseDto> posts;
}
