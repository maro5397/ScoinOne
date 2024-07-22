package com.scoinone.core.service;

import com.scoinone.core.entity.Comment;

import java.util.List;

public interface CommentService {
    List<Comment> getComments();

    Comment getCommentById(Long id);

    Comment createComment(Comment comment);

    Comment updateComment(Long id, Comment updatedComment);

    void deleteComment(Long id);
}
