package com.scoinone.user.service;

import com.scoinone.user.entity.NotificationEntity;
import java.util.List;

public interface NotificationService {
    NotificationEntity createNotification(String email, String content);

    List<NotificationEntity> getNotificationsFromLast30DaysByUserId(String userId);

    String deleteNotification(Long id);
}
