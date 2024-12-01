package com.scoinone.core.repository;

import com.scoinone.core.entity.Comment;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    Page<Comment> findByPost_Id(Pageable pageable, Long postId);

    Optional<Comment> findByIdAndUser_Id(Long id, Long userId);

    Long deleteByIdAndUser_Id(Long id, Long userId);
}