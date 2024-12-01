package com.scoinone.core.service.impl;

import com.scoinone.core.entity.Comment;
import com.scoinone.core.entity.Post;
import com.scoinone.core.entity.User;
import com.scoinone.core.repository.CommentRepository;
import com.scoinone.core.repository.PostRepository;
import com.scoinone.core.service.CommentService;
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
    public Page<Comment> getCommentsByPostId(Pageable pageable, Long postId) {
        return commentRepository.findByPost_Id(pageable, postId);
    }

    @Override
    public Comment getCommentById(Long id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Comment not found with id: " + id));
    }

    @Override
    public Comment createComment(Long PostId, String content, User user) {
        Post post = postRepository.findById(PostId)
                .orElseThrow(() -> new EntityNotFoundException("Post not found with id: " + PostId));
        Comment comment = Comment.builder()
                .post(post)
                .user(user)
                .content(content)
                .build();
        return commentRepository.save(comment);
    }

    @Override
    public Comment updateComment(Long id, Long userId, String newContent) {
        Comment existedComment = commentRepository.findByIdAndUser_Id(id, userId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Comment not found with id: " + id + ", userId: " + userId
                ));
        existedComment.setContent(newContent);
        return existedComment;
    }

    @Override
    public String deleteComment(Long id, Long userId) {
        Long count = commentRepository.deleteByIdAndUser_Id(id, userId);
        if (count == 0) {
            throw new EntityNotFoundException("Comment not found or you are not authorized to delete this Comment");
        }
        return "Comment deleted successfully";
    }
}
