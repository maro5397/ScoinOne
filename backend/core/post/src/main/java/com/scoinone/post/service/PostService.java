package com.scoinone.post.service;

import com.scoinone.post.common.type.PostType;
import com.scoinone.post.entity.PostEntity;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostService {
    Page<PostEntity> getPosts(Pageable pageable, PostType postType);

    PostEntity getPostById(Long id);

    List<PostEntity> getQuestionsByUserId(String userId);

    PostEntity createPost(String title, String content, String userId, String username, PostType postType);

    PostEntity updatePost(Long id, String userId, String title, String content);

    String deletePost(Long id, String userId);

    String deleteAllPost(String userId);
}
