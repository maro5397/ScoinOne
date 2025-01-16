package com.scoinone.post.unit.service.impl;

import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.ArgumentCaptor.forClass;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.scoinone.post.common.type.PostType;
import com.scoinone.post.entity.PostEntity;
import com.scoinone.post.repository.PostRepository;
import com.scoinone.post.service.impl.PostServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import java.util.Collections;
import java.util.List;
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

class PostServiceImplTest {
    private static final String testUserId = "aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaauser1";
    private static final String testUsername = "testUsername";

    @InjectMocks
    private PostServiceImpl postService;

    @Mock
    private PostRepository postRepository;

    @Mock
    private Page<PostEntity> page;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("게시글 페이지 가져오기")
    public void testGetPosts_Success() {
        Pageable pageable = Pageable.unpaged();
        PostType postType = PostType.QNA;
        when(postRepository.findByPostType(pageable, postType)).thenReturn(page);

        Page<PostEntity> result = postService.getPosts(pageable, postType);

        assertSoftly(softly -> {
            softly.assertThat(result).isNotNull();
            verify(postRepository).findByPostType(pageable, postType);
        });
    }

    @Test
    @DisplayName("인덱스로 게시글 가져오기")
    public void testGetPostById_Success() {
        Long postId = 1L;
        PostEntity post = PostEntity.builder()
                .id(postId)
                .build();
        when(postRepository.findById(postId)).thenReturn(Optional.of(post));

        PostEntity result = postService.getPostById(postId);

        assertSoftly(softly -> {
            softly.assertThat(result).isNotNull();
            softly.assertThat(result.getId()).isEqualTo(postId);
            verify(postRepository).findById(postId);
        });
    }

    @Test
    @DisplayName("인덱스로 게시글 가져오기 실패")
    public void testGetPostById_NotFound() {
        Long postId = 1L;
        when(postRepository.findById(postId)).thenReturn(Optional.empty());

        assertSoftly(softly -> {
            softly.assertThatThrownBy(() -> postService.getPostById(postId))
                    .isInstanceOf(EntityNotFoundException.class)
                    .hasMessageContaining("Post not found with id: " + postId);
        });
    }

    @Test
    @DisplayName("게시글 생성 테스트")
    public void testCreatePost() {
        String title = "Test Title";
        String content = "Test Content";

        postService.createPost(title, content, testUserId, testUsername, PostType.QNA);

        ArgumentCaptor<PostEntity> postCaptor = forClass(PostEntity.class);
        verify(postRepository).save(postCaptor.capture());

        PostEntity savedPost = postCaptor.getValue();

        assertSoftly(softly -> {
            softly.assertThat(savedPost).isNotNull();
            softly.assertThat(savedPost.getTitle()).isEqualTo("Test Title");
            softly.assertThat(savedPost.getContent()).isEqualTo("Test Content");
            verify(postRepository).save(savedPost);
        });
    }

    @Test
    @DisplayName("게시글 수정 테스트")
    public void testUpdatePost() {
        Long postId = 1L;
        String title = "Test Title";
        String content = "Test Content";
        PostEntity existingPost = PostEntity.builder()
                .id(postId)
                .userId(testUserId)
                .username(testUsername)
                .title("Old Title")
                .content("Old Content")
                .build();

        when(postRepository.findByIdAndUserId(postId, testUserId)).thenReturn(Optional.of(existingPost));

        PostEntity result = postService.updatePost(postId, testUserId, title, content);

        assertSoftly(softly -> {
            softly.assertThat(result).isNotNull();
            softly.assertThat(result.getTitle()).isEqualTo(title);
            softly.assertThat(result.getContent()).isEqualTo(content);
            verify(postRepository).findByIdAndUserId(postId, testUserId);
        });
    }

    @Test
    @DisplayName("게시글 삭제 테스트")
    public void testDeletePost() {
        Long postId = 1L;

        when(postRepository.deleteByIdAndUserId(postId, testUserId)).thenReturn(1L);

        postService.deletePost(postId, testUserId);

        verify(postRepository).deleteByIdAndUserId(postId, testUserId);
    }

    @Test
    @DisplayName("사용자 질의 게시글 조회")
    public void testGetQuestionsByUserId() {
        List<PostEntity> questions = Collections.singletonList(
                PostEntity.builder()
                        .id(1L)
                        .userId(testUserId)
                        .username(testUsername)
                        .postType(PostType.QNA)
                        .build()
        );
        when(postRepository.findByUserIdAndPostType(testUserId, PostType.QNA)).thenReturn(questions);

        List<PostEntity> result = postService.getQuestionsByUserId(testUserId);

        assertSoftly(softly -> {
            softly.assertThat(result).isNotNull();
            softly.assertThat(result.size()).isEqualTo(questions.size());
            verify(postRepository).findByUserIdAndPostType(testUserId, PostType.QNA);
        });
    }
}