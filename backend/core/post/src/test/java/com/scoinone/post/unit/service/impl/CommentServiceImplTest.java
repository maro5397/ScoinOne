package com.scoinone.post.unit.service.impl;

import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.ArgumentCaptor.forClass;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.scoinone.post.entity.CommentEntity;
import com.scoinone.post.entity.PostEntity;
import com.scoinone.post.repository.CommentRepository;
import com.scoinone.post.repository.PostRepository;
import com.scoinone.post.service.impl.CommentServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

class CommentServiceImplTest {
    private static final String testUserId = "aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaauser1";
    private static final String testUsername = "testUsername";

    @InjectMocks
    private CommentServiceImpl commentService;

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private PostRepository postRepository;

    @Mock
    private Page<CommentEntity> page;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("게시글에 달린 댓글 모두 조회 테스트")
    public void testGetCommentsByPostId_Success() {
        Long postId = 1L;
        Pageable pageable = Pageable.unpaged();
        when(commentRepository.findByPost_Id(pageable, postId)).thenReturn(page);

        Page<CommentEntity> result = commentService.getCommentsByPostId(postId, pageable);

        assertSoftly(softly -> {
            softly.assertThat(result).isNotNull();
            verify(commentRepository).findByPost_Id(pageable, postId);
        });
    }

    @Test
    @DisplayName("댓글 인덱스로 댓글 조회")
    public void testGetCommentById_Success() {
        Long commentId = 1L;
        CommentEntity comment = CommentEntity.builder()
                .id(commentId)
                .build();
        when(commentRepository.findById(commentId)).thenReturn(Optional.of(comment));

        CommentEntity result = commentService.getCommentById(commentId);

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
        Long postId = 1L;
        String content = "Test comment";
        PostEntity post = PostEntity.builder().build();
        when(postRepository.findById(postId)).thenReturn(Optional.of(post));

        commentService.createComment(postId, content, testUserId, testUsername);

        ArgumentCaptor<CommentEntity> commentCaptor = forClass(CommentEntity.class);
        verify(commentRepository).save(commentCaptor.capture());

        CommentEntity savedComment = commentCaptor.getValue();

        assertSoftly(softly -> {
            softly.assertThat(savedComment).isNotNull();
            softly.assertThat(savedComment.getContent()).isEqualTo("Test comment");
            verify(commentRepository).save(savedComment);
        });
    }

    @Test
    @DisplayName("댓글 수정 테스트")
    public void testUpdateComment() {
        Long commentId = 1L;
        CommentEntity existingComment = CommentEntity.builder()
                .id(commentId)
                .userId(testUserId)
                .username(testUsername)
                .content("Old content")
                .build();

        when(commentRepository.findByIdAndUserId(commentId, testUserId)).thenReturn(Optional.of(existingComment));

        CommentEntity result = commentService.updateComment(commentId, testUserId, "Updated content");

        assertSoftly(softly -> {
            softly.assertThat(result).isNotNull();
            softly.assertThat(result.getContent()).isEqualTo("Updated content");
            verify(commentRepository).findByIdAndUserId(commentId, testUserId);
        });
    }

    @Test
    @DisplayName("댓글 삭제 테스트")
    public void testDeleteComment() {
        Long commentId = 1L;

        when(commentRepository.deleteByIdAndUserId(commentId, testUserId)).thenReturn(1L);
        commentService.deleteComment(commentId, testUserId);

        verify(commentRepository).deleteByIdAndUserId(commentId, testUserId);
    }
}