package com.scoinone.core.dto.request.notification;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.scoinone.core.common.NotificationStatus;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateNotificationRequestDto {
    private Long userId;
    private String message;
    private NotificationStatus status;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime expiresAt;
}
