package com.scoinone.user.repository;

import com.scoinone.user.entity.NotificationEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends JpaRepository<NotificationEntity, Long> {
    @Query(
            value = "SELECT * " +
                    "FROM notifications n " +
                    "WHERE n.user_id = :userId AND n.created_at >= NOW() - INTERVAL 30 DAY",
            nativeQuery = true
    )
    List<NotificationEntity> findByUserIdAndLast30Days(@Param("userId") String userId);
}
