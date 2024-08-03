package com.scoinone.core.service.impl;

import com.scoinone.core.entity.Post;
import com.scoinone.core.repository.PostRepository;
import com.scoinone.core.service.PostService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    
    private final PostRepository postRepository;

    @Override
    public Page<Post> getPosts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return postRepository.findAll(pageable);
    }

    @Override
    public Post getPostById(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Post not found with id: " + id));
    }

    @Override
    public Post createPost(Post post) {
        return postRepository.save(post);
    }

    @Override
    public Post updatePost(Long id, Post updatedPost) {
        Post existedPost = getPostById(id);
        existedPost.setTitle(updatedPost.getTitle());
        existedPost.setContent(updatedPost.getContent());
        return postRepository.save(existedPost);
    }

    @Override
    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }
}
