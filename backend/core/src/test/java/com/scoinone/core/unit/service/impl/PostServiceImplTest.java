package com.scoinone.core.unit.service.impl;

import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.ArgumentCaptor.forClass;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.scoinone.core.common.type.PostType;
import com.scoinone.core.entity.Post;
import com.scoinone.core.entity.User;
import com.scoinone.core.repository.PostRepository;
import com.scoinone.core.service.impl.PostServiceImpl;
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

class PostServiceImplTest {
    @InjectMocks
    private PostServiceImpl postService;

    @Mock
    private PostRepository postRepository;

    @Mock
    private Page<Post> page;

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

        Page<Post> result = postService.getPosts(pageable, postType);

        assertSoftly(softly -> {
            softly.assertThat(result).isNotNull();
            verify(postRepository).findByPostType(pageable, postType);
        });
    }

    @Test
    @DisplayName("인덱스로 게시글 가져오기")
    public void testGetPostById_Success() {
        Long postId = 1L;
        Post post = Post.builder()
                .id(postId)
                .build();
        when(postRepository.findById(postId)).thenReturn(Optional.of(post));

        Post result = postService.getPostById(postId);

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
        User user = User.builder().build();

        postService.createPost(title, content, user, PostType.QNA);

        ArgumentCaptor<Post> postCaptor = forClass(Post.class);
        verify(postRepository).save(postCaptor.capture());

        Post savedPost = postCaptor.getValue();

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
        Long userId = 1L;
        String title = "Test Title";
        String content = "Test Content";
        Post existingPost = Post.builder()
                .id(postId)
                .title("Old Title")
                .content("Old Content")
                .build();

        when(postRepository.findByIdAndUser_Id(postId, userId)).thenReturn(Optional.of(existingPost));

        Post result = postService.updatePost(postId, userId, title, content);

        assertSoftly(softly -> {
            softly.assertThat(result).isNotNull();
            softly.assertThat(result.getTitle()).isEqualTo(title);
            softly.assertThat(result.getContent()).isEqualTo(content);
            verify(postRepository).findByIdAndUser_Id(postId, userId);
        });
    }

    @Test
    @DisplayName("게시글 삭제 테스트")
    public void testDeletePost() {
        Long postId = 1L;
        Long userId = 1L;

        when(postRepository.deleteByIdAndUser_Id(postId, userId)).thenReturn(1L);

        postService.deletePost(postId, userId);

        verify(postRepository).deleteByIdAndUser_Id(postId, userId);
    }
}