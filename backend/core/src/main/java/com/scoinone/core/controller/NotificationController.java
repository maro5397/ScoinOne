package com.scoinone.core.controller;

import com.scoinone.core.dto.common.DeleteResponseDto;
import com.scoinone.core.dto.request.notification.CreateNotificationRequestDto;
import com.scoinone.core.dto.response.notification.CreateNotificationResponseDto;
import com.scoinone.core.entity.Notification;
import com.scoinone.core.mapper.NotificationMapper;
import com.scoinone.core.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/notification")
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationService notificationService;

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<CreateNotificationResponseDto> createNotification(
        @RequestBody CreateNotificationRequestDto requestDto
    ) {
        Notification notification = notificationService.createNotification(
                requestDto.getEmail(),
                requestDto.getMessage()
        );
        return new ResponseEntity<>(
                NotificationMapper.INSTANCE.notificationToCreateNotificationResponseDto(notification),
                HttpStatus.CREATED
        );
    }

    @DeleteMapping("/{notificationId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<DeleteResponseDto> deleteNotification(
            @PathVariable("notificationId") Long notificationId
    ) {
        String result = notificationService.deleteNotification(notificationId);
        DeleteResponseDto responseDto = new DeleteResponseDto(result);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
}
