package com.scoinone.user.unit.mapper;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

import com.scoinone.user.dto.response.notification.CreateNotificationResponseDto;
import com.scoinone.user.dto.response.notification.GetNotificationResponseDto;
import com.scoinone.user.entity.NotificationEntity;
import com.scoinone.user.entity.UserEntity;
import com.scoinone.user.mapper.NotificationMapper;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

class NotificationMapperTest {
    private static final String testUserId = "aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaauser1";

    private NotificationMapper notificationMapper;
    private List<NotificationEntity> notifications;

    @BeforeEach
    public void setUp() {
        notificationMapper = Mappers.getMapper(NotificationMapper.class);
        notifications = Arrays.asList(
                createNotification(1L, testUserId),
                createNotification(2L, testUserId),
                createNotification(3L, testUserId)
        );
    }

    @Test
    @DisplayName("알람 엔티티 객체를 생성용 응답 DTO로 매핑")
    public void testNotificationToCreateNotificationResponseDto() {
        NotificationEntity notification = notifications.getFirst();
        CreateNotificationResponseDto responseDto = notificationMapper.notificationToCreateNotificationResponseDto(
                notification
        );
        assertSoftly(softly -> {
            softly.assertThat(responseDto.getNotificationId()).isEqualTo(notification.getId());
            softly.assertThat(responseDto.getUserId()).isEqualTo(notification.getUser().getId());
        });
    }

    @Test
    @DisplayName("알람 엔티티 객체를 조회용 응답 DTO로 매핑")
    public void testNotificationToGetNotificationResponseDto() {
        NotificationEntity notification = notifications.getFirst();
        GetNotificationResponseDto responseDto = notificationMapper.notificationToGetNotificationResponseDto(
                notification
        );

        assertSoftly(softly -> {
            softly.assertThat(responseDto.getNotificationId()).isEqualTo(notification.getId());
            softly.assertThat(responseDto.getUserId()).isEqualTo(notification.getUser().getId());
            softly.assertThat(responseDto.getCreatedAt()).isEqualTo(notification.getCreatedAt());
        });
    }

    private NotificationEntity createNotification(Long id, String userId) {
        UserEntity user = UserEntity.builder()
                .id(userId)
                .build();

        return NotificationEntity.builder()
                .id(id)
                .user(user)
                .build();
    }
}