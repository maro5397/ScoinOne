package com.scoinone.core.mapper;

import com.scoinone.core.dto.response.notification.CreateNotificationResponseDto;
import com.scoinone.core.dto.response.notification.GetNotificationListResponseDto;
import com.scoinone.core.dto.response.notification.GetNotificationResponseDto;
import com.scoinone.core.dto.response.notification.UpdateNotificationResponseDto;
import com.scoinone.core.entity.Notification;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface NotificationMapper {

    @Mapping(source = "notificationId", target = "notificationId")
    @Mapping(source = "user.userId", target = "userId")
    CreateNotificationResponseDto notificationToCreateNotificationResponseDto(Notification notification);

    @Mapping(source = "notificationId", target = "notificationId")
    @Mapping(source = "user.userId", target = "userId")
    @Mapping(source = "createdAt", target = "createdAt")
    @Mapping(source = "expiresAt", target = "expiresAt")
    GetNotificationResponseDto notificationToGetNotificationResponseDto(Notification notification);

    @Mapping(source = "notificationId", target = "notificationId")
    @Mapping(source = "user.userId", target = "userId")
    @Mapping(source = "updatedAt", target = "updatedAt")
    @Mapping(source = "expiresAt", target = "expiresAt")
    UpdateNotificationResponseDto notificationToUpdateNotificationResponseDto(Notification notification);

    List<GetNotificationResponseDto> notificationsToGetNotificationResponseDtos(List<Notification> notifications);

    default GetNotificationListResponseDto listtoGetNotificationListResponseDto(List<Notification> notifications) {
        GetNotificationListResponseDto responseDto = new GetNotificationListResponseDto();
        responseDto.setNotifications(notificationsToGetNotificationResponseDtos(notifications));
        return responseDto;
    }
}
