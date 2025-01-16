package com.scoinone.post.integration.repository;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

import com.scoinone.post.common.type.PostType;
import com.scoinone.post.config.TestContainerConfig;
import com.scoinone.post.entity.PostEntity;
import com.scoinone.post.repository.PostRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(TestContainerConfig.class)
@ActiveProfiles("dev")
class PostRepositoryTest {
    private static final String testUserId = "aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaauser2";
    private static final String testUsername = "aaaaaaaa-aaaa-aaaa-aaaa-aaausername1";

    private PostEntity post;

    @Autowired
    private PostRepository postRepository;

    @BeforeEach
    void setUp() {
        PostEntity post1 = PostEntity.builder()
                .userId(testUserId)
                .username(testUsername)
                .postType(PostType.QNA)
                .title("First QNA Post")
                .content("Content of the first QNA post.")
                .viewCount(0)
                .build();

        PostEntity post2 = PostEntity.builder()
                .userId(testUserId)
                .username(testUsername)
                .postType(PostType.QNA)
                .title("Second QNA Post")
                .content("Content of the second QNA post.")
                .viewCount(0)
                .build();

        PostEntity post3 = PostEntity.builder()
                .userId(testUserId)
                .username(testUsername)
                .postType(PostType.ANNOUNCEMENT)
                .title("Breaking ANNOUNCEMENT")
                .content("Content of the breaking ANNOUNCEMENT.")
                .viewCount(0)
                .build();

        post = post1;
        postRepository.save(post1);
        postRepository.save(post2);
        postRepository.save(post3);
    }

    @AfterEach
    void tearDown() {
        postRepository.deleteAll();
    }

    @Test
    @DisplayName("사용자 ID와 게시글 타입으로 게시글 조회")
    void testFindByUserIdAndPostType() {
        PostType postType = PostType.QNA;

        List<PostEntity> posts = postRepository.findByUserIdAndPostType(testUserId, postType);

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

        Page<PostEntity> postsPage = postRepository.findByPostType(pageable, postType);

        assertSoftly(softly -> {
            softly.assertThat(postsPage.getTotalElements()).isEqualTo(2);
            softly.assertThat(postsPage.getContent()).hasSize(2);
            softly.assertThat(postsPage.getContent().getFirst().getTitle()).isEqualTo("First QNA Post");
        });
    }

    @Test
    @DisplayName("게시글 ID 및 사용자 ID로 댓글 조회")
    void testFindByIdAndUser_Id() {
        Optional<PostEntity> getPost = postRepository.findByIdAndUserId(post.getId(), testUserId);

        assertSoftly(softly -> {
            softly.assertThat(getPost.isPresent()).isTrue();
            getPost.ifPresent(postData -> {
                softly.assertThat(postData.getUserId()).isEqualTo(testUserId);
                softly.assertThat(postData.getId()).isEqualTo(post.getId());
            });
        });
    }

    @Test
    @DisplayName("게시글 ID 및 사용자 ID로 댓글 삭제")
    void testDeleteByIdAndUser_Id() {
        Long count = postRepository.deleteByIdAndUserId(post.getId(), testUserId);

        assertSoftly(softly -> {
            softly.assertThat(count).isEqualTo(1);
        });
    }
}