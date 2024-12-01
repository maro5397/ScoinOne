package com.scoinone.core.integration.repository;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

import com.scoinone.core.common.type.PostType;
import com.scoinone.core.config.TestContainerConfig;
import com.scoinone.core.entity.Comment;
import com.scoinone.core.entity.Post;
import com.scoinone.core.entity.User;
import com.scoinone.core.repository.CommentRepository;
import com.scoinone.core.repository.PostRepository;
import com.scoinone.core.repository.UserRepository;
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
    private User user;
    private Post post;
    private Comment comment;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

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

        comment = commentRepository.save(comment1);
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

    @Test
    @DisplayName("댓글 ID 및 사용자 ID로 댓글 조회")
    void testFindByIdAndUser_Id() {
        Optional<Comment> getComment = commentRepository.findByIdAndUser_Id(comment.getId(), user.getId());

        assertSoftly(softly -> {
            softly.assertThat(getComment.isPresent()).isTrue();
            getComment.ifPresent(commentData -> {
                softly.assertThat(commentData.getUser().getId()).isEqualTo(user.getId());
                softly.assertThat(commentData.getPost().getId()).isEqualTo(post.getId());
            });
        });
    }

    @Test
    @DisplayName("댓글 ID 및 사용자 ID로 댓글 삭제")
    void testDeleteByIdAndUser_Id() {
        Long count = commentRepository.deleteByIdAndUser_Id(comment.getId(), user.getId());

        assertSoftly(softly -> {
            softly.assertThat(count).isEqualTo(1);
        });
    }
}