package com.scoinone.post.mapper;

import com.scoinone.post.dto.common.PageInfoDto;
import com.scoinone.post.dto.response.post.CreatePostResponseDto;
import com.scoinone.post.dto.response.post.GetPostResponseDto;
import com.scoinone.post.dto.response.post.GetPostsResponseDto;
import com.scoinone.post.dto.response.post.GetUserQuestionsResponseDto;
import com.scoinone.post.dto.response.post.UpdatePostResponseDto;
import com.scoinone.post.entity.PostEntity;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring")
public interface PostMapper {
    PostMapper INSTANCE = Mappers.getMapper(PostMapper.class);

    @Mapping(source = "id", target = "postId")
    @Mapping(source = "postType", target = "postType")
    @Mapping(source = "title", target = "title")
    @Mapping(source = "content", target = "content")
    @Mapping(source = "username", target = "username")
    @Mapping(source = "createdAt", target = "createdAt", dateFormat = "yyyy-MM-dd'T'HH:mm:ss")
    @Mapping(source = "updatedAt", target = "updatedAt", dateFormat = "yyyy-MM-dd'T'HH:mm:ss")
    CreatePostResponseDto postToCreatePostResponseDto(PostEntity post);

    @Mapping(source = "id", target = "postId")
    @Mapping(source = "postType", target = "postType")
    @Mapping(source = "title", target = "title")
    @Mapping(source = "content", target = "content")
    @Mapping(source = "username", target = "username")
    @Mapping(source = "createdAt", target = "createdAt", dateFormat = "yyyy-MM-dd'T'HH:mm:ss")
    @Mapping(source = "updatedAt", target = "updatedAt", dateFormat = "yyyy-MM-dd'T'HH:mm:ss")
    GetPostResponseDto postToGetPostResponseDto(PostEntity post);

    @Mapping(source = "id", target = "postId")
    @Mapping(source = "postType", target = "postType")
    @Mapping(source = "title", target = "title")
    @Mapping(source = "content", target = "content")
    @Mapping(source = "username", target = "username")
    @Mapping(source = "createdAt", target = "createdAt", dateFormat = "yyyy-MM-dd'T'HH:mm:ss")
    @Mapping(source = "updatedAt", target = "updatedAt", dateFormat = "yyyy-MM-dd'T'HH:mm:ss")
    UpdatePostResponseDto postToUpdatePostResponseDto(PostEntity post);

    List<GetPostResponseDto> postsToGetPostsResponseDto(List<PostEntity> posts);

    default GetPostsResponseDto pageToGetPostsResponseDto(Page<PostEntity> page) {
        GetPostsResponseDto responseDto = new GetPostsResponseDto();
        responseDto.setPosts(postsToGetPostsResponseDto(page.getContent()));
        responseDto.setPageInfo(
                PageInfoDto.builder()
                        .totalElements(page.getTotalElements())
                        .totalPages(page.getTotalPages())
                        .build()
        );
        return responseDto;
    }

    default GetUserQuestionsResponseDto listToGetPostsResponseDto(List<PostEntity> posts) {
        GetUserQuestionsResponseDto responseDto = new GetUserQuestionsResponseDto();
        responseDto.setPosts(postsToGetPostsResponseDto(posts));
        return responseDto;
    }
}
