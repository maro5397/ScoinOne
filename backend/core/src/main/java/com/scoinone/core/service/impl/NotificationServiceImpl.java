package com.scoinone.core.service.impl;

import com.scoinone.core.entity.Notification;
import com.scoinone.core.repository.NotificationRepository;
import com.scoinone.core.service.NotificationService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;

    @Override
    public Notification getNotificationById(Long id) {
        return notificationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Notification not found with id: " + id));
    }

    @Override
    public Notification createNotification(Notification notification) {
        return notificationRepository.save(notification);
    }

    @Override
    public Notification updateNotification(Long id, Notification updatedNotification) {
        Notification existedNotification = getNotificationById(id);
        existedNotification.setContent(updatedNotification.getContent());
        return notificationRepository.save(existedNotification);
    }

    @Override
    public void deleteNotification(Long id) {
        notificationRepository.deleteById(id);
    }
}
