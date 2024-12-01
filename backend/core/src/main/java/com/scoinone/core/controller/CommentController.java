package com.scoinone.core.controller;

import com.scoinone.core.common.annotation.LoginUser;
import com.scoinone.core.dto.common.DeleteResponseDto;
import com.scoinone.core.dto.request.comment.CreateCommentRequestDto;
import com.scoinone.core.dto.request.comment.UpdateCommentRequestDto;
import com.scoinone.core.dto.response.comment.CreateCommentResponseDto;
import com.scoinone.core.dto.response.comment.GetCommentsResponseDto;
import com.scoinone.core.dto.response.comment.UpdateCommentResponseDto;
import com.scoinone.core.entity.Comment;
import com.scoinone.core.entity.User;
import com.scoinone.core.mapper.CommentMapper;
import com.scoinone.core.service.CommentService;
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
@RequestMapping("/api/comment")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<CreateCommentResponseDto> createComment(
            @RequestBody CreateCommentRequestDto requestDto,
            @Valid @LoginUser User user
    ) {
        Comment comment = commentService.createComment(requestDto.getPostId(), requestDto.getContent(), user);
        return new ResponseEntity<>(
                CommentMapper.INSTANCE.commentToCreateCommentResponseDto(comment),
                HttpStatus.CREATED
        );
    }

    @GetMapping("/{postId}")
    public ResponseEntity<GetCommentsResponseDto> getComments(
            @PathVariable("postId") Long postId,
            Pageable pageable
    ) {
        Page<Comment> commentsByPostId = commentService.getCommentsByPostId(pageable, postId);
        return new ResponseEntity<>(
                CommentMapper.INSTANCE.pageToGetCommentsResponseDto(commentsByPostId),
                HttpStatus.OK
        );
    }

    @PatchMapping("/{commentId}")
    public ResponseEntity<UpdateCommentResponseDto> updateComment(
            @PathVariable("commentId") Long commentId,
            @RequestBody UpdateCommentRequestDto requestDto,
            @Valid @LoginUser User user
    ) {
        Comment comment = commentService.updateComment(commentId, user.getId(), requestDto.getContent());
        return new ResponseEntity<>(CommentMapper.INSTANCE.commentToUpdateCommentResponseDto(comment), HttpStatus.OK);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<DeleteResponseDto> deleteComment(
            @PathVariable("commentId") Long commentId,
            @Valid @LoginUser User user
    ) {
        String result = commentService.deleteComment(commentId, user.getId());
        DeleteResponseDto response = new DeleteResponseDto(result);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
