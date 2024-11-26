package com.scoinone.core.dto.request.notification;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateNotificationRequestDto {
    private String email;
    private String message;
}
