package com.scoinone.post.controller;

import com.scoinone.post.dto.common.DeleteResponseDto;
import com.scoinone.post.dto.request.comment.CreateCommentRequestDto;
import com.scoinone.post.dto.request.comment.UpdateCommentRequestDto;
import com.scoinone.post.dto.response.comment.CreateCommentResponseDto;
import com.scoinone.post.dto.response.comment.GetCommentsResponseDto;
import com.scoinone.post.dto.response.comment.UpdateCommentResponseDto;
import com.scoinone.post.entity.CommentEntity;
import com.scoinone.post.mapper.CommentMapper;
import com.scoinone.post.service.CommentService;
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
@RequestMapping("/api/comment")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<CreateCommentResponseDto> createComment(
            @RequestBody CreateCommentRequestDto requestDto,
            @RequestHeader(value = "UserId") String userId,
            @RequestHeader(value = "Username") String username
    ) {
        CommentEntity comment = commentService.createComment(
                requestDto.getPostId(),
                requestDto.getContent(),
                userId,
                username
        );
        return new ResponseEntity<>(
                CommentMapper.INSTANCE.commentToCreateCommentResponseDto(comment),
                HttpStatus.CREATED
        );
    }

    @GetMapping("/{postId}")
    public ResponseEntity<GetCommentsResponseDto> getComments(@PathVariable("postId") Long postId, Pageable pageable) {
        Page<CommentEntity> commentsByPostId = commentService.getCommentsByPostId(pageable, postId);
        return new ResponseEntity<>(
                CommentMapper.INSTANCE.pageToGetCommentsResponseDto(commentsByPostId),
                HttpStatus.OK
        );
    }

    @PatchMapping("/{commentId}")
    public ResponseEntity<UpdateCommentResponseDto> updateComment(
            @PathVariable("commentId") Long commentId,
            @RequestBody UpdateCommentRequestDto requestDto,
            @RequestHeader(value = "UserId") String userId
    ) {
        CommentEntity comment = commentService.updateComment(commentId, userId, requestDto.getContent());
        return new ResponseEntity<>(CommentMapper.INSTANCE.commentToUpdateCommentResponseDto(comment), HttpStatus.OK);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<DeleteResponseDto> deleteComment(
            @PathVariable("commentId") Long commentId,
            @RequestHeader(value = "UserId") String userId
    ) {
        String result = commentService.deleteComment(commentId, userId);
        DeleteResponseDto response = new DeleteResponseDto(result);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
