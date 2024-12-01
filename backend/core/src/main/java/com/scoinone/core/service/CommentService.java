package com.scoinone.core.service;

import com.scoinone.core.entity.Comment;
import com.scoinone.core.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CommentService {
    Page<Comment> getCommentsByPostId(Pageable pageable, Long postId);

    Comment getCommentById(Long id);

    Comment createComment(Long PostId, String content, User user);

    Comment updateComment(Long id, Long userId, String newContent);

    String deleteComment(Long id, Long userId);
}
