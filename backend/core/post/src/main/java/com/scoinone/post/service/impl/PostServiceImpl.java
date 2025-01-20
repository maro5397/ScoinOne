package com.scoinone.post.service.impl;

import com.scoinone.post.common.type.PostType;
import com.scoinone.post.entity.PostEntity;
import com.scoinone.post.repository.PostRepository;
import com.scoinone.post.service.PostService;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    @Override
    public Page<PostEntity> getPosts(Pageable pageable, PostType postType) {
        return postRepository.findByPostType(pageable, postType);
    }

    @Override
    public PostEntity getPostById(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Post not found with id: " + id));
    }

    @Override
    public List<PostEntity> getQuestionsByUserId(String userId) {
        return postRepository.findByUserIdAndPostType(userId, PostType.QNA);
    }

    @Override
    public PostEntity createPost(String title, String content, String userId, String username, PostType postType) {
        PostEntity post = PostEntity.builder()
                .userId(userId)
                .username(username)
                .postType(postType)
                .title(title)
                .content(content)
                .viewCount(0)
                .build();
        return postRepository.save(post);
    }

    @Override
    public PostEntity updatePost(Long id, String userId, String title, String content) {
        PostEntity existedPost = postRepository.findByIdAndUserId(id, userId).orElseThrow(
                () -> new EntityNotFoundException("Post not found with id: " + id + ", userId: " + userId));
        existedPost.setTitle(title);
        existedPost.setContent(content);
        return existedPost;
    }

    @Override
    public String deletePost(Long id, String userId) {
        Long count = postRepository.deleteByIdAndUserId(id, userId);
        if (count == 0) {
            throw new EntityNotFoundException("Post not found or you are not authorized to delete this Post");
        }
        return "Post deleted successfully";
    }

    @Override
    public String deleteAllPost(String userId) {
        Long count = postRepository.deleteByUserId(userId);
        return "All of Post(" + count + ") deleted successfully";
    }
}
