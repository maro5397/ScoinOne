package com.scoinone.core.service;

import com.scoinone.core.entity.Post;

import java.util.List;

public interface PostService {
    List<Post> getPosts();

    Post getPostById(Long id);

    Post createPost(Post post);

    Post updatePost(Long id, Post updatedPost);

    void deletePost(Long id);
}
