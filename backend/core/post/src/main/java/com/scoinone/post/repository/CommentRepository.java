package com.scoinone.post.repository;

import com.scoinone.post.entity.CommentEntity;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
    Page<CommentEntity> findByPost_Id(Pageable pageable, Long postId);

    Optional<CommentEntity> findByIdAndUserId(Long id, String userId);

    Long deleteByIdAndUserId(Long id, String userId);
}