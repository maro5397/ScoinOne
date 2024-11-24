package com.scoinone.core.repository;

import com.scoinone.core.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    @Query(
            value = "SELECT * " +
            "FROM notifications n " +
            "WHERE n.user_id = :userId AND n.created_at >= NOW() - INTERVAL 30 DAY",
            nativeQuery = true
    )
    List<Notification> findByUserIdAndLast30Days(@Param("userId") Long userId);
}