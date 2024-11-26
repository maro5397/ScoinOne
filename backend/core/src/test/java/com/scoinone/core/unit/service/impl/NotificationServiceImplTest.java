package com.scoinone.core.unit.service.impl;

import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.scoinone.core.entity.Notification;
import com.scoinone.core.entity.User;
import com.scoinone.core.repository.NotificationRepository;
import com.scoinone.core.repository.UserRepository;
import com.scoinone.core.service.impl.NotificationServiceImpl;
import java.time.Clock;
import java.time.ZoneId;
import java.time.Instant;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class NotificationServiceImplTest {
    @InjectMocks
    private NotificationServiceImpl notificationService;

    @Mock
    private NotificationRepository notificationRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private Clock clock;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        when(clock.instant()).thenReturn(Instant.parse("2024-11-21T00:00:00Z"));
        when(clock.getZone()).thenReturn(ZoneId.systemDefault());
    }

    @Test
    @DisplayName("알람 생성하기 테스트")
    public void testCreateNotification() {
        String email = "test@example.com";
        String content = "Test notification";
        User user = User.builder().build();
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        notificationService.createNotification(email, content);

        ArgumentCaptor<Notification> notificationCaptor = ArgumentCaptor.forClass(Notification.class);
        verify(notificationRepository).save(notificationCaptor.capture());

        Notification notification = notificationCaptor.getValue();

        assertSoftly(softly -> {
            softly.assertThat(notification).isNotNull();
            softly.assertThat(notification.getContent()).isEqualTo("Test notification");
            verify(notificationRepository).save(notification);
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