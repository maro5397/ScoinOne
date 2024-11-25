package com.scoinone.core.dto.response.comment;

import com.scoinone.core.dto.common.PageInfoDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GetCommentsResponseDto {
    private PageInfoDto pageInfo;
    private List<GetCommentResponseDto> comments;
}
