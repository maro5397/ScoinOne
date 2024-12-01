package com.scoinone.core.service;

import com.scoinone.core.common.PostType;
import com.scoinone.core.entity.Post;
import com.scoinone.core.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostService {
    Page<Post> getPosts(Pageable pageable, PostType postType);

    Post getPostById(Long id);

    Post createPost(String title, String content, User user, PostType postType);

    Post updatePost(Long id, Long userId, String title, String content);

    String deletePost(Long id, Long userId);
}
