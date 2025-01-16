package com.scoinone.post.mapper;

import com.scoinone.post.dto.common.PageInfoDto;
import com.scoinone.post.dto.response.comment.CreateCommentResponseDto;
import com.scoinone.post.dto.response.comment.GetCommentResponseDto;
import com.scoinone.post.dto.response.comment.GetCommentsResponseDto;
import com.scoinone.post.dto.response.comment.UpdateCommentResponseDto;
import com.scoinone.post.entity.CommentEntity;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    CommentMapper INSTANCE = Mappers.getMapper(CommentMapper.class);

    @Mapping(source = "id", target = "commentId")
    @Mapping(source = "post.id", target = "postId")
    @Mapping(source = "content", target = "content")
    @Mapping(source = "username", target = "author")
    @Mapping(source = "createdAt", target = "createdAt", dateFormat = "yyyy-MM-dd'T'HH:mm:ss")
    @Mapping(source = "createdAt", target = "updatedAt", dateFormat = "yyyy-MM-dd'T'HH:mm:ss")
    CreateCommentResponseDto commentToCreateCommentResponseDto(CommentEntity comment);

    @Mapping(source = "id", target = "commentId")
    @Mapping(source = "post.id", target = "postId")
    @Mapping(source = "content", target = "content")
    @Mapping(source = "username", target = "author")
    @Mapping(source = "createdAt", target = "createdAt", dateFormat = "yyyy-MM-dd'T'HH:mm:ss")
    @Mapping(source = "createdAt", target = "updatedAt", dateFormat = "yyyy-MM-dd'T'HH:mm:ss")
    GetCommentResponseDto commentToGetCommentResponseDto(CommentEntity comment);

    @Mapping(source = "id", target = "commentId")
    @Mapping(source = "post.id", target = "postId")
    @Mapping(source = "content", target = "content")
    @Mapping(source = "username", target = "author")
    @Mapping(source = "createdAt", target = "createdAt", dateFormat = "yyyy-MM-dd'T'HH:mm:ss")
    @Mapping(source = "createdAt", target = "updatedAt", dateFormat = "yyyy-MM-dd'T'HH:mm:ss")
    UpdateCommentResponseDto commentToUpdateCommentResponseDto(CommentEntity comment);

    List<GetCommentResponseDto> commentsToGetCommentsResponseDto(List<CommentEntity> comments);

    default GetCommentsResponseDto pageToGetCommentsResponseDto(Page<CommentEntity> page) {
        GetCommentsResponseDto responseDto = new GetCommentsResponseDto();
        responseDto.setComments(commentsToGetCommentsResponseDto(page.getContent()));
        responseDto.setPageInfo(PageInfoDto.builder()
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .build());

        return responseDto;
    }
}
