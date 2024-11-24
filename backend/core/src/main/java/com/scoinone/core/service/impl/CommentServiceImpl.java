package com.scoinone.core.service.impl;

import com.scoinone.core.entity.Comment;
import com.scoinone.core.repository.CommentRepository;
import com.scoinone.core.service.CommentService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    @Override
    public Page<Comment> getCommentsByPostId(Pageable pageable, Long postId) {
        return commentRepository.findByPost_Id(pageable, postId);
    }

    @Override
    public Comment getCommentById(Long id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Comment not found with id: " + id));
    }

    @Override
    public Comment createComment(Comment comment) {
        return commentRepository.save(comment);
    }

    @Override
    public Comment updateComment(Long id, Comment updatedComment) {
        Comment existedComment = getCommentById(id);
        existedComment.setContent(updatedComment.getContent());
        return commentRepository.save(existedComment);
    }

    @Override
    public void deleteComment(Long id) {
        commentRepository.deleteById(id);
    }
}
