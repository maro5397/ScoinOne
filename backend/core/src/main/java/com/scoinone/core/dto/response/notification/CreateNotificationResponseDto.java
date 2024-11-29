package com.scoinone.core.dto.response.notification;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateNotificationResponseDto {
    private Long notificationId;
    private Long userId;
    private String content;
    private String status;
}