package com.scoinone.core.repository;

import com.scoinone.core.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    @Query("SELECT n " +
            "FROM Notification n " +
            "WHERE n.user.id = :userId AND n.createdAt >= CURRENT_TIMESTAMP - 30")
    List<Notification> findByUserIdAndLast30Days(Long userId);
}