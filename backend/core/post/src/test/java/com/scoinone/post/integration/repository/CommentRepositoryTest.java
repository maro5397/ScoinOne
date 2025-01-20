package com.scoinone.post.integration.repository;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

import com.scoinone.post.common.type.PostType;
import com.scoinone.post.config.TestContainerConfig;
import com.scoinone.post.entity.CommentEntity;
import com.scoinone.post.entity.PostEntity;
import com.scoinone.post.repository.CommentRepository;
import com.scoinone.post.repository.PostRepository;
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
class CommentRepositoryTest {
    private static final String testUserId = "aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaauser1";
    private static final String testUsername = "aaaaaaaa-aaaa-aaaa-aaaa-aaausername1";

    private PostEntity post;
    private CommentEntity comment;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    @BeforeEach
    void setUp() {
        post = PostEntity.builder()
                .userId(testUserId)
                .username(testUsername)
                .postType(PostType.QNA)
                .title("Test Post")
                .content("This is a test post.")
                .viewCount(0)
                .build();
        postRepository.save(post);

        CommentEntity comment1 = CommentEntity.builder()
                .post(post)
                .userId(testUserId)
                .username(testUsername)
                .content("This is the first comment.")
                .build();

        CommentEntity comment2 = CommentEntity.builder()
                .post(post)
                .userId(testUserId)
                .username(testUsername)
                .content("This is the second comment.")
                .build();

        comment = commentRepository.save(comment1);
        commentRepository.save(comment2);
    }

    @AfterEach
    void tearDown() {
        commentRepository.deleteAll();
        postRepository.deleteAll();
    }

    @Test
    @DisplayName("게시글에 달린 댓글 조회")
    void testFindByPost_Id() {
        Long postId = post.getId();
        Pageable pageable = PageRequest.of(0, 10);

        Page<CommentEntity> commentsPage = commentRepository.findByPost_Id(pageable, postId);

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

        Page<CommentEntity> commentsPage = commentRepository.findByPost_Id(pageable, invalidPostId);

        assertSoftly(softly -> {
            softly.assertThat(commentsPage).isNotNull();
            softly.assertThat(commentsPage.getTotalElements()).isEqualTo(0);
            softly.assertThat(commentsPage.getContent()).isEmpty();
        });
    }

    @Test
    @DisplayName("댓글 ID 및 사용자 ID로 댓글 조회")
    void testFindByIdAndUserId() {
        Optional<CommentEntity> getComment = commentRepository.findByIdAndUserId(comment.getId(), testUserId);

        assertSoftly(softly -> {
            softly.assertThat(getComment.isPresent()).isTrue();
            getComment.ifPresent(commentData -> {
                softly.assertThat(commentData.getUserId()).isEqualTo(testUserId);
                softly.assertThat(commentData.getPost().getId()).isEqualTo(post.getId());
            });
        });
    }

    @Test
    @DisplayName("댓글 ID 및 사용자 ID로 댓글 삭제")
    void testDeleteByIdAndUserId() {
        Long count = commentRepository.deleteByIdAndUserId(comment.getId(), testUserId);

        assertSoftly(softly -> {
            softly.assertThat(count).isEqualTo(1);
        });
    }

    @Test
    @DisplayName("사용자 ID로 댓글 삭제")
    void testDeleteByUserId() {
        Long count = commentRepository.deleteByUserId(testUserId);

        assertSoftly(softly -> {
            softly.assertThat(count).isEqualTo(2);
        });
    }
}