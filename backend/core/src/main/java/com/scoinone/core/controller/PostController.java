package com.scoinone.core.controller;

import com.scoinone.core.auth.LoginUser;
import com.scoinone.core.common.PostType;
import com.scoinone.core.dto.common.DeleteResponseDto;
import com.scoinone.core.dto.request.post.CreatePostRequestDto;
import com.scoinone.core.dto.request.post.UpdatePostRequestDto;
import com.scoinone.core.dto.response.post.CreatePostResponseDto;
import com.scoinone.core.dto.response.post.GetPostResponseDto;
import com.scoinone.core.dto.response.post.GetPostsResponseDto;
import com.scoinone.core.dto.response.post.UpdatePostResponseDto;
import com.scoinone.core.entity.Post;
import com.scoinone.core.entity.User;
import com.scoinone.core.mapper.PostMapper;
import com.scoinone.core.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/post")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @PostMapping("/{postType}")
    public ResponseEntity<CreatePostResponseDto> createPost(
            @Valid @LoginUser User user,
            @PathVariable("postType") PostType postType,
            @RequestBody CreatePostRequestDto requestDto
    ) {
        Post post = postService.createPost(requestDto.getTitle(), requestDto.getContent(), user, postType);
        return new ResponseEntity<>(PostMapper.INSTANCE.postToCreatePostResponseDto(post), HttpStatus.CREATED);
    }

    @GetMapping("/{postType}")
    public ResponseEntity<GetPostsResponseDto> getPosts(
            Pageable pageable,
            @PathVariable("postType") PostType postType
    ) {
        Page<Post> posts = postService.getPosts(pageable, postType);
        return new ResponseEntity<>(PostMapper.INSTANCE.pageToGetPostListResponseDto(posts), HttpStatus.OK);
    }

    @GetMapping("/{postType}/{postId}")
    public ResponseEntity<GetPostResponseDto> getPost(
            @PathVariable("postType") PostType postType,
            @PathVariable("postId") Long postId
    ) {
        Post postById = postService.getPostById(postId);
        return new ResponseEntity<>(PostMapper.INSTANCE.postToGetPostResponseDto(postById), HttpStatus.OK);
    }

    @PatchMapping("/{postType}/{postId}")
    public ResponseEntity<UpdatePostResponseDto> updatePost(
            @PathVariable("postType") PostType postType,
            @PathVariable("postId") Long postId,
            @RequestBody UpdatePostRequestDto requestDto
    ) {
        Post post = postService.updatePost(postId, requestDto.getTitle(), requestDto.getContent());
        return new ResponseEntity<>(PostMapper.INSTANCE.postToUpdatePostResponseDto(post), HttpStatus.OK);
    }

    @DeleteMapping("/{postType}/{postId}")
    public ResponseEntity<DeleteResponseDto> deletePost(
            @PathVariable("postType") PostType postType,
            @PathVariable("postId") Long postId
    ) {
        String result = postService.deletePost(postId);
        DeleteResponseDto response = new DeleteResponseDto(result);
        return ResponseEntity.ok(response);
    }
}
