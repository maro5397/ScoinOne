package com.scoinone.post.dto.response.comment;

import com.scoinone.post.dto.common.PageInfoDto;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetCommentsResponseDto {
    private PageInfoDto pageInfo;
    private List<GetCommentResponseDto> comments;
}
