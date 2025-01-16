package com.scoinone.post.unit.mapper;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

import com.scoinone.post.dto.response.comment.CreateCommentResponseDto;
import com.scoinone.post.dto.response.comment.GetCommentResponseDto;
import com.scoinone.post.dto.response.comment.GetCommentsResponseDto;
import com.scoinone.post.dto.response.comment.UpdateCommentResponseDto;
import com.scoinone.post.entity.CommentEntity;
import com.scoinone.post.entity.PostEntity;
import com.scoinone.post.mapper.CommentMapper;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

class CommentMapperTest {
    private static final String testUserId1 = "aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaauser1";
    private static final String testUserId2 = "aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaauser2";
    private static final String testUserId3 = "aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaauser3";
    private static final String testUsername1 = "testUsername1";
    private static final String testUsername2 = "testUsername2";
    private static final String testUsername3 = "testUsername3";

    private CommentMapper commentMapper;
    private List<CommentEntity> comments;

    @BeforeEach
    public void setUp() {
        commentMapper = Mappers.getMapper(CommentMapper.class);
        comments = Arrays.asList(
                createComment(1L, "first comment", testUserId1, testUsername1),
                createComment(2L, "second comment", testUserId2, testUsername2),
                createComment(3L, "third comment", testUserId3, testUsername3)
        );
    }

    @Test
    @DisplayName("댓글 엔티티 객체를 생성용 응답 DTO로 매핑")
    public void testCommentToCreateCommentResponseDto() {
        CommentEntity comment = comments.getFirst();
        CreateCommentResponseDto responseDto = commentMapper.commentToCreateCommentResponseDto(comment);
        assertSoftly(softly -> {
            softly.assertThat(comment.getId()).isEqualTo(responseDto.getCommentId());
            softly.assertThat(comment.getContent()).isEqualTo(responseDto.getContent());
            softly.assertThat(testUsername1).isEqualTo(responseDto.getAuthor());
        });
    }

    @Test
    @DisplayName("댓글 엔티티 객체를 조회용 응답 DTO로 매핑")
    public void testCommentToGetCommentResponseDto() {
        CommentEntity comment = comments.getFirst();
        GetCommentResponseDto responseDto = commentMapper.commentToGetCommentResponseDto(comment);
        assertSoftly(softly -> {
            softly.assertThat(comment.getId()).isEqualTo(responseDto.getCommentId());
            softly.assertThat(comment.getContent()).isEqualTo(responseDto.getContent());
            softly.assertThat(testUsername1).isEqualTo(responseDto.getAuthor());
        });
    }

    @Test
    @DisplayName("다수의 댓글 엔티티 객체들을 조회용 응답 DTO로 매핑")
    public void testCommentsToGetCommentsResponseDto() {
        CommentEntity comment1 = comments.get(1);
        CommentEntity comment2 = comments.get(2);
        List<CommentEntity> comments = Arrays.asList(comment1, comment2);

        List<GetCommentResponseDto> responseDto = commentMapper.commentsToGetCommentsResponseDto(comments);

        assertSoftly(softly -> {
            softly.assertThat(responseDto.size()).isEqualTo(2);
            softly.assertThat(responseDto.get(0).getCommentId()).isEqualTo(comment1.getId());
            softly.assertThat(responseDto.get(1).getCommentId()).isEqualTo(comment2.getId());
        });
    }

    @Test
    @DisplayName("다수의 댓글 엔티티 객체를 페이지네이션을 통해 조회용 응답 DTO로 매핑")
    public void testPageToGetCommentListResponseDto() {
        CommentEntity comment = comments.getFirst();
        Page<CommentEntity> page = new PageImpl<>(Collections.singletonList(comment), Pageable.ofSize(1), 1);

        GetCommentsResponseDto responseDto = commentMapper.pageToGetCommentsResponseDto(page);

        assertSoftly(softly -> {
            softly.assertThat(responseDto.getComments().size()).isEqualTo(1);
            softly.assertThat(responseDto.getComments().getFirst().getCommentId()).isEqualTo(comment.getId());
            softly.assertThat(responseDto.getPageInfo().getTotalElements()).isEqualTo(page.getTotalElements());
            softly.assertThat(responseDto.getPageInfo().getTotalPages()).isEqualTo(page.getTotalPages());
        });
    }

    @Test
    @DisplayName("댓글 엔티티 객체를 수정용 응답 DTO로 매핑")
    public void testCommentToUpdateCommentResponseDto() {
        CommentEntity comment = comments.getFirst();
        UpdateCommentResponseDto responseDto = commentMapper.commentToUpdateCommentResponseDto(comment);
        assertSoftly(softly -> {
            softly.assertThat(comment.getId()).isEqualTo(responseDto.getCommentId());
            softly.assertThat(comment.getContent()).isEqualTo(responseDto.getContent());
            softly.assertThat(testUsername1).isEqualTo(responseDto.getAuthor());
        });
    }

    private CommentEntity createComment(Long commentId, String content, String userId, String username) {
        PostEntity post = PostEntity.builder()
                .userId(userId)
                .username(username)
                .build();

        return CommentEntity.builder()
                .id(commentId)
                .content(content)
                .userId(userId)
                .username(username)
                .post(post)
                .build();
    }
}