package com.scoinone.user.dto.request.notification;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateNotificationRequestDto {
    private String email;
    private String message;
}
