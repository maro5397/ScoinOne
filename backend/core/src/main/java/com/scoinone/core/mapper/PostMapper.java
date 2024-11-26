package com.scoinone.core.mapper;

import com.scoinone.core.dto.common.PageInfoDto;
import com.scoinone.core.dto.response.post.*;
import com.scoinone.core.entity.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PostMapper {
    PostMapper INSTANCE = Mappers.getMapper(PostMapper.class);

    @Mapping(source = "id", target = "postId")
    @Mapping(source = "postType", target = "postType")
    @Mapping(source = "title", target = "title")
    @Mapping(source = "content", target = "content")
    @Mapping(source = "user.username", target = "author")
    @Mapping(source = "createdAt", target = "createdAt", dateFormat = "yyyy-MM-dd'T'HH:mm:ss")
    @Mapping(source = "updatedAt", target = "updatedAt", dateFormat = "yyyy-MM-dd'T'HH:mm:ss")
    CreatePostResponseDto postToCreatePostResponseDto(Post post);

    @Mapping(source = "id", target = "postId")
    @Mapping(source = "postType", target = "postType")
    @Mapping(source = "title", target = "title")
    @Mapping(source = "content", target = "content")
    @Mapping(source = "user.username", target = "author")
    @Mapping(source = "createdAt", target = "createdAt", dateFormat = "yyyy-MM-dd'T'HH:mm:ss")
    @Mapping(source = "updatedAt", target = "updatedAt", dateFormat = "yyyy-MM-dd'T'HH:mm:ss")
    GetPostResponseDto postToGetPostResponseDto(Post post);

    @Mapping(source = "id", target = "postId")
    @Mapping(source = "postType", target = "postType")
    @Mapping(source = "title", target = "title")
    @Mapping(source = "content", target = "content")
    @Mapping(source = "user.username", target = "author")
    @Mapping(source = "createdAt", target = "createdAt", dateFormat = "yyyy-MM-dd'T'HH:mm:ss")
    @Mapping(source = "updatedAt", target = "updatedAt", dateFormat = "yyyy-MM-dd'T'HH:mm:ss")
    UpdatePostResponseDto postToUpdatePostResponseDto(Post post);

    List<GetPostResponseDto> postsToGetPostsResponseDto(List<Post> posts);

    default GetPostsResponseDto pageToGetPostListResponseDto(Page<Post> page) {
        GetPostsResponseDto responseDto = new GetPostsResponseDto();
        responseDto.setPosts(postsToGetPostsResponseDto(page.getContent()));
        responseDto.setPageInfo(PageInfoDto.builder()
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .build());

        return responseDto;
    }

    default GetPostsResponseDto listToGetPostListResponseDto(List<Post> posts) {
        GetPostsResponseDto responseDto = new GetPostsResponseDto();
        responseDto.setPosts(postsToGetPostsResponseDto(posts));
        return responseDto;
    }
}
