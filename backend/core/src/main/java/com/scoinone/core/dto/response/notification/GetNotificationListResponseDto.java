package com.scoinone.core.dto.response.notification;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GetNotificationListResponseDto {
    private List<GetNotificationResponseDto> notifications;
}
