package com.scoinone.user.mapper;

import com.scoinone.user.dto.response.notification.CreateNotificationResponseDto;
import com.scoinone.user.dto.response.notification.GetNotificationResponseDto;
import com.scoinone.user.entity.NotificationEntity;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface NotificationMapper {
    NotificationMapper INSTANCE = Mappers.getMapper(NotificationMapper.class);

    @Mapping(source = "id", target = "notificationId")
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "status.value", target = "status")
    @Mapping(source = "createdAt", target = "createdAt")
    CreateNotificationResponseDto notificationToCreateNotificationResponseDto(NotificationEntity notification);

    @Mapping(source = "id", target = "notificationId")
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "status.value", target = "status")
    @Mapping(source = "createdAt", target = "createdAt")
    GetNotificationResponseDto notificationToGetNotificationResponseDto(NotificationEntity notification);

    List<GetNotificationResponseDto> notificationsToGetNotificationsResponseDto(List<NotificationEntity> notifications);
}
