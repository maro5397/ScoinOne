package com.scoinone.user.unit.service.impl;

import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.scoinone.user.entity.NotificationEntity;
import com.scoinone.user.entity.UserEntity;
import com.scoinone.user.repository.NotificationRepository;
import com.scoinone.user.repository.UserRepository;
import com.scoinone.user.service.impl.NotificationServiceImpl;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class NotificationServiceImplTest {
    private static final String testUserId = "aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaauser1";

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
        UserEntity user = UserEntity.builder().build();
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        notificationService.createNotification(email, content);

        ArgumentCaptor<NotificationEntity> notificationCaptor = ArgumentCaptor.forClass(NotificationEntity.class);
        verify(notificationRepository).save(notificationCaptor.capture());

        NotificationEntity notification = notificationCaptor.getValue();

        assertSoftly(softly -> {
            softly.assertThat(notification).isNotNull();
            softly.assertThat(notification.getContent()).isEqualTo("Test notification");
            verify(notificationRepository).save(notification);
        });
    }

    @Test
    @DisplayName("사용자 알림 30일까지 조회")
    public void testGetCommentsFromLast30DaysByUserId() {
        List<NotificationEntity> notifications = Collections.singletonList(NotificationEntity.builder().build());
        when(notificationRepository.findByUserIdAndLast30Days(testUserId)).thenReturn(notifications);

        List<NotificationEntity> result = notificationService.getNotificationsFromLast30DaysByUserId(testUserId);

        assertSoftly(softly -> {
            softly.assertThat(result).isNotNull();
            verify(notificationRepository).findByUserIdAndLast30Days(testUserId);
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