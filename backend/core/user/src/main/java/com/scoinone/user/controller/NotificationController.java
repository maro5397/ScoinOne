package com.scoinone.user.controller;

import com.scoinone.user.dto.common.DeleteResponseDto;
import com.scoinone.user.dto.request.notification.CreateNotificationRequestDto;
import com.scoinone.user.dto.response.notification.CreateNotificationResponseDto;
import com.scoinone.user.dto.response.notification.GetNotificationsResponseDto;
import com.scoinone.user.entity.NotificationEntity;
import com.scoinone.user.mapper.NotificationMapper;
import com.scoinone.user.service.NotificationService;
import com.scoinone.user.sse.SseEmitters;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequestMapping("/api/notification")
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationService notificationService;
    private final SseEmitters sseEmitters;

    @PostMapping
    public ResponseEntity<CreateNotificationResponseDto> createNotification(
        @RequestBody CreateNotificationRequestDto requestDto
    ) {
        NotificationEntity notification = notificationService.createNotification(
                requestDto.getEmail(),
                requestDto.getMessage()
        );
        sseEmitters.notification(
                notification.getUser().getId(),
                NotificationMapper.INSTANCE.notificationToGetNotificationResponseDto(notification)
        );
        return new ResponseEntity<>(
                NotificationMapper.INSTANCE.notificationToCreateNotificationResponseDto(notification),
                HttpStatus.CREATED
        );
    }

    @GetMapping
    public ResponseEntity<GetNotificationsResponseDto> getNotifications(
            @RequestHeader(value = "UserId") String userId
    ) {
        List<NotificationEntity> notificationsFromLast30Days =
                notificationService.getNotificationsFromLast30DaysByUserId(userId);
        return new ResponseEntity<>(
                NotificationMapper.INSTANCE.listToGetNotificationsResponseDto(notificationsFromLast30Days),
                HttpStatus.OK
        );
    }

    @GetMapping(value = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public ResponseEntity<SseEmitter> getNotificationStream(@RequestHeader(value = "UserId") String userId) {
        SseEmitter emitter = new SseEmitter();
        sseEmitters.add(userId, emitter);
        try {
            emitter.send(SseEmitter.event()
                    .name("ping")
                    .data("ping"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok(emitter);
    }

    @DeleteMapping("/{notificationId}")
    public ResponseEntity<DeleteResponseDto> deleteNotification(@PathVariable("notificationId") Long notificationId) {
        String result = notificationService.deleteNotification(notificationId);
        DeleteResponseDto responseDto = new DeleteResponseDto(result);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
}
