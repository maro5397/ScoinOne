package com.scoinone.core.service;

import com.scoinone.core.entity.Post;
import org.springframework.data.domain.Page;

import java.util.List;

public interface PostService {
    Page<Post> getPosts(int page, int size);

    Post getPostById(Long id);

    Post createPost(Post post);

    Post updatePost(Long id, Post updatedPost);

    void deletePost(Long id);
}
