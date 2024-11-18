package com.scoinone.core.unit.mapper;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

import com.scoinone.core.dto.response.notification.CreateNotificationResponseDto;
import com.scoinone.core.dto.response.notification.GetNotificationListResponseDto;
import com.scoinone.core.dto.response.notification.GetNotificationResponseDto;
import com.scoinone.core.dto.response.notification.UpdateNotificationResponseDto;
import com.scoinone.core.entity.Notification;
import com.scoinone.core.entity.User;
import com.scoinone.core.mapper.NotificationMapper;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

class NotificationMapperTest {
    private NotificationMapper notificationMapper;
    private List<Notification> notifications;

    @BeforeEach
    public void setUp() {
        notificationMapper = Mappers.getMapper(NotificationMapper.class);
        notifications = Arrays.asList(
                createNotification(1L, 1L),
                createNotification(2L, 2L),
                createNotification(3L, 3L)
        );
    }

    @Test
    @DisplayName("알람 엔티티 객체를 생성하는 응답 DTO로 매핑")
    public void testNotificationToCreateNotificationResponseDto() {
        Notification notification = notifications.getFirst();
        CreateNotificationResponseDto responseDto = notificationMapper.notificationToCreateNotificationResponseDto(
                notification
        );
        assertSoftly(softly -> {
            softly.assertThat(responseDto.getNotificationId()).isEqualTo(notification.getId());
            softly.assertThat(responseDto.getUserId()).isEqualTo(notification.getUser().getId());
        });
    }

    @Test
    @DisplayName("알람 엔티티 객체를 조회하는 응답 DTO로 매핑")
    public void testNotificationToGetNotificationResponseDto() {
        Notification notification = notifications.getFirst();
        GetNotificationResponseDto responseDto = notificationMapper.notificationToGetNotificationResponseDto(
                notification
        );

        assertSoftly(softly -> {
            softly.assertThat(responseDto.getNotificationId()).isEqualTo(notification.getId());
            softly.assertThat(responseDto.getUserId()).isEqualTo(notification.getUser().getId());
            softly.assertThat(responseDto.getCreatedAt()).isEqualTo(notification.getCreatedAt());
            softly.assertThat(responseDto.getExpiresAt()).isEqualTo(notification.getExpiresAt());
        });
    }

    @Test
    @DisplayName("알람 엔티티 객체를 수정하는 응답 DTO로 매핑")
    public void testNotificationToUpdateNotificationResponseDto() {
        Notification notification = notifications.getFirst();
        UpdateNotificationResponseDto responseDto = notificationMapper.notificationToUpdateNotificationResponseDto(
                notification
        );
        assertSoftly(softly -> {
            softly.assertThat(responseDto.getNotificationId()).isEqualTo(notification.getId());
            softly.assertThat(responseDto.getUserId()).isEqualTo(notification.getUser().getId());
            softly.assertThat(responseDto.getExpiresAt()).isEqualTo(notification.getExpiresAt());
        });
    }

    @Test
    @DisplayName("알람 엔티티 객체를 리스트를 통해 조회하는 응답 DTO로 매핑")
    public void testListToGetNotificationListResponseDto() {
        GetNotificationListResponseDto responseDto = notificationMapper.listToGetNotificationListResponseDto(
                notifications
        );

        assertSoftly(softly -> {
            softly.assertThat(responseDto.getNotifications()).hasSize(3);
            softly.assertThat(responseDto.getNotifications().get(0).getNotificationId())
                    .isEqualTo(notifications.get(0).getId());
            softly.assertThat(responseDto.getNotifications().get(1).getNotificationId())
                    .isEqualTo(notifications.get(1).getId());
            softly.assertThat(responseDto.getNotifications().get(2).getNotificationId())
                    .isEqualTo(notifications.get(2).getId());
        });
    }

    private Notification createNotification(Long id, Long userId) {
        User user = User.builder()
                .id(userId)
                .build();

        return Notification.builder()
                .id(id)
                .createdAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusDays(1))
                .user(user)
                .build();
    }
}