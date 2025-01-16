package com.scoinone.user.dto.response.notification;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateNotificationResponseDto {
    private Long notificationId;
    private String userId;
    private String content;
    private String status;
}