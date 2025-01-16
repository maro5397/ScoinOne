package com.scoinone.user.dto.response.notification;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetNotificationsResponseDto {
    private List<GetNotificationResponseDto> notifications;
}
