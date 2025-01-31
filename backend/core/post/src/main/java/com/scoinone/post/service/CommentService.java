package com.scoinone.post.service;

import com.scoinone.post.entity.CommentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommentService {
    Page<CommentEntity> getCommentsByPostId(Pageable pageable, Long postId);

    CommentEntity getCommentById(Long id);

    CommentEntity createComment(Long postId, String content, String userId, String username);

    CommentEntity updateComment(Long id, String userId, String newContent);

    String deleteComment(Long id, String userId);

    String deleteAllComment(String userId);
}
