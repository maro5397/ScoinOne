package com.scoinone.user.integration.repository;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

import com.scoinone.user.common.status.NotificationStatus;
import com.scoinone.user.config.TestContainerConfig;
import com.scoinone.user.entity.NotificationEntity;
import com.scoinone.user.entity.UserEntity;
import com.scoinone.user.repository.NotificationRepository;
import com.scoinone.user.repository.UserRepository;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(TestContainerConfig.class)
@ActiveProfiles("dev")
class NotificationRepositoryTest {
    private static final String testUsername = "testUsername";

    private UserEntity user;

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        user = UserEntity.builder()
                .username(testUsername)
                .email("user@example.com")
                .password("securePassword")
                .build();
        userRepository.save(user);

        NotificationEntity notification1 = NotificationEntity.builder()
                .user(user)
                .content("New message received.")
                .status(NotificationStatus.UNREAD)
                .build();

        NotificationEntity notification2 = NotificationEntity.builder()
                .user(user)
                .content("Your order has been shipped.")
                .status(NotificationStatus.UNREAD)
                .build();

        notificationRepository.save(notification1);
        notificationRepository.save(notification2);
    }

    @AfterEach
    void tearDown() {
        notificationRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("사용자 ID 기준으로 30일 전 알람 조회")
    void testFindByUser_IdAndCreatedAtAfter() {
        String userId = user.getId();

        List<NotificationEntity> notifications = notificationRepository.findByUserIdAndLast30Days(userId);

        assertSoftly(softly -> {
            softly.assertThat(notifications).hasSize(2);
            softly.assertThat(notifications.get(0).getContent()).isEqualTo("New message received.");
            softly.assertThat(notifications.get(1).getContent()).isEqualTo("Your order has been shipped.");
        });
    }
}