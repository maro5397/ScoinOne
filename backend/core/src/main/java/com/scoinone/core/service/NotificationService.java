package com.scoinone.core.service;

import com.scoinone.core.entity.Notification;

public interface NotificationService {
    Notification createNotification(String email, String content);

    String deleteNotification(Long id);
}
