package com.scoinone.post.integration.controller;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

import com.scoinone.post.common.type.PostType;
import com.scoinone.post.config.TestContainerConfig;
import com.scoinone.post.dto.common.DeleteResponseDto;
import com.scoinone.post.dto.request.post.CreatePostRequestDto;
import com.scoinone.post.dto.request.post.UpdatePostRequestDto;
import com.scoinone.post.dto.response.post.CreatePostResponseDto;
import com.scoinone.post.dto.response.post.GetPostResponseDto;
import com.scoinone.post.dto.response.post.GetPostsResponseDto;
import com.scoinone.post.dto.response.post.UpdatePostResponseDto;
import com.scoinone.post.entity.PostEntity;
import com.scoinone.post.repository.PostRepository;
import com.scoinone.post.service.PostService;
import java.util.List;
import java.util.Objects;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Import;
import org.springframework.core.ParameterizedTypeReference;
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
class PostControllerTest {
    private static final String testUserId = "aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaauser2";
    private static final String testUsername = "aaaaaaaa-aaaa-aaaa-aaaa-aaausername1";

    @Autowired
    private TestRestTemplate restTemplate;

    private PostEntity savedPost;
    private HttpHeaders headers;

    @BeforeEach
    void setUp(@Autowired PostService postService) {
        savedPost = postService.createPost("testTitle", "testContent", testUserId, testUsername, PostType.QNA);

        headers = new HttpHeaders();
        headers.set("UserId", testUserId);
        headers.set("Username", testUsername);
        headers.set("Content-Type", "application/json");
    }

    @AfterEach
    void tearDown(@Autowired PostRepository postRepository) {
        postRepository.deleteAll();
    }

    @Test
    @DisplayName("게시글 생성 테스트")
    void createPost_shouldReturnCreatedPost() {
        CreatePostRequestDto requestDto = new CreatePostRequestDto();
        requestDto.setTitle("Test Post");
        requestDto.setContent("This is a test post content.");

        ResponseEntity<CreatePostResponseDto> response = restTemplate.exchange(
                "/api/post/" + PostType.QNA,
                HttpMethod.POST,
                new HttpEntity<>(requestDto, headers),
                CreatePostResponseDto.class
        );

        assertSoftly(softly -> {
            softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
            softly.assertThat(Objects.requireNonNull(response.getBody()).getTitle()).isEqualTo("Test Post");
            softly.assertThat(response.getBody().getContent()).isEqualTo("This is a test post content.");
        });
    }

    @Test
    @DisplayName("게시글 조회 테스트")
    void getPost_shouldReturnPost() {
        ResponseEntity<GetPostResponseDto> response = restTemplate.exchange(
                "/api/post/" + PostType.QNA + "/" + savedPost.getId(),
                HttpMethod.GET,
                null,
                GetPostResponseDto.class
        );

        assertSoftly(softly -> {
            softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
            softly.assertThat(Objects.requireNonNull(response.getBody()).getTitle()).isEqualTo("testTitle");
            softly.assertThat(response.getBody().getContent()).isEqualTo("testContent");
        });
    }

    @Test
    @DisplayName("게시글 수정 테스트")
    void updatePost_shouldReturnUpdatedPost() {
        UpdatePostRequestDto updateRequestDto = new UpdatePostRequestDto();
        updateRequestDto.setTitle("Updated Title");
        updateRequestDto.setContent("Updated Content");

        ResponseEntity<UpdatePostResponseDto> response = restTemplate.exchange(
                "/api/post/" + PostType.QNA + "/" + savedPost.getId(),
                HttpMethod.PATCH,
                new HttpEntity<>(updateRequestDto, headers),
                UpdatePostResponseDto.class
        );

        assertSoftly(softly -> {
            softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
            softly.assertThat(Objects.requireNonNull(response.getBody()).getTitle()).isEqualTo("Updated Title");
            softly.assertThat(response.getBody().getContent()).isEqualTo("Updated Content");
        });
    }

    @Test
    @DisplayName("게시글 삭제 테스트")
    void deletePost_shouldReturnOk() {
        ResponseEntity<DeleteResponseDto> response = restTemplate.exchange(
                "/api/post/" + PostType.QNA + "/" + savedPost.getId(),
                HttpMethod.DELETE,
                new HttpEntity<>(headers),
                DeleteResponseDto.class
        );

        assertSoftly(softly -> {
            softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
            softly.assertThat(Objects.requireNonNull(response.getBody()).getMessage())
                    .contains("Post deleted successfully");
        });
    }

    @Test
    @DisplayName("게시글 목록 조회 테스트")
    void getPosts_shouldReturnListOfPosts() {
        Pageable pageable = PageRequest.of(0, 10);
        String url = String.format(
                "/api/post/%s?page=%d&size=%d",
                PostType.QNA,
                pageable.getPageNumber(),
                pageable.getPageSize()
        );

        ResponseEntity<GetPostsResponseDto> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                GetPostsResponseDto.class
        );

        assertSoftly(softly -> {
            softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
            softly.assertThat(Objects.requireNonNull(response.getBody()).getPosts()).hasSize(1);
        });
    }

    @Test
    @DisplayName("사용자 질문 게시물 조회 테스트")
    void getQuestionPosts_shouldReturnListOfPosts() {
        ResponseEntity<List<GetPostResponseDto>> response = restTemplate.exchange(
                "/api/post/question",
                HttpMethod.GET,
                new HttpEntity<>(headers),
                new ParameterizedTypeReference<List<GetPostResponseDto>>() {}
        );

        assertSoftly(softly -> {
            softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
            softly.assertThat(Objects.requireNonNull(response.getBody()).size()).isEqualTo(1);
        });
    }

    @Test
    @DisplayName("게시글 전체 삭제 테스트")
    void deleteAllPostByUserId_shouldReturnOk() {
        ResponseEntity<DeleteResponseDto> response = restTemplate.exchange(
                "/api/post?userId=" + testUserId,
                HttpMethod.DELETE,
                new HttpEntity<>(headers),
                DeleteResponseDto.class
        );

        assertSoftly(softly -> {
            softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
            softly.assertThat(Objects.requireNonNull(response.getBody()).getMessage())
                    .contains("All of Post(1) deleted successfully");
        });
    }
}