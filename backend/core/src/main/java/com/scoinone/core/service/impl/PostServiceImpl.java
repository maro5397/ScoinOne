package com.scoinone.core.service.impl;

import com.scoinone.core.common.PostType;
import com.scoinone.core.entity.Post;
import com.scoinone.core.entity.User;
import com.scoinone.core.repository.PostRepository;
import com.scoinone.core.service.PostService;
import jakarta.persistence.EntityNotFoundException;
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
    public Page<Post> getPosts(Pageable pageable, PostType postType) {
        return postRepository.findByPostType(pageable, postType);
    }

    @Override
    public Post getPostById(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Post not found with id: " + id));
    }

    @Override
    public Post createPost(String title, String content, User user, PostType postType) {
        Post post = Post.builder()
                .title(title)
                .content(content)
                .user(user)
                .postType(postType)
                .viewCount(0)
                .build();
        return postRepository.save(post);
    }

    @Override
    public Post updatePost(Long id, String title, String content) {
        Post existedPost = getPostById(id);
        existedPost.setTitle(title);
        existedPost.setContent(content);
        return existedPost;
    }

    @Override
    public String deletePost(Long id) {
        postRepository.deleteById(id);
        return "Post deleted successfully";
    }
}
