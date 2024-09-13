package com.scoinone.core.dto.response.notification;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetNotificationResponseDto {
    private String notificationId;
    private String userId;
    private String content;
    private String status;
    private String createdAt;
    private String expiresAt;
}