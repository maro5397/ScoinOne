package com.scoinone.core.mapper;

import com.scoinone.core.dto.common.PageInfoDto;
import com.scoinone.core.dto.response.comment.*;
import com.scoinone.core.entity.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    CommentMapper INSTANCE = Mappers.getMapper(CommentMapper.class);

    @Mapping(source = "id", target = "commentId")
    @Mapping(source = "post.id", target = "postId")
    @Mapping(source = "content", target = "content")
    @Mapping(source = "user.username", target = "author")
    @Mapping(source = "createdAt", target = "createdAt", dateFormat = "yyyy-MM-dd'T'HH:mm:ss")
    @Mapping(source = "createdAt", target = "updatedAt", dateFormat = "yyyy-MM-dd'T'HH:mm:ss")
    CreateCommentResponseDto commentToCreateCommentResponseDto(Comment comment);

    @Mapping(source = "id", target = "commentId")
    @Mapping(source = "post.id", target = "postId")
    @Mapping(source = "content", target = "content")
    @Mapping(source = "user.username", target = "author")
    @Mapping(source = "createdAt", target = "createdAt", dateFormat = "yyyy-MM-dd'T'HH:mm:ss")
    @Mapping(source = "createdAt", target = "updatedAt", dateFormat = "yyyy-MM-dd'T'HH:mm:ss")
    GetCommentResponseDto commentToGetCommentResponseDto(Comment comment);

    @Mapping(source = "id", target = "commentId")
    @Mapping(source = "post.id", target = "postId")
    @Mapping(source = "content", target = "content")
    @Mapping(source = "user.username", target = "author")
    @Mapping(source = "createdAt", target = "createdAt", dateFormat = "yyyy-MM-dd'T'HH:mm:ss")
    @Mapping(source = "createdAt", target = "updatedAt", dateFormat = "yyyy-MM-dd'T'HH:mm:ss")
    UpdateCommentResponseDto commentToUpdateCommentResponseDto(Comment comment);

    List<GetCommentResponseDto> commentsToGetCommentsResponseDto(List<Comment> comments);

    default GetCommentListResponseDto pageToGetCommentListResponseDto(Page<Comment> page) {
        GetCommentListResponseDto responseDto = new GetCommentListResponseDto();
        responseDto.setComments(commentsToGetCommentsResponseDto(page.getContent()));
        responseDto.setPageInfo(PageInfoDto.builder()
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .build());

        return responseDto;
    }
}
