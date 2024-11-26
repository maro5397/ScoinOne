package com.scoinone.core.mapper;

import com.scoinone.core.dto.response.notification.CreateNotificationResponseDto;
import com.scoinone.core.dto.response.notification.GetNotificationListResponseDto;
import com.scoinone.core.dto.response.notification.GetNotificationResponseDto;
import com.scoinone.core.entity.Notification;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface NotificationMapper {
    NotificationMapper INSTANCE = Mappers.getMapper(NotificationMapper.class);

    @Mapping(source = "id", target = "notificationId")
    @Mapping(source = "user.id", target = "userId")
    CreateNotificationResponseDto notificationToCreateNotificationResponseDto(Notification notification);

    @Mapping(source = "id", target = "notificationId")
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "createdAt", target = "createdAt")
    @Mapping(source = "expiresAt", target = "expiresAt")
    GetNotificationResponseDto notificationToGetNotificationResponseDto(Notification notification);

    List<GetNotificationResponseDto> notificationsToGetNotificationsResponseDto(List<Notification> notifications);

    default GetNotificationListResponseDto listToGetNotificationListResponseDto(List<Notification> notifications) {
        GetNotificationListResponseDto responseDto = new GetNotificationListResponseDto();
        responseDto.setNotifications(notificationsToGetNotificationsResponseDto(notifications));
        return responseDto;
    }
}
