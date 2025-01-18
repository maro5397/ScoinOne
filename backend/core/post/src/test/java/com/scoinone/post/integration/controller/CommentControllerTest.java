package com.scoinone.post.integration.controller;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

import com.scoinone.post.common.type.PostType;
import com.scoinone.post.config.TestContainerConfig;
import com.scoinone.post.dto.common.DeleteResponseDto;
import com.scoinone.post.dto.request.comment.CreateCommentRequestDto;
import com.scoinone.post.dto.request.comment.UpdateCommentRequestDto;
import com.scoinone.post.dto.response.comment.CreateCommentResponseDto;
import com.scoinone.post.dto.response.comment.GetCommentsResponseDto;
import com.scoinone.post.dto.response.comment.UpdateCommentResponseDto;
import com.scoinone.post.entity.CommentEntity;
import com.scoinone.post.entity.PostEntity;
import com.scoinone.post.repository.CommentRepository;
import com.scoinone.post.repository.PostRepository;
import com.scoinone.post.service.CommentService;
import com.scoinone.post.service.PostService;
import java.util.Objects;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import({TestContainerConfig.class})
@ActiveProfiles("dev")
class CommentControllerTest {
    private static final String testUserId = "aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaauser2";
    private static final String testUsername = "aaaaaaaa-aaaa-aaaa-aaaa-aaausername1";

    @Autowired
    private TestRestTemplate restTemplate;

    private PostEntity savedPost;
    private CommentEntity savedComment;
    private HttpHeaders headers;

    @BeforeEach
    void setUp(@Autowired PostService postService, @Autowired CommentService commentService) {
        savedPost = postService.createPost("testTitle", "test Content", testUserId, testUsername, PostType.QNA);
        savedComment = commentService.createComment(
                savedPost.getId(),
                "test Comment Content",
                testUserId,
                testUsername
        );

        headers = new HttpHeaders();
        headers.set("UserId", testUserId);
        headers.set("Username", testUsername);
        headers.set("Content-Type", "application/json");
    }

    @AfterEach
    void tearDown(@Autowired CommentRepository commentRepository, @Autowired PostRepository postRepository) {
        commentRepository.deleteAll();
        postRepository.deleteAll();
    }

    @Test
    @DisplayName("댓글 생성 테스트")
    void createComment_shouldReturnCreatedComment() {
        CreateCommentRequestDto requestDto = new CreateCommentRequestDto();
        requestDto.setPostId(savedPost.getId());
        requestDto.setContent("This is a test comment.");

        ResponseEntity<CreateCommentResponseDto> response = restTemplate.exchange(
                "/api/comment",
                HttpMethod.POST,
                new HttpEntity<>(requestDto, headers),
                CreateCommentResponseDto.class
        );

        assertSoftly(softly -> {
            softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
            softly.assertThat(Objects.requireNonNull(response.getBody()).getContent())
                    .isEqualTo("This is a test comment.");
        });
    }

    @Test
    @DisplayName("댓글 조회 테스트")
    void getComments_shouldReturnListOfComments() {
        Pageable pageable = PageRequest.of(0, 10);
        String url = String.format(
                "/api/comment/%d?page=%d&size=%d",
                savedPost.getId(),
                pageable.getPageNumber(),
                pageable.getPageSize()
        );

        ResponseEntity<GetCommentsResponseDto> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                GetCommentsResponseDto.class
        );

        assertSoftly(softly -> {
            softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
            softly.assertThat(Objects.requireNonNull(response.getBody()).getComments()).hasSize(1);
        });
    }

    @Test
    @DisplayName("댓글 수정 테스트")
    void updateComment_shouldReturnUpdatedComment() {
        UpdateCommentRequestDto updateRequestDto = new UpdateCommentRequestDto();
        updateRequestDto.setContent("Updated Comment");

        ResponseEntity<UpdateCommentResponseDto> response = restTemplate.exchange(
                "/api/comment/" + savedComment.getId(),
                HttpMethod.PATCH,
                new HttpEntity<>(updateRequestDto, headers),
                UpdateCommentResponseDto.class
        );

        assertSoftly(softly -> {
            softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
            softly.assertThat(Objects.requireNonNull(response.getBody()).getContent()).isEqualTo("Updated Comment");
        });
    }

    @Test
    @DisplayName("댓글 삭제 테스트")
    void deleteComment_shouldReturnOk() {
        ResponseEntity<DeleteResponseDto> response = restTemplate.exchange(
                "/api/comment/" + savedComment.getId(),
                HttpMethod.DELETE,
                new HttpEntity<>(headers),
                DeleteResponseDto.class
        );

        assertSoftly(softly -> {
            softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
            softly.assertThat(Objects.requireNonNull(response.getBody()).getMessage())
                    .contains("Comment deleted successfully");
        });
    }
}