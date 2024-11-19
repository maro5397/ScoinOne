package com.scoinone.core.unit.service.impl;

import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.scoinone.core.entity.Notification;
import com.scoinone.core.repository.NotificationRepository;
import com.scoinone.core.service.impl.NotificationServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class NotificationServiceImplTest {
    @InjectMocks
    private NotificationServiceImpl notificationService;

    @Mock
    private NotificationRepository notificationRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("인덱스로 알람 조회하기")
    public void testGetNotificationById_Success() {
        Long notificationId = 1L;
        Notification notification = Notification.builder()
                .id(notificationId)
                .build();
        when(notificationRepository.findById(notificationId)).thenReturn(Optional.of(notification));

        Notification result = notificationService.getNotificationById(notificationId);

        assertSoftly(softly -> {
            softly.assertThat(result).isNotNull();
            softly.assertThat(result.getId()).isEqualTo(notificationId);
            verify(notificationRepository).findById(notificationId);
        });
    }

    @Test
    @DisplayName("인덱스로 알람 조회하기 실패")
    public void testGetNotificationById_NotFound() {
        Long notificationId = 1L;
        when(notificationRepository.findById(notificationId)).thenReturn(Optional.empty());

        assertSoftly(softly -> {
            softly.assertThatThrownBy(() -> notificationService.getNotificationById(notificationId))
                    .isInstanceOf(EntityNotFoundException.class)
                    .hasMessageContaining("Notification not found with id: " + notificationId);
        });
    }

    @Test
    @DisplayName("알람 생성하기 테스트")
    public void testCreateNotification() {
        Notification notification = Notification.builder()
                .content("Test notification")
                .build();
        when(notificationRepository.save(notification)).thenReturn(notification);

        Notification result = notificationService.createNotification(notification);

        assertSoftly(softly -> {
            softly.assertThat(result).isNotNull();
            softly.assertThat(result.getContent()).isEqualTo("Test notification");
            verify(notificationRepository).save(notification);
        });
    }

    @Test
    @DisplayName("알람 수정하기 테스트")
    public void testUpdateNotification() {
        Long notificationId = 1L;
        Notification existingNotification = Notification.builder()
                .id(notificationId)
                .content("Old content")
                .build();

        Notification updatedNotification = Notification.builder()
                .content("Updated content")
                .build();

        when(notificationRepository.findById(notificationId)).thenReturn(Optional.of(existingNotification));
        when(notificationRepository.save(existingNotification)).thenReturn(existingNotification);

        Notification result = notificationService.updateNotification(notificationId, updatedNotification);

        assertSoftly(softly -> {
            softly.assertThat(result).isNotNull();
            softly.assertThat(result.getContent()).isEqualTo("Updated content");
            verify(notificationRepository).findById(notificationId);
            verify(notificationRepository).save(existingNotification);
        });
    }

    @Test
    @DisplayName("알람 삭제하기 테스트")
    public void testDeleteNotification() {
        Long notificationId = 1L;

        notificationService.deleteNotification(notificationId);

        verify(notificationRepository).deleteById(notificationId);
    }
}