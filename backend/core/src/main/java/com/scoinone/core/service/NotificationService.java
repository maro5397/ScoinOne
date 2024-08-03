package com.scoinone.core.service;

import com.scoinone.core.entity.Notification;

import java.util.List;

public interface NotificationService {

    Notification getNotificationById(Long id);

    Notification createNotification(Notification notification);

    Notification updateNotification(Long id, Notification updatedNotification);

    void deleteNotification(Long id);
}
