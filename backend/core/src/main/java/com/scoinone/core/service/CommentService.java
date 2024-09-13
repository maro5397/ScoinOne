package com.scoinone.core.service;

import com.scoinone.core.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CommentService {
    Page<Comment> getCommentsByPostId(Pageable pageable, Long postId);

    Comment getCommentById(Long id);

    Comment createComment(Comment comment);

    Comment updateComment(Long id, Comment updatedComment);

    void deleteComment(Long id);
}
