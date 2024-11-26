package com.scoinone.core.service.impl;

import com.scoinone.core.common.NotificationStatus;
import com.scoinone.core.entity.Notification;
import com.scoinone.core.entity.User;
import com.scoinone.core.repository.NotificationRepository;
import com.scoinone.core.repository.UserRepository;
import com.scoinone.core.service.NotificationService;
import jakarta.persistence.EntityNotFoundException;
import java.time.Clock;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;

    private final Clock clock;

    @Override
    public Notification createNotification(String email, String message) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("User not found with email: " + email));
        Notification notification = Notification.builder()
                .content(message)
                .user(user)
                .status(NotificationStatus.UNREAD)
                .expiresAt(LocalDateTime.now(clock).plusDays(7))
                .build();
        return notificationRepository.save(notification);
    }

    @Override
    public String deleteNotification(Long id) {
        notificationRepository.deleteById(id);
        return "Notification deleted successfully";
    }
}
