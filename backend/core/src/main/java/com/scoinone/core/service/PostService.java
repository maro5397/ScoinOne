package com.scoinone.core.service;

import com.scoinone.core.common.PostType;
import com.scoinone.core.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostService {
    Page<Post> getPosts(Pageable pageable, PostType postType);

    Post getPostById(Long id);

    Post createPost(Post post);

    Post updatePost(Long id, Post updatedPost);

    void deletePost(Long id);
}
