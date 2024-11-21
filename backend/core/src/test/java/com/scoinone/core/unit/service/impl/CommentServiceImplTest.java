package com.scoinone.core.unit.service.impl;

import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.scoinone.core.entity.Comment;
import com.scoinone.core.repository.CommentRepository;
import com.scoinone.core.service.impl.CommentServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

class CommentServiceImplTest {
    @InjectMocks
    private CommentServiceImpl commentService;

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private Page<Comment> page;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("게시글에 달린 댓글 모두 조회 테스트")
    public void testGetCommentsByPostId_Success() {
        Long postId = 1L;
        Pageable pageable = Pageable.unpaged();
        when(commentRepository.findByPost_PostId(pageable, postId)).thenReturn(Optional.of(page));

        Page<Comment> result = commentService.getCommentsByPostId(pageable, postId);

        assertSoftly(softly -> {
            softly.assertThat(result).isNotNull();
            verify(commentRepository).findByPost_PostId(pageable, postId);
        });
    }

    @Test
    @DisplayName("게시글에 달린 댓글 조회 실패")
    public void testGetCommentsByPostId_NotFound() {
        Long postId = 1L;
        Pageable pageable = Pageable.unpaged();
        when(commentRepository.findByPost_PostId(pageable, postId)).thenReturn(Optional.empty());

        assertSoftly(softly -> {
            softly.assertThatThrownBy(() -> commentService.getCommentsByPostId(pageable, postId))
                    .isInstanceOf(EntityNotFoundException.class)
                    .hasMessageContaining("Comments not found with postId: " + postId);
        });
    }

    @Test
    @DisplayName("댓글 인덱스로 댓글 조회")
    public void testGetCommentById_Success() {
        Long commentId = 1L;
        Comment comment = Comment.builder()
                .id(commentId)
                .build();
        when(commentRepository.findById(commentId)).thenReturn(Optional.of(comment));

        Comment result = commentService.getCommentById(commentId);

        assertSoftly(softly -> {
            softly.assertThat(result).isNotNull();
            softly.assertThat(result.getId()).isEqualTo(commentId);
            verify(commentRepository).findById(commentId);
        });
    }

    @Test
    @DisplayName("댓글 인덱스로 댓글 조회 실패")
    public void testGetCommentById_NotFound() {
        Long commentId = 1L;
        when(commentRepository.findById(commentId)).thenReturn(Optional.empty());

        assertSoftly(softly -> {
            softly.assertThatThrownBy(() -> commentService.getCommentById(commentId))
                    .isInstanceOf(EntityNotFoundException.class)
                    .hasMessageContaining("Comment not found with id: " + commentId);
        });
    }

    @Test
    @DisplayName("댓글 생성 테스트")
    public void testCreateComment() {
        Comment comment = Comment.builder()
                .content("Test comment")
                .build();
        when(commentRepository.save(comment)).thenReturn(comment);

        Comment result = commentService.createComment(comment);

        assertSoftly(softly -> {
            softly.assertThat(result).isNotNull();
            softly.assertThat(result.getContent()).isEqualTo("Test comment");
            verify(commentRepository).save(comment);
        });
    }

    @Test
    @DisplayName("댓글 수정 테스트")
    public void testUpdateComment() {
        Long commentId = 1L;
        Comment existingComment = Comment.builder()
                .id(commentId)
                .content("Old content")
                .build();

        Comment updatedComment = Comment.builder()
                .content("Updated content")
                .build();

        when(commentRepository.findById(commentId)).thenReturn(Optional.of(existingComment));
        when(commentRepository.save(existingComment)).thenReturn(existingComment);

        Comment result = commentService.updateComment(commentId, updatedComment);

        assertSoftly(softly -> {
            softly.assertThat(result).isNotNull();
            softly.assertThat(result.getContent()).isEqualTo("Updated content");
            verify(commentRepository).findById(commentId);
            verify(commentRepository).save(existingComment);
        });
    }

    @Test
    @DisplayName("댓글 삭제 테스트")
    public void testDeleteComment() {
        Long commentId = 1L;

        commentService.deleteComment(commentId);

        verify(commentRepository).deleteById(commentId);
    }
}