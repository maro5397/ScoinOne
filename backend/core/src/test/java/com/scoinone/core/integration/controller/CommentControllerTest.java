package com.scoinone.core.integration.controller;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

import com.scoinone.core.common.PostType;
import com.scoinone.core.config.TestContainerConfig;
import com.scoinone.core.dto.common.DeleteResponseDto;
import com.scoinone.core.dto.request.comment.CreateCommentRequestDto;
import com.scoinone.core.dto.request.comment.UpdateCommentRequestDto;
import com.scoinone.core.dto.response.comment.CreateCommentResponseDto;
import com.scoinone.core.dto.response.comment.GetCommentsResponseDto;
import com.scoinone.core.dto.response.comment.UpdateCommentResponseDto;
import com.scoinone.core.entity.Comment;
import com.scoinone.core.entity.Post;
import com.scoinone.core.entity.User;
import com.scoinone.core.repository.CommentRepository;
import com.scoinone.core.repository.PostRepository;
import com.scoinone.core.service.AuthService;
import com.scoinone.core.service.CommentService;
import com.scoinone.core.service.PostService;
import com.scoinone.core.service.UserService;
import com.scoinone.core.util.UserDataInitializer;
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
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import({TestContainerConfig.class, UserDataInitializer.class})
@ActiveProfiles("dev")
class CommentControllerTest {
    @Autowired
    private TestRestTemplate restTemplate;

    private Post savedPost;
    private Comment savedComment;
    private HttpHeaders headers;

    @BeforeEach
    void setUp(
            @Autowired PostService postService,
            @Autowired CommentService commentService,
            @Autowired UserService userService,
            @Autowired AuthService authService
    ) {
        restTemplate.getRestTemplate().setRequestFactory(new HttpComponentsClientHttpRequestFactory());
        User savedUser = userService.getUserByEmail("user@example.com");

        savedPost = postService.createPost("testTitle", "test Content", savedUser, PostType.QNA);
        savedComment = commentService.createComment(savedPost.getId(), "test Comment Content", savedUser);

        String jwtToken = authService.authenticate("user@example.com", "password");

        headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + jwtToken);
        headers.set("Content-Type", "application/json");
    }

    @AfterEach
    void tearDown(
            @Autowired CommentRepository commentRepository,
            @Autowired PostRepository postRepository
    ) {
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
                new HttpEntity<>(headers),
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