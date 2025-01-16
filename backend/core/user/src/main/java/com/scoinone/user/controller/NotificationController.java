package com.scoinone.user.controller;

import com.scoinone.user.dto.common.DeleteResponseDto;
import com.scoinone.user.dto.request.notification.CreateNotificationRequestDto;
import com.scoinone.user.dto.response.notification.CreateNotificationResponseDto;
import com.scoinone.user.entity.NotificationEntity;
import com.scoinone.user.mapper.NotificationMapper;
import com.scoinone.user.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<CreateNotificationResponseDto> createNotification(
        @RequestBody CreateNotificationRequestDto requestDto
    ) {
        NotificationEntity notification = notificationService.createNotification(
                requestDto.getEmail(),
                requestDto.getMessage()
        );
        return new ResponseEntity<>(
                NotificationMapper.INSTANCE.notificationToCreateNotificationResponseDto(notification),
                HttpStatus.CREATED
        );
    }

    @DeleteMapping("/{notificationId}")
    public ResponseEntity<DeleteResponseDto> deleteNotification(@PathVariable("notificationId") Long notificationId) {
        String result = notificationService.deleteNotification(notificationId);
        DeleteResponseDto responseDto = new DeleteResponseDto(result);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
}
