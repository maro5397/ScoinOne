package com.scoinone.core.repository;

import com.scoinone.core.common.type.PostType;
import com.scoinone.core.entity.Post;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByUser_IdAndPostType(Long userId, PostType postType);
    Page<Post> findByPostType(Pageable pageable, PostType postType);
    Optional<Post> findByIdAndUser_Id(Long id, Long userId);
    Long deleteByIdAndUser_Id(Long id, Long userId);
}