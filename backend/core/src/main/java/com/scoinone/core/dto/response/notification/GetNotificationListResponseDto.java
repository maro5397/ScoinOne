package com.scoinone.core.dto.response.notification;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GetNotificationListResponseDto {
    private List<NotificationDto> notifications;

    @Getter
    @Setter
    public static class NotificationDto {
        private String alarmId;
        private String userId;
        private String message;
        private String status;
        private String createdAt;
        private String expiresAt;
    }
}
