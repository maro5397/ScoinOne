package com.scoinone.post.service.impl;

import com.scoinone.post.entity.CommentEntity;
import com.scoinone.post.entity.PostEntity;
import com.scoinone.post.repository.CommentRepository;
import com.scoinone.post.repository.PostRepository;
import com.scoinone.post.service.CommentService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentServiceImpl implements CommentService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    @Override
    public Page<CommentEntity> getCommentsByPostId(Long id, Pageable pageable) {
        return commentRepository.findByPost_Id(pageable, id);
    }

    @Override
    public CommentEntity getCommentById(Long id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Comment not found with id: " + id));
    }

    @Override
    public CommentEntity createComment(Long postId, String content, String userId, String username) {
        PostEntity post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("Post not found with id: " + postId));
        CommentEntity comment = CommentEntity.builder()
                .post(post)
                .userId(userId)
                .username(username)
                .content(content)
                .build();
        return commentRepository.save(comment);
    }

    @Override
    public CommentEntity updateComment(Long id, String userId, String newContent) {
        CommentEntity existedComment = commentRepository.findByIdAndUserId(id, userId).orElseThrow(
                () -> new EntityNotFoundException("Comment not found with id: " + id + ", userId: " + userId));
        existedComment.setContent(newContent);
        return existedComment;
    }

    @Override
    public String deleteComment(Long id, String userId) {
        Long count = commentRepository.deleteByIdAndUserId(id, userId);
        if (count == 0) {
            throw new EntityNotFoundException("Comment not found or you are not authorized to delete this Comment");
        }
        return "Comment deleted successfully";
    }
}
