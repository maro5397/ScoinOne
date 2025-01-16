package com.scoinone.post.repository;

import com.scoinone.post.common.type.PostType;
import com.scoinone.post.entity.PostEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<PostEntity, Long> {
    List<PostEntity> findByUserIdAndPostType(String userId, PostType postType);
    Page<PostEntity> findByPostType(Pageable pageable, PostType postType);
    Optional<PostEntity> findByIdAndUserId(Long id, String userId);
    Long deleteByIdAndUserId(Long id, String userId);
}