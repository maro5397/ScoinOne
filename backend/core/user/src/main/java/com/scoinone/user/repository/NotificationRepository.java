package com.scoinone.user.repository;

import com.scoinone.user.entity.NotificationEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends JpaRepository<NotificationEntity, Long> {
    @Query("""
        SELECT n
        FROM NotificationEntity n
        WHERE n.user.id = :userId
        AND n.createdAt >= CURRENT_TIMESTAMP - 30
    """)
    List<NotificationEntity> findByUserIdAndLast30Days(@Param("userId") String userId);
}
