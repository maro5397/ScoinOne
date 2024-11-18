package com.scoinone.core.dto.response.notification;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.scoinone.core.common.NotificationStatus;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetNotificationResponseDto {
    private Long notificationId;
    private Long userId;
    private String content;
    private NotificationStatus status;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime expiresAt;
}