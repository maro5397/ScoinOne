package com.scoinone.core.dto.request.notification;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateNotificationRequestDto {
    private Long userId;
    private String message;
    private String status;
    private String expiresAt;
}
