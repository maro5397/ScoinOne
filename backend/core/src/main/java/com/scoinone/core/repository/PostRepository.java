package com.scoinone.core.repository;

import com.scoinone.core.common.PostType;
import com.scoinone.core.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByUser_IdAndPostType(Long userId, PostType postType);
    Page<Post> findByPostType(Pageable pageable, PostType postType);
}