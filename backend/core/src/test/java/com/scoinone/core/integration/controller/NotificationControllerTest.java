package com.scoinone.core.integration.controller;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

import com.scoinone.core.config.TestContainerConfig;
import com.scoinone.core.dto.common.DeleteResponseDto;
import com.scoinone.core.dto.request.notification.CreateNotificationRequestDto;
import com.scoinone.core.dto.response.notification.CreateNotificationResponseDto;
import com.scoinone.core.entity.Notification;
import com.scoinone.core.entity.User;
import com.scoinone.core.repository.NotificationRepository;
import com.scoinone.core.service.AuthService;
import com.scoinone.core.service.NotificationService;
import com.scoinone.core.service.UserService;
import com.scoinone.core.util.UserDataInitializer;
import java.util.Objects;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import({TestContainerConfig.class, UserDataInitializer.class})
@ActiveProfiles("dev")
class NotificationControllerTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private NotificationRepository notificationRepository;

    private Notification savedNotification;
    private User savedUser;
    private HttpHeaders headers;

    @BeforeEach
    void setUp(
            @Autowired NotificationService notificationService,
            @Autowired UserService userService,
            @Autowired AuthService authService
    ) {
        savedUser = userService.getUserByEmail("user@example.com");

        savedNotification = notificationService.createNotification(savedUser.getEmail(), "test Notification Content");

        String jwtToken = authService.authenticate("admin@example.com", "password");

        headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + jwtToken);
        headers.set("Content-Type", "application/json");
    }

    @AfterEach
    void tearDown() {
        notificationRepository.deleteAll();
    }

    @Test
    @DisplayName("알림 생성 테스트")
    void createNotification_shouldReturnCreatedNotification() {
        CreateNotificationRequestDto requestDto = new CreateNotificationRequestDto();
        requestDto.setEmail("user@example.com");
        requestDto.setMessage("This is a test notification.");

        ResponseEntity<CreateNotificationResponseDto> response = restTemplate.exchange(
                "/api/notification",
                HttpMethod.POST,
                new HttpEntity<>(requestDto, headers),
                CreateNotificationResponseDto.class
        );

        assertSoftly(softly -> {
            softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
            softly.assertThat(response.getBody()).isNotNull();
            softly.assertThat(Objects.requireNonNull(response.getBody()).getUserId()).isEqualTo(savedUser.getId());
            softly.assertThat(response.getBody().getContent()).isEqualTo("This is a test notification.");
        });
    }

    @Test
    @DisplayName("알림 삭제 테스트")
    void deleteNotification_shouldReturnOk() {
        ResponseEntity<DeleteResponseDto> response = restTemplate.exchange(
                "/api/notification/" + savedNotification.getId(),
                HttpMethod.DELETE,
                new HttpEntity<>(headers),
                DeleteResponseDto.class
        );

        assertSoftly(softly -> {
            softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
            softly.assertThat(response.getBody()).isNotNull();
            softly.assertThat(Objects.requireNonNull(response.getBody()).getMessage())
                    .contains("Notification deleted successfully");
        });
    }
}