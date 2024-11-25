package com.scoinone.core.controller;

import com.scoinone.core.common.PostType;
import com.scoinone.core.dto.request.post.CreatePostRequestDto;
import com.scoinone.core.dto.response.post.CreatePostResponseDto;
import com.scoinone.core.mapper.PostMapper;
import com.scoinone.core.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

//    @PostMapping("/{postType}")
//    public ResponseEntity<CreatePostResponseDto> createPost(
//            @PathVariable("postType") PostType postType,
//            @RequestBody CreatePostRequestDto requestDto
//    ) {
//        postService.createPost(requestDto, postType);
//        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
//    }
}
