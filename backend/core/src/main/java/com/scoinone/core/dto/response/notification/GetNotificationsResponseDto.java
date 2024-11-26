package com.scoinone.core.dto.response.notification;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GetNotificationsResponseDto {
    private List<GetNotificationResponseDto> notifications;
}
