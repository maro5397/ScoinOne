package com.scoinone.core.integration.repository;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

import com.scoinone.core.CoreApplication;
import com.scoinone.core.common.PostType;
import com.scoinone.core.config.TestContainerConfig;
import com.scoinone.core.entity.Post;
import com.scoinone.core.entity.User;
import com.scoinone.core.repository.PostRepository;
import com.scoinone.core.repository.UserRepository;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(TestContainerConfig.class)
class PostRepositoryTest {
    private User user;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .username("testUser")
                .email("test@example.com")
                .password("securePassword")
                .build();
        userRepository.save(user);

        Post post1 = Post.builder()
                .user(user)
                .postType(PostType.QNA)
                .title("First QNA Post")
                .content("Content of the first QNA post.")
                .viewCount(0)
                .build();

        Post post2 = Post.builder()
                .user(user)
                .postType(PostType.QNA)
                .title("Second QNA Post")
                .content("Content of the second QNA post.")
                .viewCount(0)
                .build();

        Post post3 = Post.builder()
                .user(user)
                .postType(PostType.ANNOUNCEMENT)
                .title("Breaking ANNOUNCEMENT")
                .content("Content of the breaking ANNOUNCEMENT.")
                .viewCount(0)
                .build();

        postRepository.save(post1);
        postRepository.save(post2);
        postRepository.save(post3);
    }

    @AfterEach
    void tearDown() {
        postRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("사용자 ID와 게시글 타입으로 게시글 조회")
    void testFindByUserIdAndPostType() {
        Long userId = user.getId();
        PostType postType = PostType.QNA;

        List<Post> posts = postRepository.findByUser_IdAndPostType(userId, postType);

        assertSoftly(softly -> {
            softly.assertThat(posts).hasSize(2);
            softly.assertThat(posts.getFirst().getTitle()).isEqualTo("First QNA Post");
        });
    }

    @Test
    @DisplayName("게시글 타입으로 게시글 조회")
    void testFindByPostType() {
        PostType postType = PostType.QNA;
        Pageable pageable = PageRequest.of(0, 10);

        Page<Post> postsPage = postRepository.findByPostType(pageable, postType);

        assertSoftly(softly -> {
            softly.assertThat(postsPage.getTotalElements()).isEqualTo(2);
            softly.assertThat(postsPage.getContent()).hasSize(2);
            softly.assertThat(postsPage.getContent().getFirst().getTitle()).isEqualTo("First QNA Post");
        });
    }
}