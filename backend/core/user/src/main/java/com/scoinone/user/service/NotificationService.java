package com.scoinone.user.service;

import com.scoinone.user.entity.NotificationEntity;

public interface NotificationService {
    NotificationEntity createNotification(String email, String content);

    String deleteNotification(Long id);
}
