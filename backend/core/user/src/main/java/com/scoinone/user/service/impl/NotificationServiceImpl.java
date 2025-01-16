package com.scoinone.user.service.impl;

import com.scoinone.user.common.status.NotificationStatus;
import com.scoinone.user.entity.NotificationEntity;
import com.scoinone.user.entity.UserEntity;
import com.scoinone.user.repository.NotificationRepository;
import com.scoinone.user.repository.UserRepository;
import com.scoinone.user.service.NotificationService;
import jakarta.persistence.EntityNotFoundException;
import java.time.Clock;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;

    private final Clock clock;

    @Override
    public NotificationEntity createNotification(String email, String content) {
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("User not found with email: " + email));
        NotificationEntity notification = NotificationEntity.builder()
                .content(content)
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
