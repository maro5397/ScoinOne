package com.scoinone.post.controller;

import com.scoinone.post.common.type.PostType;
import com.scoinone.post.dto.common.DeleteResponseDto;
import com.scoinone.post.dto.request.post.CreatePostRequestDto;
import com.scoinone.post.dto.request.post.UpdatePostRequestDto;
import com.scoinone.post.dto.response.post.CreatePostResponseDto;
import com.scoinone.post.dto.response.post.GetPostResponseDto;
import com.scoinone.post.dto.response.post.GetPostsResponseDto;
import com.scoinone.post.dto.response.post.UpdatePostResponseDto;
import com.scoinone.post.entity.PostEntity;
import com.scoinone.post.mapper.PostMapper;
import com.scoinone.post.service.PostService;
import java.util.List;
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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/post")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @PostMapping("/{postType}")
    public ResponseEntity<CreatePostResponseDto> createPost(
            @PathVariable("postType") PostType postType,
            @RequestBody CreatePostRequestDto requestDto,
            @RequestHeader(value = "UserId") String userId
    ) {
        PostEntity post = postService.createPost(
                requestDto.getTitle(),
                requestDto.getContent(),
                userId,
                requestDto.getUsername(),
                postType
        );
        return new ResponseEntity<>(PostMapper.INSTANCE.postToCreatePostResponseDto(post), HttpStatus.CREATED);
    }

    @GetMapping("/{postType}")
    public ResponseEntity<GetPostsResponseDto> getPosts(
            @PathVariable("postType") PostType postType,
            Pageable pageable
    ) {
        Page<PostEntity> posts = postService.getPosts(pageable, postType);
        return new ResponseEntity<>(PostMapper.INSTANCE.pageToGetPostsResponseDto(posts), HttpStatus.OK);
    }

    @GetMapping("/{postType}/{postId}")
    public ResponseEntity<GetPostResponseDto> getPost(
            @PathVariable("postType") PostType postType,
            @PathVariable("postId") Long postId
    ) {
        PostEntity postById = postService.getPostById(postId);
        return new ResponseEntity<>(PostMapper.INSTANCE.postToGetPostResponseDto(postById), HttpStatus.OK);
    }

    @GetMapping("/question")
    public ResponseEntity<GetPostsResponseDto> getQuestionPosts(@RequestHeader(value = "UserId") String userId) {
        List<PostEntity> questionsByUserId = postService.getQuestionsByUserId(userId);
        return new ResponseEntity<>(
                PostMapper.INSTANCE.listToGetPostsResponseDto(questionsByUserId),
                HttpStatus.OK
        );
    }

    @PatchMapping("/{postType}/{postId}")
    public ResponseEntity<UpdatePostResponseDto> updatePost(
            @PathVariable("postType") PostType postType,
            @PathVariable("postId") Long postId,
            @RequestBody UpdatePostRequestDto requestDto,
            @RequestHeader(value = "UserId") String userId
    ) {
        PostEntity post = postService.updatePost(postId, userId, requestDto.getTitle(), requestDto.getContent());
        return new ResponseEntity<>(PostMapper.INSTANCE.postToUpdatePostResponseDto(post), HttpStatus.OK);
    }

    @DeleteMapping("/{postType}/{postId}")
    public ResponseEntity<DeleteResponseDto> deletePost(
            @PathVariable("postType") PostType postType,
            @PathVariable("postId") Long postId,
            @RequestHeader(value = "UserId") String userId
    ) {
        String result = postService.deletePost(postId, userId);
        DeleteResponseDto response = new DeleteResponseDto(result);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
