package com.scoinone.post.unit.mapper;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

import com.scoinone.post.common.type.PostType;
import com.scoinone.post.dto.response.post.CreatePostResponseDto;
import com.scoinone.post.dto.response.post.GetPostResponseDto;
import com.scoinone.post.dto.response.post.GetPostsResponseDto;
import com.scoinone.post.dto.response.post.UpdatePostResponseDto;
import com.scoinone.post.entity.PostEntity;
import com.scoinone.post.mapper.PostMapper;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

class PostMapperTest {
    private static final String testUserId1 = "aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaauser1";
    private static final String testUserId2 = "aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaauser2";
    private static final String testUsername1 = "testUsername1";
    private static final String testUsername2 = "testUsername2";

    private PostMapper mapper;
    private List<PostEntity> posts;

    @BeforeEach
    public void setUp() {
        mapper = Mappers.getMapper(PostMapper.class);
        posts = Arrays.asList(
                createPost(
                        1L, testUserId1, PostType.ANNOUNCEMENT, "Test Title1", "Test Content1", testUsername1
                ),
                createPost(
                        2L, testUserId2, PostType.QNA, "Test Title2", "Test Content2", testUsername2
                )
        );
    }

    @Test
    @DisplayName("게시글 엔티티 객체를 생성용 응답 DTO로 매핑")
    public void testPostToCreatePostResponseDto() {
        PostEntity post = posts.getFirst();

        CreatePostResponseDto responseDto = mapper.postToCreatePostResponseDto(post);

        assertSoftly(softly -> {
            softly.assertThat(responseDto).isNotNull();
            softly.assertThat(responseDto.getPostId()).isEqualTo(1L);
            softly.assertThat(responseDto.getPostType()).isEqualTo(PostType.ANNOUNCEMENT);
            softly.assertThat(responseDto.getTitle()).isEqualTo("Test Title1");
            softly.assertThat(responseDto.getContent()).isEqualTo("Test Content1");
            softly.assertThat(responseDto.getAuthor()).isEqualTo(testUsername1);
        });
    }

    @Test
    @DisplayName("게시글 엔티티 객체를 조회용 응답 DTO로 매핑")
    public void testPostToGetPostResponseDto() {
        PostEntity post = posts.getFirst();

        GetPostResponseDto responseDto = mapper.postToGetPostResponseDto(post);

        assertSoftly(softly -> {
            softly.assertThat(responseDto).isNotNull();
            softly.assertThat(responseDto.getPostId()).isEqualTo(1L);
            softly.assertThat(responseDto.getPostType()).isEqualTo(PostType.ANNOUNCEMENT);
            softly.assertThat(responseDto.getTitle()).isEqualTo("Test Title1");
            softly.assertThat(responseDto.getContent()).isEqualTo("Test Content1");
            softly.assertThat(responseDto.getAuthor()).isEqualTo(testUsername1);
        });
    }

    @Test
    @DisplayName("게시글 엔티티 객체를 수정용 응답 DTO로 매핑")
    public void testPostToUpdatePostResponseDto() {
        PostEntity post = posts.getFirst();

        UpdatePostResponseDto responseDto = mapper.postToUpdatePostResponseDto(post);

        assertSoftly(softly -> {
            softly.assertThat(responseDto).isNotNull();
            softly.assertThat(responseDto.getPostId()).isEqualTo(1L);
            softly.assertThat(responseDto.getPostType()).isEqualTo(PostType.ANNOUNCEMENT);
            softly.assertThat(responseDto.getTitle()).isEqualTo("Test Title1");
            softly.assertThat(responseDto.getContent()).isEqualTo("Test Content1");
            softly.assertThat(responseDto.getAuthor()).isEqualTo(testUsername1);
        });
    }

    @Test
    @DisplayName("게시글 페이지 엔티티 객체들을 조회용 응답 DTO로 매핑")
    public void testPageToGetPostListResponseDto() {
        Page<PostEntity> page = new PageImpl<>(posts, PageRequest.of(0, 10), 1);

        GetPostsResponseDto responseDto = mapper.pageToGetPostsResponseDto(page);

        assertSoftly(softly -> {
            softly.assertThat(responseDto).isNotNull();
            softly.assertThat(responseDto.getPosts()).hasSize(2);
            softly.assertThat(responseDto.getPosts().getFirst().getPostId()).isEqualTo(1L);
            softly.assertThat(responseDto.getPageInfo().getTotalElements()).isEqualTo(2);
            softly.assertThat(responseDto.getPageInfo().getTotalPages()).isEqualTo(1);
        });

    }

    @Test
    @DisplayName("게시글 리스트 엔티티 객체들을 조회용 응답 DTO로 매핑")
    public void testListToGetPostListResponseDto() {
        GetPostsResponseDto responseDto = mapper.listToGetPostsResponseDto(posts);

        assertSoftly(softly -> {
            softly.assertThat(responseDto).isNotNull();
            softly.assertThat(responseDto.getPosts()).hasSize(2);
            softly.assertThat(responseDto.getPosts().get(0).getPostId()).isEqualTo(1L);
            softly.assertThat(responseDto.getPosts().get(1).getPostId()).isEqualTo(2L);
        });

    }

    private PostEntity createPost(
            Long id,
            String userId,
            PostType postType,
            String title,
            String content,
            String username
    ) {
        return PostEntity.builder()
                .id(id)
                .userId(userId)
                .username(username)
                .postType(postType)
                .title(title)
                .content(content)
                .build();
    }
}