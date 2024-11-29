package com.scoinone.core.service;

import com.scoinone.core.entity.Notification;

import java.time.LocalDateTime;
import java.util.List;

public interface NotificationService {
    Notification createNotification(String email, String content);

    String deleteNotification(Long id);
}
