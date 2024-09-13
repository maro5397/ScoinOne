package com.scoinone.core.repository;

import com.scoinone.core.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    Optional<List<Notification>> findByUser_UserIdAndCreatedAtAfter(Long userId, LocalDateTime date);
}