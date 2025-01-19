package com.scoinone.user.sse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.scoinone.user.dto.response.notification.GetNotificationResponseDto;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Component
@RequiredArgsConstructor
public class SseEmitters {
    private final Map<String, SseEmitter> emitters = new ConcurrentHashMap<>();
    private final ObjectMapper objectMapper;

    public SseEmitter add(String userId, SseEmitter emitter) {
        emitters.remove(userId);
        emitters.put(userId, emitter);
        emitter.onCompletion(() -> emitters.remove(userId));
        emitter.onTimeout(() -> emitters.remove(userId));
        return emitter;
    }

    public void notification(String userId, GetNotificationResponseDto notification) {
        SseEmitter emitter = emitters.get(userId);
        if (emitter != null) {
            try {
                String response = objectMapper.writeValueAsString(notification);
                emitter.send(SseEmitter.event()
                        .name("notification")
                        .data(response));
            } catch (IOException e) {
                emitters.remove(userId);
                throw new RuntimeException(e);
            }
        }
    }
}
