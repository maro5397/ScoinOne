package com.scoinone.core.integration.repository;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

import com.scoinone.core.CoreApplication;
import com.scoinone.core.common.PostType;
import com.scoinone.core.entity.Comment;
import com.scoinone.core.entity.Post;
import com.scoinone.core.entity.User;
import com.scoinone.core.repository.CommentRepository;
import com.scoinone.core.repository.PostRepository;
import com.scoinone.core.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@SpringBootTest(classes = CoreApplication.class)
class CommentRepositoryTest {
    private User user;
    private Post post;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Container
    static MySQLContainer<?> sqlContainer = new MySQLContainer<>(
            "mysql:8.0.34"
    );

    @DynamicPropertySource
    public static void overrideProps(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.driver-class-name", sqlContainer::getDriverClassName);
        registry.add("spring.datasource.url", sqlContainer::getJdbcUrl);
        registry.add("spring.datasource.~username", sqlContainer::getUsername);
        registry.add("spring.datasource.password", sqlContainer::getPassword);
    }

    @BeforeEach
    void setUp() {
        user = User.builder()
                .username("testUser")
                .email("user@example.com")
                .password("securePassword")
                .build();
        userRepository.save(user);

        post = Post.builder()
                .user(user)
                .postType(PostType.QNA)
                .title("Test Post")
                .content("This is a test post.")
                .viewCount(0)
                .build();
        postRepository.save(post);

        Comment comment1 = Comment.builder()
                .post(post)
                .user(user)
                .content("This is the first comment.")
                .build();

        Comment comment2 = Comment.builder()
                .post(post)
                .user(user)
                .content("This is the second comment.")
                .build();

        commentRepository.save(comment1);
        commentRepository.save(comment2);
    }

    @AfterEach
    void tearDown() {
        commentRepository.deleteAll();
        postRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("게시글에 달린 댓글 조회")
    void testFindByPost_Id() {
        Long postId = post.getId();
        Pageable pageable = PageRequest.of(0, 10);

        Page<Comment> commentsPage = commentRepository.findByPost_Id(pageable, postId);

        assertSoftly(softly -> {
            softly.assertThat(commentsPage).isNotNull();
            softly.assertThat(commentsPage.getTotalElements()).isEqualTo(2);
            softly.assertThat(commentsPage.getContent()).hasSize(2);
            softly.assertThat(commentsPage.getContent().get(0).getContent()).isEqualTo("This is the first comment.");
            softly.assertThat(commentsPage.getContent().get(1).getContent()).isEqualTo("This is the second comment.");
        });
    }

    @Test
    @DisplayName("없는 게시글에 달린 댓글 조회")
    void testFindByPost_Id_NoCommentsFound() {
        Long invalidPostId = 999L;
        Pageable pageable = PageRequest.of(0, 10);

        Page<Comment> commentsPage = commentRepository.findByPost_Id(pageable, invalidPostId);

        assertSoftly(softly -> {
            softly.assertThat(commentsPage).isNotNull();
            softly.assertThat(commentsPage.getTotalElements()).isEqualTo(0);
            softly.assertThat(commentsPage.getContent()).isEmpty();
        });
    }
}