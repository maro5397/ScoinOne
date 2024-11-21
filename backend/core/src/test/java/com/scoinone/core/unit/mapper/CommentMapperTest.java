package com.scoinone.core.unit.mapper;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

import com.scoinone.core.dto.response.comment.CreateCommentResponseDto;
import com.scoinone.core.dto.response.comment.GetCommentListResponseDto;
import com.scoinone.core.dto.response.comment.GetCommentResponseDto;
import com.scoinone.core.dto.response.comment.UpdateCommentResponseDto;
import com.scoinone.core.entity.Comment;
import com.scoinone.core.entity.Post;
import com.scoinone.core.entity.User;
import com.scoinone.core.mapper.CommentMapper;
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
    private CommentMapper commentMapper;
    private List<Comment> comments;

    @BeforeEach
    public void setUp() {
        commentMapper = Mappers.getMapper(CommentMapper.class);
        comments = Arrays.asList(
                createComment(1L, "first comment", "user1", 1L),
                createComment(2L, "second comment", "user2", 2L),
                createComment(3L, "third comment", "user3", 3L)
        );
    }

    @Test
    @DisplayName("댓글 엔티티 객체를 생성용 응답 DTO로 매핑")
    public void testCommentToCreateCommentResponseDto() {
        Comment comment = comments.getFirst();
        CreateCommentResponseDto responseDto = commentMapper.commentToCreateCommentResponseDto(comment);
        assertSoftly(softly -> {
            softly.assertThat(comment.getId()).isEqualTo(responseDto.getCommentId());
            softly.assertThat(comment.getContent()).isEqualTo(responseDto.getContent());
            softly.assertThat("user1").isEqualTo(responseDto.getAuthor());
        });
    }

    @Test
    @DisplayName("댓글 엔티티 객체를 조회용 응답 DTO로 매핑")
    public void testCommentToGetCommentResponseDto() {
        Comment comment = comments.getFirst();
        GetCommentResponseDto responseDto = commentMapper.commentToGetCommentResponseDto(comment);
        assertSoftly(softly -> {
            softly.assertThat(comment.getId()).isEqualTo(responseDto.getCommentId());
            softly.assertThat(comment.getContent()).isEqualTo(responseDto.getContent());
            softly.assertThat("user1").isEqualTo(responseDto.getAuthor());
        });
    }

    @Test
    @DisplayName("다수의 댓글 엔티티 객체들을 조회용 응답 DTO로 매핑")
    public void testCommentsToGetCommentsResponseDto() {
        Comment comment1 = comments.get(1);
        Comment comment2 = comments.get(2);
        List<Comment> comments = Arrays.asList(comment1, comment2);

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
        Comment comment = comments.getFirst();
        Page<Comment> page = new PageImpl<>(Collections.singletonList(comment), Pageable.ofSize(1), 1);

        GetCommentListResponseDto responseDto = commentMapper.pageToGetCommentListResponseDto(page);

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
        Comment comment = comments.getFirst();
        UpdateCommentResponseDto responseDto = commentMapper.commentToUpdateCommentResponseDto(comment);
        assertSoftly(softly -> {
            softly.assertThat(comment.getId()).isEqualTo(responseDto.getCommentId());
            softly.assertThat(comment.getContent()).isEqualTo(responseDto.getContent());
            softly.assertThat("user1").isEqualTo(responseDto.getAuthor());
        });
    }

    private Comment createComment(Long commentId, String content, String username, Long postId) {
        User user = User.builder()
                .username(username)
                .build();

        Post post = Post.builder()
                .id(postId)
                .user(user)
                .build();

        return Comment.builder()
                .id(commentId)
                .content(content)
                .user(user)
                .post(post)
                .build();
    }
}